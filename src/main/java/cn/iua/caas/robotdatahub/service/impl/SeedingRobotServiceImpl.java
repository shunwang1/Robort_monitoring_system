package cn.iua.caas.robotdatahub.service.impl;

import cn.iua.caas.robotdatahub.Entity.page.PageResult;
import cn.iua.caas.robotdatahub.Entity.robotLocation.Location;
import cn.iua.caas.robotdatahub.Entity.robotSensors.SensorData;
import cn.iua.caas.robotdatahub.Entity.robots.SeedingRobot;
import cn.iua.caas.robotdatahub.Utils.AmapReverseGeocoding;
import cn.iua.caas.robotdatahub.mapper.locationMappers.GeographicLocationMapper;
import cn.iua.caas.robotdatahub.mapper.robotMappers.SeedingRobotDataMapper;
import cn.iua.caas.robotdatahub.mapper.sensorMappers.SensorDataMapper;
import cn.iua.caas.robotdatahub.service.SeedingRobotService;
import com.google.protobuf.ServiceException;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class SeedingRobotServiceImpl implements SeedingRobotService {

    @Resource
    private SeedingRobotDataMapper seedingRobotDataMapper;

    @Resource
    private SensorDataMapper sensorDataMapper;

    @Resource
    private GeographicLocationMapper geographicLocationMapper;

    @Override
    @Transactional(rollbackFor = ServiceException.class)
    public void saveRobotData(SeedingRobot seedingRobot) throws Exception {
        try {
            // 设置创建时间
            seedingRobot.setCreatedAt(LocalDateTime.now());
            // 添加播种机基本数据
            int rowAffected = seedingRobotDataMapper.insert(seedingRobot);
            if (rowAffected <= 0) {
                log.debug("添加数据时，数据库无变化");
                throw new ServiceException("插入数据失败");
            }

            // 添加播种机传感器数据
            List<SensorData> sensorDataList = seedingRobot.getSensorDatas();
            if (sensorDataList != null && !sensorDataList.isEmpty()) {
                int seedingRobotId = seedingRobot.getId();
                for (SensorData s : sensorDataList) {
                    s.setRobotId(seedingRobotId);
                }
                int rowAffected2 = sensorDataMapper.insertSensorData(sensorDataList);
                if (rowAffected2 <= 0) {
                    log.debug("添加sensor数据时，数据库无变化");
                    throw new ServiceException("插入传感器数据失败");
                }
            }


            //此方法为设置更新地理位置频率
            // 获取位置并设置更新时间
            boolean needUpdate = false;
            Double latitude = seedingRobot.getLocation().getLatitude();
            Double longitude = seedingRobot.getLocation().getLongitude();
            Location lastLocation = geographicLocationMapper.getLastLocationByImei(seedingRobot.getDeviceIEMI());
            if(lastLocation == null) {
                needUpdate = true;
            }else {
                //System.out.println(lastLocation);
                LocalDateTime lastUpdateTime = lastLocation.getUpdatedTime();
                LocalDateTime now = LocalDateTime.now();
                if(lastUpdateTime == null || lastUpdateTime.plusHours(1).isBefore(now)) {
                    needUpdate = true;
                }
            }

            Location location = new Location();
            location.setLatitude(latitude);
            location.setLongitude(longitude);
            if(needUpdate) {
                location = AmapReverseGeocoding.getAddressByLocation(longitude.toString(), latitude.toString());
                if(location == null){
                    log.debug("无法解析经纬度，或者上传信息有误");
                    throw new ServiceException("地理位置无法获取");
                }
            }
            location.setDeviceIMEI(seedingRobot.getDeviceIEMI());
            // 更新播种机地理信息
            geographicLocationMapper.saveOrUpdateGeographicLocation(location);
            location.setId(seedingRobot.getId());
            geographicLocationMapper.saveGeographicLocation(location);

            //更新或新增设备的trace_num
            seedingRobotDataMapper.saveOrUpdateTraceNum(seedingRobot.getDeviceIEMI(),seedingRobot.getId());

            /**
            Double latitude = seedingRobot.getLocation().getLatitude();
            Double longitude = seedingRobot.getLocation().getLongitude();

            Location location = AmapReverseGeocoding.getAddressByLocation(longitude.toString(), latitude.toString());
            if(location == null){
                log.debug("无法解析经纬度，或者上传信息有误");
                throw new ServiceException("地理位置无法获取");
            }

            location.setDeviceIMEI(seedingRobot.getDeviceIEMI());
            // 更新播种机地理信息
            geographicLocationMapper.saveOrUpdateGeographicLocation(location);**/



        } catch (Exception e) {
            log.error("保存机器人数据失败", e);
            throw new ServiceException("保存机器人数据失败", e);
        }
    }

    @Override
    public List<SeedingRobot> getRobotByIEMI(String robotIEMI) throws ServiceException {
        List<SeedingRobot> result = seedingRobotDataMapper.getRobotByIEMI(robotIEMI);
        if(result==null){
            throw new ServiceException("未有查询到对应数据");
        }
        System.out.println("result:  "+result);
        return result;
    }

    @Override
    public SeedingRobot getLatestRobotDataByIEMI(String robotIEMI) throws ServiceException {
        SeedingRobot target = seedingRobotDataMapper.getLatestRobotDataByIEMI(robotIEMI);
        if(target==null){
            throw new ServiceException("未有查询到对应数据");
        }
        return target;
    }

    @Override
    public List<SeedingRobot> getAllLatestRobotsData() {
        List<SeedingRobot> AllTargets = seedingRobotDataMapper.getAllLatestRobotsData();
        return AllTargets;
    }

    @Override
    public List<String> getAllProvinceName() throws ServiceException {
        List<String> allProvinceNames = geographicLocationMapper.getAllRobotsProvinceCategory();
        if(allProvinceNames==null||allProvinceNames.isEmpty()){
            throw new ServiceException("internal error");
        }
        return allProvinceNames;
    }

    @Override
    public List<String> getAllCityNameByProvinceName(String provinceName) throws ServiceException {
        List<String> allCityNames = geographicLocationMapper.getAllRobotsCityCategory(provinceName);
        if(allCityNames==null||allCityNames.isEmpty()){
            throw new ServiceException("没有查询到对应城市");
        }
        return allCityNames;
    }

    @Override
    public List<String> getAllDistrictNameByCityName(String cityName) throws ServiceException {
        List<String> allDistrictNames = geographicLocationMapper.getAllRobotsDistrictCategory(cityName);
        if(allDistrictNames==null||allDistrictNames.isEmpty()){
            throw new ServiceException("没有查询到对应区域");
        }
        return allDistrictNames;
    }

    @Override
    public List<String> getAllTownshipNameByDistrictName(String districtName) throws ServiceException {
        List<String> allTownshipNames = geographicLocationMapper.getAllRobotsTownshipCategory(districtName);
        if(allTownshipNames==null||allTownshipNames.isEmpty()){
            throw new ServiceException("没有查询到对应乡县");
        }
        return allTownshipNames;
    }

    @Override
    public List<String> getRobotImeiByTownship(String township) {
        List<String> ImeiList= seedingRobotDataMapper.getRobotImeiByTownship(township);
        if(ImeiList==null||ImeiList.isEmpty()){
            return null;
        }
        return ImeiList;
    }

    @Override
    public List<SeedingRobot> fetch20DatasetByImei(String imei) {
        List<SeedingRobot> dataSet = seedingRobotDataMapper.fetch20DatasetByImei(imei);
        if(dataSet==null||dataSet.isEmpty()){
            return null;
        }
        return dataSet;
    }

    @Override
    public PageResult getPage(int pageNum, int pageSize) {
        int offest = (pageNum-1)*pageSize;
        int limit = pageSize;

        List<SeedingRobot> data = seedingRobotDataMapper.getPagedLatestRobotsData(limit,offest);
        int totalNumber = seedingRobotDataMapper.countLatestRobotsData();
        return new PageResult(data,totalNumber,pageNum,pageSize);
    }

    @Override
    public List<String> getAllRobotCities() {
        List<String> allCities = geographicLocationMapper.getAllCities();
        return allCities;
    }

    @Override
    public int updateDeviceStatus(String deviceId, int status) {
        return seedingRobotDataMapper.updateDeviceStatus(deviceId,status);
    }

    @Override
    public PageResult<SeedingRobot> getFilteredPage(int page, int size, String city, boolean status) {
        int offset = (page-1)*size;
        int limit = size;
        System.out.println("offset:  "+offset);
        System.out.println("limit:  "+limit);
        System.out.println("city:  "+city);
        System.out.println("status:  "+status);
        List<SeedingRobot> data = seedingRobotDataMapper.getFilteredPagedLatestRobotsData(limit,offset,city,status);
        int totalNumber = seedingRobotDataMapper.countLatestFilteredRobotsData(city,status);
        System.out.println("totalNumber:  "+totalNumber);
        return new PageResult(data,totalNumber,page,size);
    }
}
