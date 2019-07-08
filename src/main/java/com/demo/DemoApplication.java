package com.demo;

import com.demo.dataobject.Info;
import com.demo.mapper.DemoMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.Resource;
import java.util.List;

@SpringBootApplication
@EnableScheduling //声明定时任务
@Slf4j
public class DemoApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }


    @Resource
    private DemoMapper demoMapper;


    @Override
    public void run(String... args) throws Exception {

        int pageNo = 1;
        int pageSize = 2;
        PageHelper.startPage(pageNo, pageSize);
        List<Info> list = demoMapper.userInfo();
        //用PageInfo对结果进行包装
        PageInfo<Info> page = new PageInfo<>(list);
        System.out.println("总记录数 ： " + page.getTotal());
        System.out.println("结果集 ： " + page.getList());


    }
}
