package gatling.demo

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration.DurationInt

/**
 * Created by Efrem Dubrovin - Delta Source Bulgaria on 05/02/2021.
 */
class CheckJsonPathScenario extends Simulation {

	val scn = scenario("Video game DB")

		.exec(http("Get specific video game")
			.get("videogames/1")
			.check(jsonPath("$.name").is("Resident Evil 4"))
		)

	setUp(scn.inject(atOnceUsers(1))
		.protocols(http
			.baseUrl("http://localhost:8080/app/")
			.acceptHeader("application/json")))

}
