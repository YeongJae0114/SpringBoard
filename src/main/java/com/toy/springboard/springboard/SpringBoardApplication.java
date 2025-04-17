package com.toy.springboard.springboard;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Slf4j
@SpringBootApplication
@EnableConfigurationProperties
@EnableJpaAuditing
public class SpringBoardApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SpringBoardApplication.class);
        app.setBannerMode(Banner.Mode.OFF); // 실행 시, 처음에 뜨는 배너 출력 끄기
        app.run(args);
        log.info("SpringBoardApplication started successfully.");
    }
}
