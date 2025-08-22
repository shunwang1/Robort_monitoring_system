package cn.iua.caas.robotdatahub.service;

import cn.iua.caas.robotdatahub.Entity.robots.RobotData;
import com.google.protobuf.ServiceException;

/**
 * 不用，次借口为老版本接口
 */
public interface RobotDataService {

   //机器人数据上传
   void saveRobotData(RobotData robotData) throws ServiceException;

   int deleteById(Long robotId) throws ServiceException;

   RobotData getRobotById(Long id) throws ServiceException;
}
