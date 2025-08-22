package cn.iua.caas.robotdatahub.mapper.robotMappers;

import cn.iua.caas.robotdatahub.Entity.robots.SeedingRobot;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SeedingRobotDataMapper {

    @Insert("INSERT INTO seeding_robot_general_data (device_id,device_iemi,\n" +
            "        device_type, record_timestamp, organization_code, speed,\n" +
            "        seeding_area,\n" +
            "        created_at,on_duty) " +
            "VALUES (#{deviceId}, #{deviceIEMI}, #{deviceType},\n" +
            "        #{RecordTimestamp}, #{organizationCode}, #{speed}, #{seedingArea},\n" +
            "        #{createdAt},#{onDuty})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(SeedingRobot seedingRobot);

    List<SeedingRobot> getRobotByIEMI(@Param("robotIEMI")String robotIEMI);

    SeedingRobot getLatestRobotDataByIEMI(@Param("robotIEMI")String robotIEMI);

    List<SeedingRobot> getAllLatestRobotsData();

    @Select("SELECT r.imei FROM latest_geo_location_info r" +
            " WHERE r.township = #{township}")
    List<String> getRobotImeiByTownship(@Param("township")String township);

    List<SeedingRobot> fetch20DatasetByImei(@Param("imei")String imei);

    List<SeedingRobot> getPagedLatestRobotsData(int limit, int offset);

    int countLatestRobotsData();

    @Update("UPDATE seeding_robot_general_data " +
            "SET on_duty = #{status} " +
            "WHERE device_iemi = #{deviceId} " +
            "AND record_timestamp IN " +
            "(SELECT record_timestamp FROM (SELECT record_timestamp " +
            "FROM seeding_robot_general_data WHERE device_iemi = #{deviceId} " +
            "ORDER BY record_timestamp DESC LIMIT 1) t)")
    int updateDeviceStatus(@Param("deviceId") String deviceId, @Param("status") int status);


    List<SeedingRobot> getFilteredPagedLatestRobotsData(@Param("limit") int limit,
                                                        @Param("offset") int offset,
                                                        @Param("city") String city,
                                                        @Param("status") boolean status);

    int countLatestFilteredRobotsData(@Param("city")String city, @Param("status") boolean status);

    @Insert("INSERT INTO device_upload_count (device_imei,trace_count,locator,record_time)" +
            "VALUES (#{deviceIMEI},1,#{id},NOW())" +
            " ON DUPLICATE KEY UPDATE " +
            "trace_count=trace_count + 1, " +
            "record_time = NOW()")
    void saveOrUpdateTraceNum(String deviceIMEI,int id);
}
