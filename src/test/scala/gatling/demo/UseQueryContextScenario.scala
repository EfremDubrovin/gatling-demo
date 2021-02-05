package gatling.demo

import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
 * Created by Efrem Dubrovin - Delta Source Bulgaria on 05/02/2021.
 */
class UseQueryContextScenario extends Simulation {

	val scn = scenario("Video game DB")

		.exec(http("Get all games")
			.get("videogames")
			.check(jsonPath("$[1].id").saveAs("gameId"))
		)
		.exec({
			session =>
				println(session)
				session
		})
		.exec(http("get second game")
			.get("videogames/${gameId}")
			.check(jsonPath("$.name").is("Gran Turismo 3"))
			.check(bodyString.saveAs("responseBody"))
		)
		.exec({
			session =>
				println(session("responseBody").as[String])
				session
		})

	setUp(scn.inject(atOnceUsers(1))
		.protocols(http
			.baseUrl("http://localhost:8080/app/")
			.acceptHeader("application/json")))

}
