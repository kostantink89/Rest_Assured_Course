package com.rest;

import io.restassured.config.LogConfig;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;

public class AutomateGet {

    @Test
    public void validate_get_status_code() {

        given().
                baseUri("https://api.postman.com").
                header("X-Api-Key", "PMAK-6471cc222c311653f84572da-44bc7a8b8b42551de8b0d56e5f1f89f520").
                when().
                get("/workspaces").
                then().
                assertThat().
                statusCode(200).log().all();


    }

    @Test
    public void validate_response_body() {

        given().
                baseUri("https://api.postman.com").
                header("X-Api-Key", "PMAK-6471cc222c311653f84572da-44bc7a8b8b42551de8b0d56e5f1f89f520").
                when().
                get("/workspaces").
                then().log().all().
                assertThat().
                statusCode(200).body("workspaces.name", hasItems("My Workspace2", "Team Workspace",
                                "Udemy Course"), "workspaces.type", hasItems("personal", "team", "team"),
                        "workspaces[0].name", equalTo("My Workspace2"),
                        "workspaces[0].name", is(equalTo("My Workspace2")),
                        "workspaces.size()", equalTo(3),
                        "workspaces.name", hasItems("My Workspace2"));

    }

    @Test
    public void extract_response() {

        Response res = given().
                baseUri("https://api.postman.com").
                header("X-Api-Key", "PMAK-6471cc222c311653f84572da-44bc7a8b8b42551de8b0d56e5f1f89f520").
                when().
                get("/workspaces").
                then().
                assertThat().
                statusCode(200).
                extract().
                response();
        System.out.println("Response = " + res.asString());


    }

    @Test
    public void extract_single_value_from_response() {

        String response = given().
                baseUri("https://api.postman.com").
                header("X-Api-Key", "PMAK-6471cc222c311653f84572da-44bc7a8b8b42551de8b0d56e5f1f89f520").
                when().
                get("/workspaces").
                then().
                assertThat().
                statusCode(200).
                extract().
                response().asString();

        System.out.println("Response = " + JsonPath.from(response).getString("workspaces[0].name"));


    }

    @Test
    public void hamcrest_assert_on_extracted_response() {

        String name = given().
                baseUri("https://api.postman.com").
                header("X-Api-Key", "PMAK-6471cc222c311653f84572da-44bc7a8b8b42551de8b0d56e5f1f89f520").
                when().
                get("/workspaces").
                then().
                assertThat().
                statusCode(200).
                extract().
                response().path("workspaces[0].name");

        System.out.println("workspace name = " + name);

//        assertThat(name, equalTo("My Workspace2"));
        Assert.assertEquals(name, "My Workspace2");


    }

    @Test
    public void validate_response_body_hamcrest_learnings() {

        given().
                baseUri("https://api.postman.com").
                header("X-Api-Key", "PMAK-6471cc222c311653f84572da-44bc7a8b8b42551de8b0d56e5f1f89f520").
                when().
                get("/workspaces").
                then().log().all().
                assertThat().
                statusCode(200).body("workspaces.name", containsInAnyOrder("My Workspace2", "Udemy Course",
                                "Team Workspace"),
                        "workspaces.name", is(not(emptyArray())),
                        "workspaces.name", hasSize(3),
                        "workspaces[0]", hasKey("id"),
                        "workspaces[0]", hasValue("My Workspace2"),
                        "workspaces[0]", hasEntry("id", "acf16991-83c1-4851-855b-5732a16074c4"),
                        "workspaces[0]", not(equalTo(Collections.EMPTY_MAP)),
                        "workspaces[0].name", allOf(startsWith("My"), containsString("Workspace2")));
    }

    @Test
    public void request_response_logging() {
        given().
                baseUri("https://api.postman.com").
                header("X-Api-Key", "PMAK-6471cc222c311653f84572da-44bc7a8b8b42551de8b0d56e5f1f89f520").
                log().all().
                when().
                get("/workspaces").
                then().
                log().all().
                assertThat().
                statusCode(200);
    }

    @Test
    public void log_only_if_error() {
        given().
                baseUri("https://api.postman.com").
                header("X-Api-Key", "PMAK-6471cc222c311653f84572da-44bc7a8b8b42551de8b0d56e5f1f89f520").
                log().all().
                when().
                get("/workspaces").
                then().
                log().ifError().
                assertThat().
                statusCode(200);
    }

    @Test
    public void log_only_if_validation_fails() {

        given().
                baseUri("https://api.postman.com").
                header("X-Api-Key", "PMAK-6471cc222c311653f84572da-44bc7a8b8b42551de8b0d56e5f1f89f520").
                config(config.logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails())).
//                log().ifValidationFails().
        when().
                get("/workspaces").
                then().
//                log().ifValidationFails().
        assertThat().
                statusCode(200);
    }


    @Test
    public void logs_blacklist_header() {
        Set<String> headers = new HashSet<String>();
        headers.add("X-Api-Key");
        headers.add("Accept");
        given().
                baseUri("https://api.postman.com").
                header("X-Api-Key", "PMAK-6471cc222c311653f84572da-44bc7a8b8b42551de8b0d56e5f1f89f520").
                config(config.logConfig(LogConfig.logConfig().blacklistHeaders(headers))).
                log().all().
                when().
                get("/workspaces").
                then().
                assertThat().
                statusCode(200);
    }


}
