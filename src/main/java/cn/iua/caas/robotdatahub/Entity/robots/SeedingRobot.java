package cn.iua.caas.robotdatahub.Entity.robots;
import cn.iua.caas.robotdatahub.Entity.robotLocation.Location;
import cn.iua.caas.robotdatahub.Entity.robotSensors.SensorData;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SeedingRobot {
    private Integer id;
    private String deviceId;
    private String deviceIEMI;
    //01:玉米播种机；02:小籽粒播种机；
    private String deviceType;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime RecordTimestamp;
    //01:都市所
    //02:xx研究所
    //03:xx农业基地
    private String organizationCode;
    private String ownerName;
    private String ownerPhone;
    private BigDecimal speed;
    private BigDecimal seedingArea;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    private boolean onDuty;
    private Location location;
    //（肥箱，种箱，气流等）
    private List<SensorData> sensorDatas;
}
