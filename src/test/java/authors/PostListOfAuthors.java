package authors;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static assertions.CustomAssertions.assertResponseBody;
import static assertions.CustomAssertions.assertThatStatusCodeIs;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;

public class PostListOfAuthors {
    private static final int PORT = 8080;
    private static final String HOST = "localhost";

    private static WireMockServer wireMockServer = new WireMockServer(PORT);

    @BeforeClass
    public static void setupStubPostListOfAuthors(){
        wireMockServer.start();

        WireMock.configureFor(HOST, PORT);
        WireMock.stubFor(post("/api/v1/Authors")
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("post-list-of-authors-response.json")
                        .withStatus(200)));
    }

    @AfterClass
    public static void tearDown(){
        if (null != wireMockServer && wireMockServer.isRunning()){
            wireMockServer.shutdownServer();
        }
    }

    @Test
    public void testPostListOfAuthors() throws URISyntaxException {
       Response response = RestAssured.given()
                .when()
                .post(new URI("http://localhost:8080/api/v1/Authors"));

        assertAll(
                () -> assertResponseBody("firstName[0]", is("Alice"), response),
                () -> assertResponseBody("lastName[0]", is("Walker"), response ),
                () -> assertResponseBody("idBook[0]", is(13), response ),
                () -> assertResponseBody("id[0]", is(100), response ),
                () -> assertThatStatusCodeIs(response, is(200))
        );
    }
    }

