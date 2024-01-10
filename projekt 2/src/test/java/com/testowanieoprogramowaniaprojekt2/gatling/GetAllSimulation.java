package com.testowanieoprogramowaniaprojekt2.gatling;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.core.CoreDsl.stressPeakUsers;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;


public class GetAllSimulation extends Simulation {

    public GetAllSimulation() {

        HttpProtocolBuilder httpProtocolBuilder = http
                .baseUrl("http://localhost:8080")
                .acceptHeader("application/json");

        ScenarioBuilder scn = scenario("Get all tasks")
                .exec(http("Get all tasks")
                        .get("/tasks")
                        .check(status().is(200)));

        setUp(
                scn.injectOpen(
                        stressPeakUsers(100000).during(300)
                ).protocols(httpProtocolBuilder)
        );

    }

}
