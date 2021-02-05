import gatling.demo.{CheckJsonPathScenario, CodeReuseScenario, DemoScenario, UseQueryContextScenario}
import io.gatling.app.Gatling
import io.gatling.core.config.GatlingPropertiesBuilder

/**
 * Main runner class - runs a specific scenario class
 *
 * Created by Efrem Dubrovin - Delta Source Bulgaria on 05/02/2021.
 */
object GatlingRunner {

	def main(args: Array[String]): Unit = {
		val simClass = classOf[CodeReuseScenario].getName
		val props = new GatlingPropertiesBuilder
		props.simulationClass(simClass)

		Gatling.fromMap(props.build)
	}

}
