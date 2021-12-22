package assertions;

import io.restassured.response.Response;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;

public class CustomAssertions {

    public static void assertResponseBody(String path, Matcher matcher, Response response){
        response.then().assertThat().body(path, matcher);
    }

    public static void assertThatStatusCodeIs(Response actualResponse, Matcher matcher){
        MatcherAssert.assertThat(actualResponse.getStatusCode(), matcher);
    }
}
