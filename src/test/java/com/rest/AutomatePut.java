package com.rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class AutomatePut {
    @BeforeClass
    public void beforeClass() {

        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
                setBaseUri("https://api.postman.com").
                addHeader("X-Api-Key", "PMAK-6471cc222c311653f84572da-44bc7a8b8b42551de8b0d56e5f1f89f520").
                setContentType(ContentType.JSON).
                log(LogDetail.ALL);
        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                log(LogDetail.ALL);
        RestAssured.responseSpecification = responseSpecBuilder.build();

    }

    @Test
    public void validatePutRequestBDDStyle() {
        String workspaceID = "acf16991-83c1-4851-855b-5732a16074c4";
        String payload = "{\n" +
                "    \"workspace\": {\n" +
                "        \"name\": \"My WorkspaceTwo\",\n" +
                "        \"type\": \"private\",\n" +
                "        \"description\": \"This is a new description\"\n" +
                "    }\n" +
                "}";

        given().
            body(payload).
                pathParam("workspaceId",workspaceID).
        when().
                put("/workspaces/{workspaceId}").
              then().
                log().all().
                body("workspace.name",equalTo("My WorkspaceTwo"),"workspace.id",equalTo(workspaceID));
    }
}
