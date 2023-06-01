package com.rest;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;

public class AutomateHeaders {

    @Test
    public void multiple_headers() {
        Header header = new Header("header", "value2");
        Header matchHeader = new Header("x-mock-request-headers", "header");
        given().
                baseUri("https://e092bcf3-c429-4e8e-97f9-2491853e6b8b.mock.pstmn.io/").
                header(header).
                header(matchHeader).
                when().
                get("/get").
                then().
                log().all().
                assertThat().
                statusCode(200);
    }

    @Test
    public void multiple_headers_using_Headers() {
        Header header = new Header("header", "value2");
        Header matchHeader = new Header("x-mock-request-headers", "header");

        Headers headers = new Headers(header, matchHeader);
        given().
                baseUri("https://e092bcf3-c429-4e8e-97f9-2491853e6b8b.mock.pstmn.io/").
                headers(headers).
                when().
                get("/get").
                then().
                log().all().
                assertThat().
                statusCode(200);
    }

    @Test
    public void multiple_headers_using_map() {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("header", "value2");
        headers.put("x-mock-request-headers", "header");

        given().
                baseUri("https://e092bcf3-c429-4e8e-97f9-2491853e6b8b.mock.pstmn.io/").
                headers(headers).
                when().
                get("/get").
                then().
                log().all().
                assertThat().
                statusCode(200);
    }

    @Test
    public void multi_value_header_in_the_request() {
        Header header1 = new Header("multiValueHeader", "value1");
        Header header2 = new Header("multiValueHeader", "value2");

        Headers headers = new Headers(header1, header2);

        given().
                baseUri("https://e092bcf3-c429-4e8e-97f9-2491853e6b8b.mock.pstmn.io/").
//                headers(headers).
        headers(headers).
                log().headers().
                when().
                get("/get").
                then().
                log().all().
                assertThat().
                statusCode(200);
    }

    @Test
    public void assert_response_headers() {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("header", "value2");
        headers.put("x-mock-request-headers", "header");

        given().
                baseUri("https://e092bcf3-c429-4e8e-97f9-2491853e6b8b.mock.pstmn.io").
                headers(headers).
//        headers(headers).

        when().
                get("/get").
                then().
                assertThat().
                statusCode(200).
                headers("responseHeader", "resValue2", "X-RateLimit-Limit", "120");


    }

    @Test
    public void extract_response_headers() {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("header", "value1");
        headers.put("x-mock-request-headers", "header");

        Headers extractedHeaders = given().
                baseUri("https://e092bcf3-c429-4e8e-97f9-2491853e6b8b.mock.pstmn.io").
                headers(headers).
                when().
                get("/get").
                then().
                assertThat().
                statusCode(200).
                extract().
                headers();


        for (Header header : extractedHeaders) {
            System.out.println("Header = " + header.getName() + ", ");
            System.out.println("Header value = " + header.getValue());
        }
//        System.out.println("Header name = " + extractedHeaders.get("responseHeader").getName());
//        System.out.println("Header value = " + extractedHeaders.get("responseHeader").getValue());
//        System.out.println("Header value = " + extractedHeaders.getValue("responseHeader"));

    }

    @Test
    public void extract_multivalue_response_header() {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("header", "value1");
        headers.put("x-mock-request-headers", "header");

        Headers extractedHeaders = given().
                baseUri("https://e092bcf3-c429-4e8e-97f9-2491853e6b8b.mock.pstmn.io").
                headers(headers).
        when().
                get("/get").
         then().
                assertThat().
                statusCode(200).
                extract().
                headers();

        List<String> values = extractedHeaders.getValues("multiValueHeader");
        for (String value:values) {
            System.out.println(value);
        }

    }
}
