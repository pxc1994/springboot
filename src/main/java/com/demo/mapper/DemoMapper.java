package com.demo.mapper;


import com.demo.dataobject.Info;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DemoMapper {

    @Select("select * from user ")
    List<Info> userInfo();
}
