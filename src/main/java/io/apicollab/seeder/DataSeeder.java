package io.apicollab.seeder;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.apicollab.seeder.dto.ApplicationDTO;
import io.apicollab.seeder.dto.SeedApi;
import io.apicollab.seeder.dto.SeedApp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Component
@Slf4j
public class DataSeeder {

    @Autowired
    private RestTemplate restTemplate;

    public void seed() throws Exception {
        log.info("Seeding data");
        String jsonData = getFile("swagger_defs/data.json");
        SeedApp[] apps = new ObjectMapper().readValue(jsonData, SeedApp[].class);
        String applicationId;
        for (SeedApp seedApp : apps) {
            applicationId = createApplication(seedApp);
            for (SeedApi apiData : seedApp.getApis()) {
                createApi(applicationId, apiData);
            }
        }
    }

    private String createApplication(SeedApp seedApp) {
        ApplicationDTO applicationDTO = ApplicationDTO.builder().name(seedApp.getName()).email(seedApp.getEmail()).build();
        ResponseEntity<ApplicationDTO> response = restTemplate.postForEntity("/applications", applicationDTO, ApplicationDTO.class);
        assert (response.getStatusCode()).is2xxSuccessful();
        log.info("Application {} with id {} created successfully", response.getBody().getName(), response.getBody().getApplicationId());
        return response.getBody().getApplicationId();
    }

    private void createApi(String applicationId, SeedApi seedApi) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> multipartRequest = new LinkedMultiValueMap<>();
        multipartRequest.add("swaggerDoc", loadSwaggerSpec(seedApi.getFilePath()));
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(multipartRequest, headers);
        ResponseEntity<String> response = restTemplate.exchange("/applications/" + applicationId + "/apis",
                HttpMethod.POST, requestEntity, String.class);
        log.info("Api swagger doc {} uploaded for application {}", seedApi.getFilePath(), applicationId);

    }

    private String getFile(String fileName) {
        String result = "";
        ClassLoader classLoader = getClass().getClassLoader();
        try {
            result = IOUtils.toString(classLoader.getResourceAsStream(fileName), "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException("Error occurred while loading file - " + fileName);
        }
        return result;
    }

    private FileSystemResource loadSwaggerSpec(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        return new FileSystemResource(classLoader.getResource(fileName).getPath());
    }
}
