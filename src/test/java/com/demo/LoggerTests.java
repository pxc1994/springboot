package com.demo;


import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j(topic = "web_log")
@RunWith(SpringRunner.class)
@SpringBootTest
public class LoggerTests {

    @Test
    public void testLog() {
        String name = "hello";
        String pwd = "world";
        log.debug("debugger...");
        log.info("info....");
        log.error("error.....");
        log.info("name：{},pawword:{}", name, pwd);
    }

}
