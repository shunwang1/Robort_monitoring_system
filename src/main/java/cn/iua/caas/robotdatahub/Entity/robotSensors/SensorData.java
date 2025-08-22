package cn.iua.caas.robotdatahub.Entity.robotSensors;

import lombok.Data;

@Data
public class SensorData {
    private Integer id;
    private Integer robotId;
    private String name;
    private Double value;
}
