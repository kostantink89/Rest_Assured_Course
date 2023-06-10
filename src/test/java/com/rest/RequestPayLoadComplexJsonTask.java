package com.rest;


import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class RequestPayLoadComplexJsonTask {
    ResponseSpecification customResponseSpecification;

    @BeforeClass
    public void beforeClass() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
                setBaseUri("https://8cca1bed-9a34-49db-af07-4d1a41b7e7e4.mock.pstmn.io").
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


        List<Integer> rgbColors = new ArrayList<>();
        rgbColors.add(255);
        rgbColors.add(255);
        rgbColors.add(255);
        rgbColors.add(1);

        HashMap<String, Object> firstColor = new HashMap<>();
        firstColor.put("rgba", rgbColors);
        firstColor.put("hex", "#000");

        HashMap<String,Object> mainColor = new HashMap<String,Object>();
        mainColor.put("color","black");
        mainColor.put("category","hue");
        mainColor.put("type","primary");
        mainColor.put("code",firstColor);


        List<Integer> rgbColorsTwo = new ArrayList<>();
        rgbColorsTwo.add(0);
        rgbColorsTwo.add(0);
        rgbColorsTwo.add(0);
        rgbColorsTwo.add(1);

        HashMap<String, Object> secondaryColor= new HashMap<>();
        secondaryColor.put("rgba", rgbColorsTwo);
        secondaryColor.put("hex", "#FFF");

        HashMap<String,Object> secondColor = new HashMap<String,Object>();
        secondColor.put("color","white");
        secondColor.put("category","value");
        secondColor.put("code",secondaryColor);



        List<HashMap<String, Object>> colorsList = new ArrayList<HashMap<String, Object>>();
        colorsList.add(mainColor);
        colorsList.add(secondColor);


        HashMap<String, Object> firstMainObjects = new HashMap<String, Object>();
        firstMainObjects.put("colors", colorsList);

        given().
                body(firstMainObjects).
                when().
                post("/postComplexJson").
                then().spec(customResponseSpecification).
                assertThat().
                body("Message", equalTo("Everything went great! "));

    }
}

