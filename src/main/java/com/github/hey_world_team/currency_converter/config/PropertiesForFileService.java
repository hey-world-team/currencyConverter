package com.github.hey_world_team.currency_converter.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author artemnoskov
 * @date 5/13/22
 * Класс для введения свойств приложения
 */
@Configuration
@PropertySource("classpath:application.properties")
@SuppressWarnings("all")
public class PropertiesForFileService {              //

    /**
     * Путь до папки с конфигурационными файлами
     */
    @Value("${filepath}")
    private String path;

    /**
     * @return путь хранения  файла
     */
    public String getPath() {
        return path;
    }
}
