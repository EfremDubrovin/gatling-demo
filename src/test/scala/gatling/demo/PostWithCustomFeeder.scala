package gatling.demo

import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import io.gatling.http.Predef._

import java.time.{LocalDate, ZonedDateTime}
import java.time.format.DateTimeFormatter
import scala.util.Random

/**
 * Created by Efrem Dubrovin - Delta Source Bulgaria on 05/02/2021.
 */
class PostWithCustomFeeder extends Simulation {

	var idNumbers = (11 to 20).iterator
	val rnd = new Random()
	val now = LocalDate.now()
	val pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd")

	def randomString(length: Int) = {
		rnd.alphanumeric.filter(_.isLetter).take(length).mkString
	}

	def getRandomDate(startDate: LocalDate, random: Random): String = {
		startDate.minusDays(random.nextInt(30)).format(pattern)
	}

	val customFeeder = Iterator.continually(Map(
		"gameId" -> idNumbers.next(),
		"name" -> ("Game-" + randomString(5)),
		"releaseDate" -> getRandomDate(now, rnd),
		"reviewScore" -> rnd.nextInt(100),
		"category" -> ("Category-" + randomString(6)),
		"rating" -> ("Rating-" + randomString(4))
	))

	def getSpecificVideoGame() = {
		repeat(10) {
			feed(customFeeder)
				.exec(http("Create video game")
				.post("videogames/")
				.body(ElFileBody("bodies/postBody.json")).asJson
				.check(status.is(200)))
				.pause(1)
		}
	}

	val scn = scenario("Video game DB")
		.exec(getSpecificVideoGame())

	setUp(scn.inject(atOnceUsers(1))
		.protocols(http
			.baseUrl("http://localhost:8080/app/")
			.acceptHeader("application/json")))
}
