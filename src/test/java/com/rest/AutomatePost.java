package com.rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import java.io.File;
import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.with;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;

public class AutomatePost {

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
    public void validate_post_request_bdd_style() {
        String payload = "{\n" +
                "    \"workspace\": {\n" +
                "        \"id\": \"acf16991-83c1-4851-855b-5732a16074c4\",\n" +
                "        \"name\": \"My WorkspaceOne\",\n" +
                "        \"type\": \"personal\",\n" +
                "        \"description\": \"This is my personal workspace so I'm just playing around with it.\"\n" +
                "       \n" +
                "        \n" +
                "    }\n" +
                "}";
        given().
                body(payload).
        when().
                post("/workspaces").

         then().
                log().all().
                assertThat().
                body("workspace.name",equalTo("My WorkspaceOne"),"workspace.id",
                        matchesPattern("^[a-z0-9-]{36}$"));

    }

    @Test
    public void validate_post_request_non_bdd_style() {
        String payload = "{\n" +
                "    \"workspace\": {\n" +
                "        \"id\": \"acf16991-83c1-4851-855b-5732a16074c4\",\n" +
                "        \"name\": \"My WorkspaceOne\",\n" +
                "        \"type\": \"personal\",\n" +
                "        \"description\": \"This is my personal workspace so I'm just playing around with it.\"\n" +
                "       \n" +
                "        \n" +
                "    }\n" +
                "}";

      Response response =  with().
              body(payload).
              post("/workspaces");
      assertThat(response.<String>path("workspace.name"),equalTo("My WorkSpaceOne"));
      assertThat(response.<String>path("workspace.name"),equalTo("My WorkSpaceOne"));


    }

    @Test
    public void validate_post_request_payload_from_file() {
        File file = new File("src/main/resources/CreateWorkspacePayload.json");
        given().
                body(file).
                when().
                post("/workspaces").

                then().
                log().all().
                assertThat().
                body("workspace.name", equalTo("My SecondWorkSpace"), "workspace.id",
                        matchesPattern("^[a-z0-9-]{36}$"));

    }

    @Test
    public void validate_post_request_payload_as_map() {
       HashMap <String,Object> mainObject = new HashMap<>();

       HashMap <String,String> nestedObject = new HashMap<>();
       nestedObject.put("name","myThirdWorkspace");
       nestedObject.put("type","personal");
       nestedObject.put("description","Created for test purposes #2");

       mainObject.put("workspace",nestedObject);

        given().
                body(mainObject).
        when().
             post("/workspaces").
        then().
            log().all().
            assertThat().
            body("workspace.name",equalTo("myThirdWorkspace"));
    }
}
