package cn.iua.caas.robotdatahub.controller;

import cn.iua.caas.robotdatahub.Entity.page.PageResult;
import cn.iua.caas.robotdatahub.Entity.robots.RobotData;
import cn.iua.caas.robotdatahub.Entity.robots.SeedingRobot;
import cn.iua.caas.robotdatahub.common.Result;
import cn.iua.caas.robotdatahub.service.SeedingRobotService;
import com.google.protobuf.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/UAAS")
public class RobotDataController {

    //@Autowired
    //private RobotDataService robotDataService;

    @Autowired
    private SeedingRobotService seedingRobotService;

    @PostMapping("/robot-data-upload")
    public Result saveRobotData(@RequestBody RobotData robotData) {
        /**if(robotData==null){
            return Result.error("数据不能为空");
        }
        try{
            robotData.setUserName((UserContext.getUser()));
            robotDataService.saveRobotData(robotData);
            return Result.success();
        }catch (Exception e){
            return Result.error("数据上传失败");
        }**/
        return Result.error("功能尚未开放");
    }

    @DeleteMapping("/robot-data-delete/{RobotId}")
    public Result deleteRobotData(@PathVariable Long RobotId) {
        /**if(RobotId<=0||RobotId==null){
            return Result.error("删除对象id值为空");
        }
        try{
            robotDataService.deleteById(RobotId);
            return Result.success();
        } catch (ServiceException e) {
            return Result.error("删除失败");
        }**/
        return Result.error("功能尚未开放");
    }

    @GetMapping("/robot-data-page")
    public Result getPagedData(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PageResult<SeedingRobot> pageData = seedingRobotService.getPage(page, size);
        return Result.success(pageData);
    }

    @GetMapping("/robot-data-get/{RobotIEMI}")
    public Result getRobotData(@PathVariable String RobotIEMI) throws ServiceException {
        if(RobotIEMI.isEmpty()){
            return Result.error("IMEI建值错误");
        }
        List<SeedingRobot> seedingRobots = seedingRobotService.getRobotByIEMI(RobotIEMI);
        return seedingRobots.isEmpty()? Result.error("未查询到数据") : Result.success(seedingRobots);
    }

    @GetMapping("/latest-robot-data-get/{RobotIEMI}")
    public Result getLatestRobotData(@PathVariable String RobotIEMI) throws ServiceException {
        if (RobotIEMI.isEmpty()) {
            return Result.error("IMEI建值错误");
        }
        SeedingRobot target = seedingRobotService.getLatestRobotDataByIEMI(RobotIEMI);
        return target == null ? Result.error("未查询到数据") : Result.success(target);
    }

    @GetMapping("/latest-robot-data-get")
    public Result getAllLatestRobotData() throws ServiceException {
        List<SeedingRobot> latestRobots = seedingRobotService.getAllLatestRobotsData();
        if(latestRobots==null||latestRobots.isEmpty()){
            return Result.error("internal error");
        }
        return Result.success(latestRobots);
    }

    @GetMapping("/get-robot-province-name")
    public Result getProvinceNames() throws ServiceException {
        List<String> allProvinceNames = seedingRobotService.getAllProvinceName();
        if(allProvinceNames==null||allProvinceNames.isEmpty()){
            return Result.error("未找到各省");
        }
        return Result.success(allProvinceNames);
    }

    @GetMapping("/get-robot-city-name/{province}")
    public Result getCityNamesByProvince(@PathVariable String province) throws ServiceException {
        List<String> cityNames = seedingRobotService.getAllCityNameByProvinceName(province);
        if(cityNames==null||cityNames.isEmpty()){
            return Result.error("未找到对应城市");
        }
        return Result.success(cityNames);
    }

    @GetMapping("/get-robot-district-name/{city}")
    public Result getDistrictNamesByCity(@PathVariable String city) throws ServiceException {
        List<String> districtNames = seedingRobotService.getAllDistrictNameByCityName(city);
        if(districtNames==null||districtNames.isEmpty()){
            return Result.error("未找到对应区域");
        }
        return Result.success(districtNames);
    }

    @GetMapping("/get-robot-township-name/{district}")
    public Result getTownshipNamesByDistrict(@PathVariable String district) throws ServiceException {
        List<String> townshipNames = seedingRobotService.getAllTownshipNameByDistrictName(district);
        if(townshipNames==null||townshipNames.isEmpty()){
            return Result.error("未找到对应乡县");
        }
        return Result.success(townshipNames);
    }

    @GetMapping("/get-robot-imei-by-township-name/{township}")
    public Result getRobotsByTownship(@PathVariable String township) throws ServiceException {
        List<String> ImeiList = null;
        try {
            ImeiList = seedingRobotService.getRobotImeiByTownship(township);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
        if(ImeiList==null||ImeiList.isEmpty()){
            return Result.error("没有找到这个区域的机器人，请重新查找");
        }
        return Result.success(ImeiList);
    }

    @GetMapping("/fetch-20-robot-dataset-by-imei/{imei}")
    public Result fetch20DatasetByImei(@PathVariable String imei) throws ServiceException {
        if(imei==null||imei.isEmpty()){
            return Result.error("imei不能为空");
        }
        List<SeedingRobot> dataSet = seedingRobotService.fetch20DatasetByImei(imei);
        if(dataSet==null||dataSet.isEmpty()){
            return Result.error("未找到任何数据");
        }
        return Result.success(dataSet);
    }

    @GetMapping("/get-all-robot-city")
    public Result getAllRobotCity() throws ServiceException {
        List<String> allCity = seedingRobotService.getAllRobotCities();
        if(allCity==null||allCity.isEmpty()){
            return Result.error("查询城市异常");
        }
        return Result.success(allCity);

    }

    @GetMapping("/robot-data-page-filtered")
    public Result getFilteredPagedData(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) Boolean status
    ) {
        PageResult<SeedingRobot> filteredPageData = seedingRobotService.getFilteredPage(page, size,city,status);
        return Result.success(filteredPageData);
    }


}






