package com.microservice.mail.utils;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class FileUtil {
    
    @SneakyThrows
    public static String readTemplate(String fileName){
        ClassPathResource classPathResource = new ClassPathResource("templates/"+fileName);
        return new String(Files.readAllBytes(Paths.get(classPathResource.getURI())));
    }
    
}
