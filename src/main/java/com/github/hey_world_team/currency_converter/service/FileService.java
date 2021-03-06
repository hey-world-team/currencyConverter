package com.github.hey_world_team.currency_converter.service;

import com.github.hey_world_team.currency_converter.config.PropertiesForFileService;
import com.github.hey_world_team.currency_converter.repository.CurrencyDataRepository;
import com.github.hey_world_team.currency_converter.service.statuses.FileWriteStatus;
import com.github.hey_world_team.currency_converter.service.statuses.XmlParseStatus;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static java.lang.Double.parseDouble;

@Service
public class FileService {
    private static final String VALUTE_TAG_NAME = "Valute";

    private static final Logger log = LoggerFactory.getLogger(FileService.class);
    private final String fileForeignCurrencies;
    private final String charset;
    private final PropertiesForFileService propertiesForFileService;
    private final CurrencyDataRepository currencyDataRepository;

    @Autowired
    public FileService(PropertiesForFileService propertiesForFileService,
                       CurrencyDataRepository currencyDataRepository) {
        this.propertiesForFileService = propertiesForFileService;
        this.currencyDataRepository = currencyDataRepository;
        this.fileForeignCurrencies = propertiesForFileService.getFileForeignCurrencies();
        this.charset = propertiesForFileService.getCharset();
    }

    /**
     *
     * @param file
     * @return answer to controller
     *
     */
    public String writeToFile(String file) {
        log.info("Started read file {}", fileForeignCurrencies);
        var currencyFile = new File(propertiesForFileService.getPath() + fileForeignCurrencies);
        try (var outputStream = new FileOutputStream(currencyFile, false)) {
            log.info("Started write file {}", fileForeignCurrencies);
            outputStream.write(file.getBytes());
        } catch (IOException ex) {
            log.error(ex.getMessage());
            return FileWriteStatus.NOT_WRITTEN.name();
        }
        log.info("Write {} completed", fileForeignCurrencies);
        return FileWriteStatus.WRITTEN.name();
    }

    /**
     *
     * @return answer to controller
     * @throws IOException
     */
    public String parseXmlToObject() throws IOException {
        log.info("Started writing XML to object");
        var input = new File(propertiesForFileService.getPath() + fileForeignCurrencies);
        Document doc = Jsoup.parse(input, charset, "", Parser.xmlParser());

        for (Element e : doc.select(VALUTE_TAG_NAME)) {
            String name = e.getElementsByTag("Name").text();
            Double value = parseDouble(e.getElementsByTag("Value").text().replace(',', '.'));
            currencyDataRepository.save(name, value);
        }
        return XmlParseStatus.PARSED.name() +
                "\n---\nContent:\n" +
                currencyDataRepository.getAllCurrencies().toString();
    }
}
