package cn.iua.caas.robotdatahub.Entity.robots;

import java.time.LocalDateTime;

import lombok.*;

/**
 * 不用，为测试对象
 */
public class RobotData {
    private Long id;
    private LocalDateTime timestamp;
    private Double longitude;
    private Double latitude;
    private Double objectWidth;
    private Integer quantity;
    private String robotNumber;
    private String userName;
} 