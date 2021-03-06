package gatling.demo

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration.DurationInt

/**
 * Created by Efrem Dubrovin - Delta Source Bulgaria on 05/02/2021.
 */
class DemoScenario extends Simulation {

	val scn = scenario("Video game DB")
		.exec(http("Get all video games")
			.get("videogames")
			.check(status.is(200)))
		.pause(5)

		.exec(http("Get specific video game")
			.get("videogames/1")
			.check(status.in(200, 210))
		)
		.pause(1, 5)

		.exec(http("Get all video games")
			.get("videogames")
			.check(status.not(404), status.not(500))
		)
		.pause(3000.milliseconds)

	setUp(scn.inject(atOnceUsers(1))
		.protocols(http
			.baseUrl("http://localhost:8080/app/")
			.acceptHeader("application/json")))

}
