package gradle_project_start.app

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import gradle_project_start.app.formats.JacksonMessage
import gradle_project_start.todoapp.ToDoApp
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.BAD_REQUEST
import org.http4k.core.Status.Companion.OK
import org.http4k.strikt.bodyString
import org.http4k.strikt.status
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class todoapp_apiTest {

    private var toDoApp = ToDoApp()
    var server = create(toDoApp)

    @BeforeEach
    fun setUp() {
        toDoApp = ToDoApp()
        server = create(toDoApp)
    }

    @Test
    fun `Ping test`() {
        assertEquals(server(Request(GET, "/ping")), Response(OK).body("pong"))
    }

    @Test
    fun `Check Strikt matcher for http4k work as expected`() {
        val request = Request(GET, "/testing/strikt?a=b").body("http4k is cool").header("my header", "a value")
        val response = server(request)

        // response assertions
        expectThat(response).status.isEqualTo(OK)
        expectThat(response).bodyString.isEqualTo("Echo 'http4k is cool'")
    }

    @Test
    fun `route getToDoListFormatJSON should return the todolist in JSON Format`() {
        toDoApp.addNewTask(ToDoApp.TaskToDo("task3", false))
        toDoApp.addNewTask(ToDoApp.TaskToDo("task2", false))
        toDoApp.addNewTask(ToDoApp.TaskToDo("task1", true))

        val request = Request(GET, "/getToDoListFormatJSON")
        val response = server(request)
        val mapper = jacksonObjectMapper()
        val jacksonObj: JacksonMessage = mapper.readValue<JacksonMessage>(response.bodyString())
        val returnedList: Array<ToDoApp.TaskToDo> = mapper.readValue<Array<ToDoApp.TaskToDo>>(jacksonObj.message)

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

    // Format /addTask?text='a_new_task'
    @Test
    fun `route addTask should add task print in url`() {
        toDoApp.addNewTask(ToDoApp.TaskToDo("task3", false))
        toDoApp.addNewTask(ToDoApp.TaskToDo("task2", false))
        toDoApp.addNewTask(ToDoApp.TaskToDo("task1", true))

        val request = Request(GET, "/addTask?text=task0")
        val response = server(request)

        // response assertions
        expectThat(response).status.isEqualTo(OK)
        expectThat(toDoApp.getActualToDoList().size).isEqualTo(4)
        expectThat(toDoApp.getActualToDoList()[0]?.name).isEqualTo("task0")
        expectThat(toDoApp.getActualToDoList()[0]?.isCheck).isEqualTo(false)

    }

    @Test
    fun `route addTask should return status_BAD_REQUEST if no text input`() {
        toDoApp.addNewTask(ToDoApp.TaskToDo("task3", false))
        toDoApp.addNewTask(ToDoApp.TaskToDo("task2", false))
        toDoApp.addNewTask(ToDoApp.TaskToDo("task1", true))

        val request = Request(GET, "/addTask")
        val response = server(request)

        // response assertions
        expectThat(response).status.isEqualTo(BAD_REQUEST)

    }

    @Test
    fun `route removeTask should remove task in taskList`() {
        toDoApp.addNewTask(ToDoApp.TaskToDo("task3", false))
        toDoApp.addNewTask(ToDoApp.TaskToDo("task2", false))
        toDoApp.addNewTask(ToDoApp.TaskToDo("task1", true))

        val request = Request(GET, "/removeTask?id=1")
        val response = server(request)

        // response assertions
        expectThat(response).status.isEqualTo(OK)

        val request2 = Request(GET, "/getToDoListFormatJSON")
        val response2 = server(request2)
        val mapper = jacksonObjectMapper()
        val jacksonObj: JacksonMessage = mapper.readValue<JacksonMessage>(response2.bodyString())
        val returnedList: Array<ToDoApp.TaskToDo> = mapper.readValue<Array<ToDoApp.TaskToDo>>(jacksonObj.message)

        expectThat(returnedList.size).isEqualTo(2)
        expectThat(returnedList[0].name).isEqualTo("task2")
        expectThat(returnedList[0].isCheck).isEqualTo(false)
        expectThat(returnedList[1].name).isEqualTo("task1")
        expectThat(returnedList[1].isCheck).isEqualTo(true)

    }

    @Test
    fun `route removeTask should return status_BAD_REQUEST if wrong or none input`() {
        toDoApp.addNewTask(ToDoApp.TaskToDo("task3", false))
        toDoApp.addNewTask(ToDoApp.TaskToDo("task2", false))
        toDoApp.addNewTask(ToDoApp.TaskToDo("task1", true))

        var request = Request(GET, "/removeTask?id=test")
        var response = server(request)

        // response assertions
        expectThat(response).status.isEqualTo(BAD_REQUEST)

        request = Request(GET, "/removeTask?id=3.45")
        response = server(request)

        // response assertions
        expectThat(response).status.isEqualTo(BAD_REQUEST)

        request = Request(GET, "/removeTask")
        response = server(request)

        // response assertions
        expectThat(response).status.isEqualTo(BAD_REQUEST)
    }

    @Test
    fun `route checkTask should remove task in taskList`() {
        toDoApp.addNewTask(ToDoApp.TaskToDo("task3", false))
        toDoApp.addNewTask(ToDoApp.TaskToDo("task2", false))
        toDoApp.addNewTask(ToDoApp.TaskToDo("task1", true))

        val request = Request(GET, "/checkTask?id=1")
        val response = server(request)

        // response assertions
        expectThat(response).status.isEqualTo(OK)

        val request2 = Request(GET, "/getToDoListFormatJSON")
        val response2 = server(request2)
        val mapper = jacksonObjectMapper()
        val jacksonObj: JacksonMessage = mapper.readValue<JacksonMessage>(response2.bodyString())
        val returnedList: Array<ToDoApp.TaskToDo> = mapper.readValue<Array<ToDoApp.TaskToDo>>(jacksonObj.message)

        expectThat(returnedList.size).isEqualTo(3)
        expectThat(returnedList[0].name).isEqualTo("task2")
        expectThat(returnedList[0].isCheck).isEqualTo(false)
        expectThat(returnedList[1].name).isEqualTo("task1")
        expectThat(returnedList[1].isCheck).isEqualTo(true)
        expectThat(returnedList[2].name).isEqualTo("task3")
        expectThat(returnedList[2].isCheck).isEqualTo(true)

    }

    @Test
    fun `route checkTask should return status_BAD_REQUEST if wrong or none input`() {
        toDoApp.addNewTask(ToDoApp.TaskToDo("task3", false))
        toDoApp.addNewTask(ToDoApp.TaskToDo("task2", false))
        toDoApp.addNewTask(ToDoApp.TaskToDo("task1", true))

        var request = Request(GET, "/checkTask?id=test")
        var response = server(request)

        // response assertions
        expectThat(response).status.isEqualTo(BAD_REQUEST)

        request = Request(GET, "/checkTask?id=3.45")
        response = server(request)

        // response assertions
        expectThat(response).status.isEqualTo(BAD_REQUEST)

        request = Request(GET, "/checkTask")
        response = server(request)

        // response assertions
        expectThat(response).status.isEqualTo(BAD_REQUEST)
    }

    @Test
    fun `route unCheckTask should remove task in taskList`() {
        toDoApp.addNewTask(ToDoApp.TaskToDo("task3", true))
        toDoApp.addNewTask(ToDoApp.TaskToDo("task2", false))
        toDoApp.addNewTask(ToDoApp.TaskToDo("task1", true))

        val request = Request(GET, "/unCheckTask?id=1")
        val response = server(request)

        // response assertions
        expectThat(response).status.isEqualTo(OK)

        val request2 = Request(GET, "/getToDoListFormatJSON")
        val response2 = server(request2)
        val mapper = jacksonObjectMapper()
        val jacksonObj: JacksonMessage = mapper.readValue<JacksonMessage>(response2.bodyString())
        val returnedList: Array<ToDoApp.TaskToDo> = mapper.readValue<Array<ToDoApp.TaskToDo>>(jacksonObj.message)

        expectThat(returnedList.size).isEqualTo(3)
        expectThat(returnedList[0].name).isEqualTo("task1")
        expectThat(returnedList[0].isCheck).isEqualTo(false)
        expectThat(returnedList[1].name).isEqualTo("task2")
        expectThat(returnedList[1].isCheck).isEqualTo(false)
        expectThat(returnedList[2].name).isEqualTo("task3")
        expectThat(returnedList[2].isCheck).isEqualTo(true)

    }

    @Test
    fun `route unCheckTask should return status_BAD_REQUEST if wrong or none input`() {
        toDoApp.addNewTask(ToDoApp.TaskToDo("task3", false))
        toDoApp.addNewTask(ToDoApp.TaskToDo("task2", false))
        toDoApp.addNewTask(ToDoApp.TaskToDo("task1", true))

        var request = Request(GET, "/unCheckTask?id=test")
        var response = server(request)

        // response assertions
        expectThat(response).status.isEqualTo(BAD_REQUEST)

        request = Request(GET, "/unCheckTask?id=3.45")
        response = server(request)

        // response assertions
        expectThat(response).status.isEqualTo(BAD_REQUEST)

        request = Request(GET, "/unCheckTask")
        response = server(request)

        // response assertions
        expectThat(response).status.isEqualTo(BAD_REQUEST)
    }


}
