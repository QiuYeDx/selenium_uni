package com.example.selenium_uni.Controller;


import com.example.selenium_uni.Service.InstructionService;
import com.example.selenium_uni.beans.HttpResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.util.Vector;

@CrossOrigin(origins = "*")
@RestController
public class InstructionController {
    @Autowired
    private InstructionService instructionService;

    /**
     * 插入指令到表中，获取的参数是一个String串
     * @param string
     * @return
     */
    @RequestMapping(value = "/insertInstruction", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity insert_instruction(@RequestBody String string){
        string=URLDecoder.decode(string);
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try{
            boolean right=instructionService.insert_instructions(string);
            if(!right){
                httpResponseEntity.setCode("0");
                httpResponseEntity.setData(null);
                httpResponseEntity.setMessage("插入指令失败");
            }else{
                httpResponseEntity.setCode("200");
                httpResponseEntity.setData(null);
                httpResponseEntity.setMessage("插入指令成功");
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return httpResponseEntity;
    }

    /**
     * 创建表，首先删除之前可能存在的表，然后执行创建表语句
     * @return
     */
    @RequestMapping(value = "/initialTable", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity initial_table(){
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try{
            boolean right=instructionService.initial_table();
            if(!right){
                httpResponseEntity.setCode("0");
                httpResponseEntity.setData(null);
                httpResponseEntity.setMessage("初始化表失败");
            }else{
                httpResponseEntity.setCode("200");
                httpResponseEntity.setData(null);
                httpResponseEntity.setMessage("初始化表成功");
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return httpResponseEntity;
    }

    /**
     * 查询指令，返回值为包含指令的vector数组
     * @return
     */
    @RequestMapping(value = "/queryInstruction", method = RequestMethod.GET, headers = "Accept=application/json")
    public HttpResponseEntity query_instruction(){
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try{
            //判空
            Vector<String[]> instructions=instructionService.getInstruction();
            if(instructions.isEmpty()){
                httpResponseEntity.setCode("1");
                httpResponseEntity.setData(null);
                httpResponseEntity.setMessage("指令序列为空");
            }else{
                httpResponseEntity.setCode("200");
                httpResponseEntity.setData(instructions);
                httpResponseEntity.setMessage("查询指令成功");
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return httpResponseEntity;

    }

    /**
     * 查询日志的vector数组
     * @return
     */
    @RequestMapping(value = "/queryLog", method = RequestMethod.GET, headers = "Accept=application/json")
    public HttpResponseEntity query_log(){
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try{
            //判空
            Vector<String [] > logs = instructionService.getLog();
            if(logs.isEmpty()){
                httpResponseEntity.setCode("1");
                httpResponseEntity.setData(null);
                httpResponseEntity.setMessage("日志序列为空");
            }else{
                httpResponseEntity.setCode("200");
                httpResponseEntity.setData(logs);
                httpResponseEntity.setMessage("查询日志成功");
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return httpResponseEntity;

    }

    /**
     * 测试指令
     * @return
     */
    @RequestMapping(value = "/executeTest", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity test_instruction(){
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try{
            boolean right=instructionService.testInstruction();
            if(!right){
                httpResponseEntity.setCode("0");
                httpResponseEntity.setData(null);
                httpResponseEntity.setMessage("执行失败");
            }else{
                httpResponseEntity.setCode("200");
                httpResponseEntity.setData(null);
                httpResponseEntity.setMessage("执行成功");
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return httpResponseEntity;
    }
}
