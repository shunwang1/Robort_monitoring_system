package cn.iua.caas.robotdatahub;

import cn.iua.caas.robotdatahub.Entity.robots.SeedingRobot;
import cn.iua.caas.robotdatahub.Utils.BCryptUtil;
import cn.iua.caas.robotdatahub.service.impl.SeedingRobotServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
class RobotDataHubApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void test() {
        BCryptUtil bCryptUtil = new BCryptUtil();
        System.out.println(bCryptUtil.encode("123456"));
        bCryptUtil.matches("123456", bCryptUtil.encode("123456"));
    }


    @Test
    public void test1() {
        SeedingRobotServiceImpl seedingRobotServiceImpl = new SeedingRobotServiceImpl();
        SeedingRobot robot = new SeedingRobot();
        robot.setId(1);
        robot.setSpeed(BigDecimal.valueOf(1.1));
        robot.setSeedingArea(BigDecimal.valueOf(48.9));
        //robot.setLongitude(BigDecimal.valueOf(103.6723));
        //robot.setLatitude(BigDecimal.valueOf(30.6328));
        //robot.setCreatedAt(LocalDateTime.of(2025, 6, 13, 14, 45, 30)); // 格式: yyyy-MM-dd'T'HH:mm:ss
        try{
            //seedingRobotServiceImpl.saveRobotData(robot);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
