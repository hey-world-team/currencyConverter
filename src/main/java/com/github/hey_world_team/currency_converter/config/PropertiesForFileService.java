package com.github.hey_world_team.currency_converter.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class PropertiesForFileService {              // класс для введения свойств приложения
    @Value("${filepath}")                            // аннотация для конфигурации свойства хранения файла
    private String path;                             // переменная доступная только для данного класса


    public String getPath() {                        // метод для получения пути хранения файла
        return path;                                 // метод возвращает переменную path (return-оператор перехода)
    }
}
