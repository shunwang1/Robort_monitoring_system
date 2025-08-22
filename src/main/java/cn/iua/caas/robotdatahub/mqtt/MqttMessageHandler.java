package cn.iua.caas.robotdatahub.mqtt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;

@Service
public class MqttMessageHandler implements MessageHandler {
    private static final Logger logger = LoggerFactory.getLogger(MqttMessageHandler.class);

    @Override
    public void handleMessage(Message<?> message) {
        try {
            String payload = message.getPayload().toString();
            logger.info("收到MQTT消息: {}", payload);
            
            // TODO: 在这里添加您的业务处理逻辑
            // 1. 解析消息内容
            // 2. 数据转换
            // 3. 存储到数据库
            // 4. 触发其他业务逻辑
            
        } catch (Exception e) {
            logger.error("处理MQTT消息时发生错误", e);
        }
    }
} 