package com.rest;


import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class RequestPayLoadComplexJsonTask {
    ResponseSpecification customResponseSpecification;

    @BeforeClass
    public void beforeClass() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
                setBaseUri("https://8cca1bed-9a34-49db-af07-4d1a41b7e7e4.mock.pstmn.io/postComplexJson").
                addHeader("x-mock-match-request-body", "true").
                setContentType("application/json;charset=utf-8").
                log(LogDetail.ALL);

        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                log(LogDetail.ALL);
        customResponseSpecification = responseSpecBuilder.build();

    }
        @Test
        public void validatePostRequestPayloadComplexJsonMyTask() {


    }
}

