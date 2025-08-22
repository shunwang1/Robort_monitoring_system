package cn.iua.caas.robotdatahub.configure;

import cn.iua.caas.robotdatahub.tcp.TcpMessageHandler;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;


@Component
public class TcpConfig {

    @Value("${tcp.port}")
    private int port;

    @Autowired
    private TcpMessageHandler tcpMessageHandler;

    // Acceptor 线程池（单线程）
    @Resource(name = "tcpAcceptorExecutor")
    private ExecutorService acceptorExecutor;

    @PostConstruct
    public void start() {
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                System.out.println("TCP Server started on port " + port);
                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    acceptorExecutor.submit(() -> tcpMessageHandler.handleClient(clientSocket));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }


}
