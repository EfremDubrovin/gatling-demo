package gatling.demo

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
 * Created by Efrem Dubrovin - Delta Source Bulgaria on 05/02/2021.
 */
class CsvFeeder extends Simulation {

	val csvFeeder = csv("data/gameDb.csv").circular

	def getSpecificVideoGame() = {
		repeat(10) {
			feed(csvFeeder)
				.exec(http("Get specific video game")
				.get("videogames/${gameId}")
				.check(jsonPath("$.name").is("${gameName}"))
				.check(status.is(200)))
				.pause(2)
		}
	}

	val scn = scenario("Video game DB")
		.exec(getSpecificVideoGame())

	setUp(scn.inject(atOnceUsers(1))
		.protocols(http
			.baseUrl("http://localhost:8080/app/")
			.acceptHeader("application/json")))
}
