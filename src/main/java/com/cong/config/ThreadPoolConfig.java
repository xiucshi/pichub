package com.cong.config;

import org.apache.tomcat.util.threads.TaskThreadFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * @Date : 2019/11/7
 * @Author : xiuc_shi
 **/
@Configuration
public class ThreadPoolConfig {
    @Bean
    public ExecutorService executorService(){
        return new ThreadPoolExecutor(1,5, 5, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());
    }
}
