package cn.iua.caas.robotdatahub.configure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ThreadPoolConfig {

    @Value("${robotdata.tcp.worker-thread-pool-size:10}")
    private int workerThreadPoolSize;

    @Value("${robotdata.tcp.acceptor-thread-pool-size:1}")
    public int acceptorThreadPoolSize;

    @Bean(name = "tcpWorkerExecutor")
    public ExecutorService tcpWorkerExecutor() {
        return Executors.newFixedThreadPool(workerThreadPoolSize);
    }

    @Bean(name = "tcpAcceptorExecutor")
    public ExecutorService tcpAcceptorExecutor() {return Executors.newFixedThreadPool(acceptorThreadPoolSize);}
}
