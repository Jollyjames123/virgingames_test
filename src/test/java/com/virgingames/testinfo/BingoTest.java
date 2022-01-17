package com.virgingames.testinfo;

import com.virgingames.constants.EndPoints;
import com.virgingames.constants.Path;
import com.virgingames.testbase.TestBase;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import org.junit.Assert;
import org.junit.Test;

import java.security.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Date;
import java.util.Calendar;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

public class BingoTest extends TestBase {
    /**
     * Back End Automation:
     * - Create a project using Java that contains test(s) for the following endpoint:
     * 'https://www.virgingames.com/bingo/GetBingoLobbyFeed.do'
     * e.g. 'defaultGameFrequency' should always be '300000' or
     * 'startTime' should always be future timestamp or any other values you can assert.
     */

    @Test
    public void test001() {

        ValidatableResponse response = SerenityRest.given().log().all()
                .when()
                .get(Path.BINGO + EndPoints.LOBBY_FEED)
                .then().statusCode(200);

        List<String> gameFrequencyList = response.extract().jsonPath().get("bingoLobbyInfoResource.streams.defaultGameFrequency");

        //Printing the extracted list of defalutGamingFrequency
        System.out.println(gameFrequencyList);

        for (Object gameFrequency : gameFrequencyList) {
            if (gameFrequency != null) {
                Assert.assertThat(gameFrequency, equalTo(300000));
                System.out.println("DefaultGameFrequency is " + gameFrequency);
            } else {
                System.out.println("DefaultGameFrequency is null");
            }

        }

//        for (Object gameFrequency : gameFrequencyList) {
//                Assert.assertThat(gameFrequency, equalTo(300000));
//              }
    }

    @Test
    public void test002() {

        ValidatableResponse response = SerenityRest.given().log().all()
                .when()
                .get(Path.BINGO + EndPoints.LOBBY_FEED)
                .then().statusCode(200);

        long currentTimeStamp = response.extract().jsonPath().get("timestamp");
        System.out.println(currentTimeStamp);
        System.out.println(new Date(currentTimeStamp));
      Date date = new Date(currentTimeStamp);
      // System.out.println(date);


        List<Long> startTimeList = response.extract().jsonPath().get("bingoLobbyInfoResource.streams.startTime");
        System.out.println(startTimeList);
        for (long eachStartTime : startTimeList) {
            System.out.println("The current time is: "+date);
            System.out.println("The start time is: "+new Date(eachStartTime));
           //System.out.println("*************************************************");
            System.out.println("\n");
                        Assert.assertThat(eachStartTime, greaterThan(currentTimeStamp));
        }
    }
}
