package cn.iua.caas.robotdatahub.service;

import cn.iua.caas.robotdatahub.Entity.page.PageResult;
import cn.iua.caas.robotdatahub.Entity.robots.SeedingRobot;

import com.google.protobuf.ServiceException;

import java.util.List;

public interface SeedingRobotService {

    void saveRobotData(SeedingRobot seedingRobot) throws Exception;

    List<SeedingRobot> getRobotByIEMI(String robotIEMI) throws ServiceException;

    SeedingRobot getLatestRobotDataByIEMI(String robotIEMI) throws ServiceException;

    List<SeedingRobot> getAllLatestRobotsData();

    List<String> getAllProvinceName() throws ServiceException;

    List<String> getAllCityNameByProvinceName(String provinceName) throws ServiceException;

    List<String> getAllDistrictNameByCityName(String cityName) throws ServiceException;

    List<String> getAllTownshipNameByDistrictName(String districtName) throws ServiceException;

    List<String> getRobotImeiByTownship(String township);

    List<SeedingRobot> fetch20DatasetByImei(String imei);

    PageResult getPage(int pageNum, int pageSize);

    List<String> getAllRobotCities();

    int updateDeviceStatus(String deviceId, int status);

    PageResult<SeedingRobot> getFilteredPage(int page, int size, String city, boolean status);
}
