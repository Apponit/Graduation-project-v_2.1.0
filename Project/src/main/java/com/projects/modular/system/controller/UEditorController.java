package com.projects.modular.system.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.projects.config.web.WebProperties;
import com.projects.modular.system.entity.Ueditor;

import cn.stylefeng.roses.core.util.ToolUtil;

@Controller

public class UEditorController {
	@Autowired
	private WebProperties gunsProperties;

	@RequestMapping("/uEditors")
    private String showPage(){
        return "ueditor.html";
    }
	
	

    @RequestMapping(value="/imgUpload")
    @ResponseBody
    public Ueditor imgUpload(MultipartFile[] upfile) {
    		Ueditor ueditor = new Ueditor();
    	
    	  // 循环上传多个图片
     			for (MultipartFile file : upfile) {
     				
     				try {
     					String fileSavePath = "";
     					String serverSavePath = "";
     					String pictureName = UUID.randomUUID().toString() + "." + ToolUtil.getFileSuffix(file.getOriginalFilename());
     					
     						fileSavePath = gunsProperties.getFileUploadPath();
     						serverSavePath = gunsProperties.getServerUploadPath();
     						file.transferTo(new File(fileSavePath + pictureName));
     					
						ueditor.setUrl( serverSavePath + pictureName);
						ueditor.setTitle(pictureName);
						ueditor.setState("SUCCESS");
						ueditor.setOriginal("上传成功");
				      
						
     				} catch (IOException e) {
						
						e.printStackTrace();
					}
     			
     			}
    
        
        
        
        
        return ueditor;
    }
	
	
	
	
	
    
}
