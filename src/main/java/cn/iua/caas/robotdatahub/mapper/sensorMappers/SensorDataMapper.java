package cn.iua.caas.robotdatahub.mapper.sensorMappers;

import cn.iua.caas.robotdatahub.Entity.robotSensors.SensorData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SensorDataMapper {
    int insertSensorData(@Param("sensorDatas") List<SensorData> sensorDatas);
}
