package gradle_project_start.app

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import gradle_project_start.app.formats.JacksonMessage
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
import strikt.assertions.*

class todoapp_apiTest {

    val toDoApp = ToDoApp()
    var server = create(toDoApp)

//    @BeforeEach
//    fun setUp() {
//        server = create(toDoApp)
//    }

    @Test
    fun `Ping test`() {
        assertEquals(server(Request(GET, "/ping")), Response(OK).body("pong"))
    }

    @Test
    fun `route getFirstTaskText should return the first task name of the todolist`() {
        toDoApp.addNewTask(ToDoApp.TaskToDo("task3", false))
        toDoApp.addNewTask(ToDoApp.TaskToDo("task2", false))
        toDoApp.addNewTask(ToDoApp.TaskToDo("task1", true))

        val request = Request(GET, "/getFirstTaskText")
        val response = server(request)

        // response assertions
        expectThat(response).status.isEqualTo(OK)
        expectThat(response).bodyString.isEqualTo("{\"subject\":\"todolist\",\"message\":\"task2\"}")

    }

    @Test
    fun `route getToDoListFormatJSON should return the todolist in JSON Format`() {
        toDoApp.addNewTask(ToDoApp.TaskToDo("task3", false))
        toDoApp.addNewTask(ToDoApp.TaskToDo("task2", false))
        toDoApp.addNewTask(ToDoApp.TaskToDo("task1", true))

        val request = Request(GET, "/getToDoListFormatJSON")
        val response = server(request)
        val mapper = jacksonObjectMapper()
        val jacksonobj : JacksonMessage = mapper.readValue<JacksonMessage>(response.bodyString())
        val returnedList: Array<ToDoApp.TaskToDo> = mapper.readValue<Array<ToDoApp.TaskToDo>>(jacksonobj.message)

        // response assertions
        expectThat(response).status.isEqualTo(OK)
        expectThat(returnedList.size).isEqualTo(3)
        expectThat(returnedList[0].name).isEqualTo(toDoApp.getActualToDoList()[0]?.name)
        expectThat(returnedList[0].isCheck).isEqualTo(toDoApp.getActualToDoList()[0]?.isCheck)
        expectThat(returnedList[1].name).isEqualTo(toDoApp.getActualToDoList()[1]?.name)
        expectThat(returnedList[1].isCheck).isEqualTo(toDoApp.getActualToDoList()[1]?.isCheck)
        expectThat(returnedList[2].name).isEqualTo(toDoApp.getActualToDoList()[2]?.name)
        expectThat(returnedList[2].isCheck).isEqualTo(toDoApp.getActualToDoList()[2]?.isCheck)

    }

    @Test
    fun `Check Strikt matcher for http4k work as expected`() {
        val request = Request(GET, "/testing/strikt?a=b").body("http4k is cool").header("my header", "a value")
        val response = server(request)

        // response assertions
        expectThat(response).status.isEqualTo(OK)
        expectThat(response).bodyString.isEqualTo("Echo 'http4k is cool'")
    }

}
