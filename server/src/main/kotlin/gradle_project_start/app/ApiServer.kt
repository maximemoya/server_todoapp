package gradle_project_start.app

import gradle_project_start.app.formats.JacksonMessage
import gradle_project_start.app.formats.jacksonMessageLens
import org.http4k.core.*
import org.http4k.core.Status.Companion.OK
import org.http4k.filter.DebuggingFilters
import org.http4k.routing.bind
import org.http4k.routing.routes
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

//    val handler = {request: Request -> Response(OK).body("hello, ${request.query("name")}")}
//    val get = Request(Method.GET, "/").query("name", "John Doe")
//    val response = handler(get)

    init {
        println("Server started on ${server.port()}")
//        { request: Request -> Response(OK).body("Hello World") }.asServer(SunHttp(9000)).start()
//        println("${response.status}")
//        println(response.bodyString())
    }

}