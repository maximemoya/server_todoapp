package gradle_project_start.app

import gradle_project_start.app.ApiServer
import gradle_project_start.todoapp.ToDoApp
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.strikt.bodyString
import org.http4k.strikt.status
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class todoapp_apiTest {

    var app = create(ToDoApp())

    @BeforeEach
    fun setUp(){
        app = create(ToDoApp())
    }

    @Test
    fun `Ping test`() {
        assertEquals(app(Request(GET, "/ping")), Response(OK).body("pong"))
    }
    @Test
    fun `Check Strikt matcher for http4k work as expected`() {
        val request = Request(GET, "/testing/strikt?a=b").body("http4k is cool").header("my header", "a value")

        val response = app(request)

        // response assertions
        expectThat(response).status.isEqualTo(OK)
        expectThat(response).bodyString.isEqualTo("Echo 'http4k is cool'")
    }

}
