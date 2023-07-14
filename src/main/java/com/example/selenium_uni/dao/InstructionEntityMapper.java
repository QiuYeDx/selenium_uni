package com.example.selenium_uni.dao;
import com.example.selenium_uni.dao.Entity.InstructionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import java.util.List;
@Mapper
@Component
public interface InstructionEntityMapper {
    void createTable();
    void dropTable();
    int insert_instruction(InstructionEntity instructionEntity);
    List<InstructionEntity> queryInstructionList();
}
