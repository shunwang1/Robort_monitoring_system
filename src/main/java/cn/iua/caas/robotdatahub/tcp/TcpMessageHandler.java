package cn.iua.caas.robotdatahub.tcp;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import cn.iua.caas.robotdatahub.Entity.robots.SeedingRobot;
import cn.iua.caas.robotdatahub.Utils.JsonUtils;
import cn.iua.caas.robotdatahub.common.SpringContextHolder;
import cn.iua.caas.robotdatahub.service.SeedingRobotService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TcpMessageHandler {
    @Autowired
    private SeedingRobotService seedingRobotService;

    // Worker 线程池（处理数据）
    @Resource(name = "tcpWorkerExecutor")
    private ExecutorService workerExecutor;

    // 设备活跃状态追踪
    private static final Map<String, Long> deviceLastSeenMap = new ConcurrentHashMap<>();
    private static final ScheduledExecutorService statusChecker = Executors.newSingleThreadScheduledExecutor();

    static {
        statusChecker.scheduleAtFixedRate(() -> {
            long now = System.currentTimeMillis();
            for (Map.Entry<String, Long> entry : deviceLastSeenMap.entrySet()) {
                String deviceId = entry.getKey();
                long lastSeen = entry.getValue();
                if (now - lastSeen > 65_000) {
                    log.warn("⚠️ 设备 " + deviceId + " 超过 65 秒未上报，设置为离线");
                    deviceLastSeenMap.remove(deviceId);
                    try {
                        SeedingRobotService bean = SpringContextHolder.getBean(SeedingRobotService.class);
                        bean.updateDeviceStatus(deviceId, 0);
                    } catch (Exception e) {
                        log.error("❌ 更新设备状态失败: " + e.getMessage());
                    }
                }
            }
        }, 60, 60, TimeUnit.SECONDS);
    }

    public void handleClient(Socket socket) {
        InputStream inputStream = null;
        try {
            // 设置socket超时，避免长时间阻塞
            socket.setSoTimeout(30000); // 30秒超时
            socket.setKeepAlive(false); // 禁用keep-alive
            
            inputStream = socket.getInputStream();
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] temp = new byte[1024];
            int len;

            while ((len = inputStream.read(temp)) != -1) {
                buffer.write(temp, 0, len);
                String data = buffer.toString("UTF-8");
                int index;
                while ((index = data.indexOf("###END###")) != -1) {
                    String oneJson = data.substring(0, index);
                    // 提交给 Worker 线程池处理
                    workerExecutor.submit(() -> handleJson(oneJson));
                    data = data.substring(index + "###END###".length());
                }
                buffer.reset();
                buffer.write(data.getBytes(StandardCharsets.UTF_8));
            }
        } catch (IOException e) {
            log.debug("客户端连接已断开: " + e.getMessage());
        } catch (Exception e) {
            log.error("处理客户端连接时出错: " + e.getMessage());
        } finally {
            // 确保正确关闭所有资源
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                log.error("关闭输入流失败: " + e.getMessage());
            }
            try {
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                }
            } catch (IOException e) {
                log.error("关闭socket失败: " + e.getMessage());
            }
        }
    }

    public void handleJson(String json) {
        try {
            SeedingRobot robot = JsonUtils.OBJECT_MAPPER.readValue(json, SeedingRobot.class);
            // 记录设备活跃时间
            deviceLastSeenMap.put(robot.getDeviceIEMI(), System.currentTimeMillis());
            seedingRobotService.saveRobotData(robot);
        } catch (Exception e) {
            log.error("❌ JSON 解析失败: " + e.getMessage());
        }
    }
}