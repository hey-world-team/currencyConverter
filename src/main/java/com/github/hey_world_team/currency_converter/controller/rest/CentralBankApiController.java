package com.github.hey_world_team.currency_converter.controller.rest;

import com.github.hey_world_team.currency_converter.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class CentralBankApiController {     // класс для интерфейса приложения
    private final FileService fileService;  // (переменная) константа доступная только данного класса

    @Autowired
    //метод который принимает и возвращает fileservice
    public CentralBankApiController(FileService fileService) { this.fileService = fileService;
    }

    //TODO for future api with current date
 //    private static final String DATE_API = "date_req";
    //константа доступная только в данном классе
    private static final String LINK = "http://www.cbr.ru/scripts/XML_daily.asp";

    @GetMapping(value = "/foreignCurrencies")
    public ResponseEntity<String> getCurrencies() { // метод получения валют, вовзращает хз как назвать
        RestTemplate restTemplate = new RestTemplate();
        String currenciesXml = restTemplate.getForObject(LINK, String.class);
        String answer = fileService.writeToFile(currenciesXml);
        Object currenciesObj = fileService.parseXmlToObject(currenciesXml);
        return new ResponseEntity<>("File was " + answer.toLowerCase(), HttpStatus.OK);
    }
}