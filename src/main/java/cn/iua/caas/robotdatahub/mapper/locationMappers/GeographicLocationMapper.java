package cn.iua.caas.robotdatahub.mapper.locationMappers;

import cn.iua.caas.robotdatahub.Entity.robotLocation.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GeographicLocationMapper {

    @Insert("INSERT INTO latest_geo_location_info(imei, longitude, latitude, province, " +
            "city, district, township, full_address, create_time, update_time) " +
            "VALUES (#{deviceIMEI}, #{longitude}, #{latitude}, #{provinceName}, " +
            "#{cityName}, #{districtName}, #{townShipName}, #{fullAddress}, NOW(), NOW()) " +
            "ON DUPLICATE KEY UPDATE " +
            "longitude = VALUES(longitude), " +
            "latitude = VALUES(latitude), " +
            "province = VALUES(province), " +
            "city = VALUES(city), " +
            "district = VALUES(district), " +
            "township = VALUES(township), " +
            "full_address = VALUES(full_address), " +
            "update_time = NOW()")
    int saveOrUpdateGeographicLocation(Location location);

    @Select("SELECT DISTINCT province FROM latest_geo_location_info")
    List<String> getAllRobotsProvinceCategory();

    @Select("SELECT DISTINCT r.city FROM latest_geo_location_info r" +
            " WHERE r.province = #{province}")
    List<String> getAllRobotsCityCategory(String province);

    @Select("SELECT DISTINCT r.district FROM latest_geo_location_info r" +
            " WHERE r.city = #{city}")
    List<String> getAllRobotsDistrictCategory(String city);

    @Select("SELECT DISTINCT r.township FROM latest_geo_location_info r" +
            " WHERE r.district = #{district}")
    List<String> getAllRobotsTownshipCategory(String district);

    @Select("SELECT imei, longitude, latitude, province, " +
            "city, district, township, full_address, create_time, update_time " +
            "FROM latest_geo_location_info r " +
            "WHERE imei = #{imei}")
    Location getLastLocationByImei(String imei);

    @Select("SELECT DISTINCT city" +
            " FROM latest_geo_location_info " +
            "WHERE city IS NOT NULL " +
            "ORDER BY city")
    List<String> getAllCities();

    @Insert("INSERT INTO geo_location_info(id,latitude,longitude,record_time)" +
            "values (#{id},#{latitude},#{longitude},NOW())")
    int saveGeographicLocation(Location location);
}
