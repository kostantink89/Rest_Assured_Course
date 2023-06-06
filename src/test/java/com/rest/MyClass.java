package com.rest;

import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.with;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class MyClass {
    RequestSpecification requestSpecification;


    @BeforeClass
    public void beforeClass() {
    requestSpecification = with().
           baseUri("https://api.postman.com").
           header("X-Api-Key", "PMAK-6471cc222c311653f84572da-44bc7a8b8b42551de8b0d56e5f1f89f520");
    }

    @Test
    public void validate_status_code() {

        given().spec(requestSpecification).
        when().
                get("/workspaces").
         then().
                assertThat().
                statusCode(200).log().all();

    }

    @Test
    public void validate_response_body() {
        given().spec(requestSpecification).
        when().
                get("/workspaces").
        then().
                log().all().
                assertThat().
                statusCode(200).
                body("workspaces[0].name",equalTo("My Workspace2"));

    }


}
