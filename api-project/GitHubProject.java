package liveProject;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GitHubProject {
    RequestSpecification requestSpec;
    String sshKey;
    int sshKeyId;

    @BeforeClass
    public void setUp() {
        requestSpec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", "token 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")
                .setBaseUri("https://api.github.com")
                .build();

        sshKey = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCVxT1xIBtOIOTA+2a9+lAB9zDLOmc5z39fQabDP28IXl8SWEXrXjxcaCBjVnIZ82d/vshD+X/rfDTtnHnMRFv1L+4UUBfXhSZ2K1IqE3FKTP8AgXqUXML7l1QXw3wIrBwzbJMHcBNj9PbgfQkWgaDm5+x5ObG4qLj/NGw7YwwaaWbRGoH4gqXzE1wdjE2EW8SLu6CtXLnrfJ5Ee60ZlY+sxeKLNokXS7y2xzW9QY/hh68bnOnEJ2KTf25J4v9kkcTyfvFKFhCwgg08H9RVuoYyob5owrFInxfY3OFepiQGXpUOohn8d76dbp5J9rrBCCWeogDaSxsjntCK/1wAk9SD";
    }

    @Test(priority = 1)
    public void addKey() {
        String reqBody = "{\"title\": \"TestAPIKey\", \"key\": \"" + sshKey + "\"}";
        Response response = given().spec(requestSpec)
                .body(reqBody)
                .when().post("/user/keys");
        sshKeyId = response.then().extract().path("id");

        response.then().statusCode(201);
    }

    @Test(priority = 2)
    public void getKey() {
        Response Response = given().spec(requestSpec).pathParam("keyId", sshKeyId).when()
                .get("/user/keys/{keyId}");

        System.out.println(Response.body().asPrettyString());
        Response.then().statusCode(200);
    }

    @Test(priority = 3)
    public void deleteKey() {
        Response Response = given().spec(requestSpec).pathParam("keyId", sshKeyId).when()
            .delete("/user/keys/{keyId}");

        System.out.println(Response.body().asPrettyString());
        Response.then().statusCode(204);
    }
}