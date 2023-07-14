package com.example.selenium_uni.dao;
import com.example.selenium_uni.dao.Entity.LogEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import java.util.List;
@Mapper
@Component
public interface LogEntityMapper {
    void createTable();
    void dropTable();
    int insert_log(LogEntity logEntity);
    List<LogEntity> queryLogList();
}
