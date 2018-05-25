package io.apicollab.seeder;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.ServerSocket;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class SeederApplicationTests {

    private static Integer PORT;

    static {
        try {
            ServerSocket serverSocket = new ServerSocket(0);
            PORT = serverSocket.getLocalPort();
            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Unable to get a free port");
        }
        System.setProperty("api.portal.port", String.valueOf(PORT));
    }

    @Rule
    public final WireMockRule wireMockRule = new WireMockRule(PORT);

    @Autowired
    private DataSeeder dataSeeder;

    @Test
    public void createApplication() throws Exception {
        wireMockRule.stubFor(post(urlPathEqualTo("/applications"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(201)
                        .withBody("{\"id\":\"1\"}")));
        wireMockRule.stubFor(post(urlPathEqualTo("/applications/1/apis"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("Success")));
        dataSeeder.seed();
    }

}
