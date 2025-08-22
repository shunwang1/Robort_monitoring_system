package cn.iua.caas.robotdatahub.Utils;

import cn.iua.caas.robotdatahub.message.DeviceMessageWrapper;
import cn.iua.caas.robotdatahub.Entity.robots.SeedingRobot;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MqttPayloadParser {

    public static Object parse(String payload) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        DeviceMessageWrapper wrapper = null;
        try {
            wrapper = mapper.readValue(payload, DeviceMessageWrapper.class);
        }catch (Exception e){
            log.error("转译payload失败", e);
        }
        String deviceType = wrapper.getDeviceType();
        switch (deviceType) {
            case "seedingRobot":
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.registerModule(new JavaTimeModule());
                SeedingRobot seedingRobot = objectMapper.treeToValue(wrapper.getData(), SeedingRobot.class);
                return seedingRobot;
        }
        return null;
    }

}
