package com.testowanieoprogramowaniaprojekt2.gatling;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class GetFilteredByTitleSimulation extends Simulation {

    public GetFilteredByTitleSimulation() {

        HttpProtocolBuilder httpProtocolBuilder = http
                .baseUrl("http://localhost:8080")
                .acceptHeader("application/json");


        ScenarioBuilder scn = scenario("Get tasks filtered by title")
        .exec(http("Get tasks filtered by title")
                .get("/tasks/filter/title?search=task")
                .check(status().is(200)));

        setUp(
                scn.injectOpen(
                        stressPeakUsers(100000).during(300)
                ).protocols(httpProtocolBuilder)
        );

    }

}
