package com.rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.ResponseSpecification;
import io.restassured.specification.SpecificationQuerier;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.get;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class ResponseSpecificationExample {

    @BeforeClass
    public void beforeClass(){
/*        requestSpecification = with().
                baseUri("https://api.postman.com").
                header("X-Api-Key", "PMAK-5ff2d720d2a39a004250e5da-c658c4a8a1cee3516762cb1a51cba6c5e2").
                log().all();*/
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri("https://api.postman.com");
        requestSpecBuilder.addHeader("X-Api-Key", "PMAK-6471cc222c311653f84572da-44bc7a8b8b42551de8b0d56e5f1f89f520");
        requestSpecBuilder.log(LogDetail.ALL);

        RestAssured.requestSpecification = requestSpecBuilder.build();

//        responseSpecification = RestAssured.expect()
//                .statusCode(200).contentType(ContentType.JSON)
//                .log().all();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                log(LogDetail.ALL);

        RestAssured.responseSpecification = responseSpecBuilder.build();
    }


    @Test
    public void validate_status_code(){
       get("/workspaces");



    }

    @Test
    public void validate_response_body(){
        Response response = get("/workspaces")
                .then()
                 .extract()
                .response();

        assertThat(response.path("workspaces[0].name").toString(), equalTo("My Workspace2"));
    }
}

