package authors;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static assertions.CustomAssertions.assertResponseBody;
import static assertions.CustomAssertions.assertThatStatusCodeIs;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;

public class GetListOfAuthors {
    private RequestSpecification requestSpecification;

    @Rule
    public WireMockRule getListOfAuthorsService = new WireMockRule(1000); //default port 8080

    @Before
    public void createRequestSpecification(){
        requestSpecification = new RequestSpecBuilder().
                setBaseUri("http://localhost").
                setPort(1000).
                build();
    }
    
    public void setupStubGetListOfAuthors(){
        configureFor("localhost", 1000);
        stubFor(get(urlEqualTo("/api/v1/Authors"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBodyFile("get-list-of-authors-response.json")));
    }

    @Test
    public void testGetListOfAuthors(){
        setupStubGetListOfAuthors();
        Response response =
                given()
                .spec(requestSpecification)
                .get("/api/v1/Authors");

        assertAll(
                () -> assertResponseBody("firstName[0]", is("First Name 1"), response),
                () -> assertResponseBody("lastName[0]", is("Last Name 1"), response ),
                () -> assertResponseBody("idBook[0]", is(1), response ),
                () -> assertResponseBody("id[0]", is(1), response ),
                () -> assertThatStatusCodeIs(response, is(200))
        );
    }
}
