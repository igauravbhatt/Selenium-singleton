package test;

import org.testng.Assert;
import org.testng.annotations.Test;

import groovyjarjarantlr4.v4.parse.ANTLRParser.id_return;
import io.restassured.response.Response;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;


public class AutomationAdvance_Rest {
//framework and next tasks
    //RestAPI
    //Data from file + data provider
    //priority and option
    //trigger from maven and jenkins
    //azure or aws
    //screenshot
    //reports
    //listeners
    //BDD
    //parallel execution
    //common methods - click. wait, ss, page factory
    //

    //Rest ASSURED
    @Test
    public void basics1(){
        Response resp = get("https://jsonplaceholder.typicode.com/posts");
        int statuscode = resp.getStatusCode();
        //System.out.println(resp.getTime()+resp.getBody().asString());
        System.out.println(resp.getStatusLine());
        System.out.println(resp.getContentType()+"ok");
          
        Assert.assertEquals(statuscode, 200);
        System.out.println("Rest API started");
        
    }

    @Test
    public void basics2(){
        baseURI = "https://jsonplaceholder.typicode.com";
        given()
            .get("/posts")
        .then()
            .statusCode(200)
            .body("id[1]",equalTo(2))
            .log().all();
            }

    @Test
    public void getAndPost(){
        Response resp;
        //get
        baseURI = "https://jsonplaceholder.typicode.com";
        given().
            get("/posts").
        then().
            statusCode(200).
            body("title[0]",containsString("sunt aut"));
            //log().all();
            
            Response response = given().get("/posts");
            System.out.println(response.jsonPath().getString("userId[2].id"));
            
        //post
        Map<String,Object> map = new HashMap<>();
        //map.put("", map);
        //test
    }
}