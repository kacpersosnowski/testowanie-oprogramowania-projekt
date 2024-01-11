package com.testowanieoprogramowaniaprojekt2.gatling;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static io.gatling.javaapi.core.CoreDsl.*;

public class GetByIdSimulation extends Simulation {

    public GetByIdSimulation() {

        HttpProtocolBuilder httpProtocolBuilder = http
                .baseUrl("http://localhost:8080")
                .acceptHeader("application/json");

        ScenarioBuilder scn = scenario("Get task by id")
                .exec(http("Get task by id")
                        .get("/tasks/1")
                        .check(status().is(200)));

        setUp(
                scn.injectOpen(
                        stressPeakUsers(100000).during(300)
                ).protocols(httpProtocolBuilder)
        );

    }

}
