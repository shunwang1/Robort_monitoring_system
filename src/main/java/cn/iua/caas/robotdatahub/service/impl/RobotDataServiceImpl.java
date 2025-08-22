package cn.iua.caas.robotdatahub.service.impl;

import cn.iua.caas.robotdatahub.service.RobotDataService;
import com.google.protobuf.ServiceException;

import cn.iua.caas.robotdatahub.Entity.robots.RobotData;

/**
 * 不用，次实现类是老版本
 */
public class RobotDataServiceImpl implements RobotDataService {

    //@Resource
    //private RobotDataMapper robotDataMapper;

    public void saveRobotData(RobotData robotData) throws ServiceException {
        /**
        try {
            int rowAffected = robotDataMapper.insert(robotData);
            if (rowAffected <= 0) {
                log.debug("添加数据时，数据库无变化");
                throw new ServiceException("插入数据失败");
            }
        }catch (Exception e) {
        }
         **/
    }

    @Override
    public int deleteById(Long robotId) throws ServiceException {
        /**int rowAffected = robotDataMapper.delete(robotId);
        if (rowAffected < 0) {
            log.debug("删除数据时，数据库无变化");
            throw new ServiceException("删除失败");
        }
        return 0;//删除失败
         **/
        return -1;
    }

    @Override
    public RobotData getRobotById(Long robotId) throws ServiceException {
        /**RobotData robotData = robotDataMapper.getRobotById(robotId);
        if(robotData == null){
            log.debug("没有找到对应的数据条");
            throw new ServiceException("没有找到对应的数据条");
        }
        return robotDataMapper.getRobotById(robotId);
         **/
        return null;
    }


}