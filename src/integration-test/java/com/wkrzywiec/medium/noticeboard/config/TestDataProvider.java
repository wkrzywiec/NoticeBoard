package com.wkrzywiec.medium.noticeboard.config;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.FileCopyUtils;
import springfox.documentation.spring.web.json.Json;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;

import static java.nio.charset.StandardCharsets.UTF_8;

public class TestDataProvider {

    private static final String NOTICE_JSON_PATH = "data/notice.json";

    public static JsonObject getNoticeJson(){
        String noticeString = getJsonString(NOTICE_JSON_PATH);
        return mapStringToJsonObject(noticeString);
    }

    private static String getJsonString(String path) {
        Resource resource = getResource(path);
        return mapResourceToString(resource);
    }

    private static Resource getResource(String path) {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        return resourceLoader.getResource("classpath:" + path);
    }

    private static String mapResourceToString(Resource resource) {
        try (Reader reader = new InputStreamReader(resource.getInputStream(), UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static JsonObject mapStringToJsonObject(String jsonString){
        Gson gson = new Gson();
        return gson.fromJson(jsonString, JsonObject.class);
    }
}
