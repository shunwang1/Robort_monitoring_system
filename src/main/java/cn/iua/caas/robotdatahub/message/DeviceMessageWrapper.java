package cn.iua.caas.robotdatahub.message;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

/**
 * 包装mqtt传过来的json消息。将消息分为两类做区分
 * 现在用tcp协议暂时不用
 */
@Data
public class DeviceMessageWrapper {
    private String deviceType;
    private JsonNode Data;
}
