package gradle_project_start.app

import gradle_project_start.app.formats.JacksonMessage
import gradle_project_start.app.formats.jacksonMessageLens
import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.then
import org.http4k.core.with
import org.http4k.filter.DebuggingFilters.PrintRequest
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.core.*
import org.http4k.filter.DebuggingFilters
import org.http4k.server.SunHttp
import org.http4k.server.asServer

class ApiServer {

    val app: HttpHandler = routes(
        "/ping" bind Method.GET to {
            Response(Status.OK).body("pong")
        },

        "/formats/json/jackson" bind Method.GET to {
            Response(Status.OK).with(jacksonMessageLens of JacksonMessage("Barry", "Hello there!"))
        },

        "/testing/strikt" bind Method.GET to { request ->
            Response(Status.OK).body("Echo '${request.bodyString()}'")
        }
    )
    val printingApp: HttpHandler = DebuggingFilters.PrintRequest().then(app)
    val server = printingApp.asServer(SunHttp(9000)).start()

    init {
        println("Server started on " + server.port())
    }

}