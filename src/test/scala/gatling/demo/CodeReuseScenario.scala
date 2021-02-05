package gatling.demo

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration.DurationInt

/**
 * Created by Efrem Dubrovin - Delta Source Bulgaria on 05/02/2021.
 */
class CodeReuseScenario extends Simulation {

	def getAllVideoGames() = {
		repeat(3) {
			exec(http("Get all video games")
				.get("videogames")
				.check(status.is(200)))
		}
	}

	def getASpecificVideoGame() = {
		repeat(5) {
			exec(http("Get specific video game")
				.get("videogames/1")
				.check(status.in(200, 210)))
		}
	}

	val scn = scenario("Video game DB")
		.exec(getAllVideoGames())
		.pause(5)
		.exec(getASpecificVideoGame())
		.pause(5)

	setUp(scn.inject(atOnceUsers(1))
		.protocols(http
			.baseUrl("http://localhost:8080/app/")
			.acceptHeader("application/json")))

}
