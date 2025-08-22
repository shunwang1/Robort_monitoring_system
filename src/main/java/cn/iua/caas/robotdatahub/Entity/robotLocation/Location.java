package cn.iua.caas.robotdatahub.Entity.robotLocation;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {
    private int id;
    private String deviceIMEI;
    private double latitude;
    private double longitude;
    private String provinceName;
    private String cityName;
    private String districtName;
    private String townShipName;
    private String fullAddress;
    private LocalDateTime updatedTime;
}
