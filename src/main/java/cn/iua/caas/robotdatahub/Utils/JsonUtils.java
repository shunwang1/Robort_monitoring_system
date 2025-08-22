package cn.iua.caas.robotdatahub.Utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonUtils {
    // 全局唯一的 ObjectMapper 实例
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        // 注册 Java 8 时间模块（支持 LocalDateTime 等）
        OBJECT_MAPPER.registerModule(new JavaTimeModule());

        // 可选：其他配置（如忽略未知字段）
        // OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    // 禁止实例化（工具类）
    private JsonUtils() {}
}
