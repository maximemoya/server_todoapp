import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class ToDoAppTest {

    // -------------
    //  CONSTRUCTOR
    // -------------

    private var myToDoApp = ToDoApp()

    @BeforeEach
    fun setUp(){
        myToDoApp = ToDoApp()
    }

    // The instantiation of ToDoApp class has one list<TaskToDo> empty
    @Test
    fun initToDoList() {
        // Execution :
        expectThat(myToDoApp.getActualToDoList().size).isEqualTo(0)
    }

    // -------------
    //  ADD METHODS
    // -------------

    // The method addNewTask, add one task to the toDoList
    @Test
    fun add1TaskToTheToDoList() {
        // Test method :
        myToDoApp.addNewTask(ToDoApp.TaskToDo("name0", false))
        // Execution :
        expectThat(myToDoApp.getActualToDoList().size).isEqualTo(1)
        expectThat(myToDoApp.getActualToDoList()[0]?.name).isEqualTo("name0")
        expectThat(myToDoApp.getActualToDoList()[0]?.isCheck).isEqualTo(false)
    }

    // The method add 2 tasks to the toDoList
    // sort by checked then alphabetical order
    @Test
    fun add2TasksToTheToDoList() {
        // Test method :
        myToDoApp.addNewTask(ToDoApp.TaskToDo("name0", false))
        myToDoApp.addNewTask(ToDoApp.TaskToDo("name1", false))
        // Execution :
        expectThat(myToDoApp.getActualToDoList().size).isEqualTo(2)
        expectThat(myToDoApp.getActualToDoList()[0]?.name).isEqualTo("name0")
        expectThat(myToDoApp.getActualToDoList()[0]?.isCheck).isEqualTo(false)
        expectThat(myToDoApp.getActualToDoList()[1]?.name).isEqualTo("name1")
        expectThat(myToDoApp.getActualToDoList()[1]?.isCheck).isEqualTo(false)
    }

    // The method add 3 tasks to the toDoList
    // sort by checked then alphabetical order
    @Test
    fun add3TasksToTheToDoList() {
        // Test method :
        myToDoApp.addNewTask(ToDoApp.TaskToDo("name2", true))
        myToDoApp.addNewTask(ToDoApp.TaskToDo("name0", true))
        myToDoApp.addNewTask(ToDoApp.TaskToDo("name1", false))
        // Execution :
        expectThat(myToDoApp.getActualToDoList().size).isEqualTo(3)
        expectThat(myToDoApp.getActualToDoList()[0]?.name).isEqualTo("name1")
        expectThat(myToDoApp.getActualToDoList()[0]?.isCheck).isEqualTo(false)
        expectThat(myToDoApp.getActualToDoList()[1]?.name).isEqualTo("name0")
        expectThat(myToDoApp.getActualToDoList()[1]?.isCheck).isEqualTo(true)
        expectThat(myToDoApp.getActualToDoList()[2]?.name).isEqualTo("name2")
        expectThat(myToDoApp.getActualToDoList()[2]?.isCheck).isEqualTo(true)
    }

    // ----------------
    //  REMOVE METHODS
    // ----------------

    // The method remove 1 task from the toDoList of 1 task, according input ID
    @Test
    fun removeTaskToTheToDoList() {
        myToDoApp.addNewTask(ToDoApp.TaskToDo("name0", false))
        // Test method :
        myToDoApp.removeTaskById(0)
        // Execution :
        expectThat(myToDoApp.getActualToDoList().size).isEqualTo(0)
    }

    // The method remove 1 task from the toDoList of 2 tasks, according input ID
    // get list sort by checked then alphabetical
    @Test
    fun remove1TaskToTheToDoList() {
        myToDoApp.addNewTask(ToDoApp.TaskToDo("name0", true))
        myToDoApp.addNewTask(ToDoApp.TaskToDo("name1",false))
        // Test method :
        myToDoApp.removeTaskById(0)
        // Execution :
        expectThat(myToDoApp.getActualToDoList().size).isEqualTo(1)
        expectThat(myToDoApp.getActualToDoList()[0]?.name).isEqualTo("name0")
        expectThat(myToDoApp.getActualToDoList()[0]?.isCheck).isEqualTo(true)
    }

    // The method remove 3 tasks from the toDoList of 5 tasks, according input ID
    // get list sort by checked then alphabetical
    @Test
    fun remove2TasksToTheToDoList() {
        myToDoApp.addNewTask(ToDoApp.TaskToDo("name0", true))
        myToDoApp.addNewTask(ToDoApp.TaskToDo("name1", true))
        myToDoApp.addNewTask(ToDoApp.TaskToDo("name3", false))
        myToDoApp.addNewTask(ToDoApp.TaskToDo("name2", false))
        myToDoApp.addNewTask(ToDoApp.TaskToDo("name4", false))
        // Test method :
        myToDoApp.removeTaskById(0)
        myToDoApp.removeTaskById(2)
        // Execution :
        expectThat(myToDoApp.getActualToDoList().size).isEqualTo(3)
        expectThat(myToDoApp.getActualToDoList()[0]?.name).isEqualTo("name3")
        expectThat(myToDoApp.getActualToDoList()[0]?.isCheck).isEqualTo(false)
        expectThat(myToDoApp.getActualToDoList()[1]?.name).isEqualTo("name4")
        expectThat(myToDoApp.getActualToDoList()[1]?.isCheck).isEqualTo(false)
        expectThat(myToDoApp.getActualToDoList()[2]?.name).isEqualTo("name1")
        expectThat(myToDoApp.getActualToDoList()[2]?.isCheck).isEqualTo(true)

    }

    // ------------------
    //  CHECKBOX METHODS
    // ------------------

    // The method checkATaskByIndex set the Task attribute ischeck to true
    @Test
    fun checkATaskByIndex() {
        myToDoApp.addNewTask(ToDoApp.TaskToDo("name0", false))
        // Test method :
        myToDoApp.checkATaskById(0)
        // Execution :
        expectThat(myToDoApp.getActualToDoList()[0]?.isCheck).isEqualTo(true)
    }

    // The method checkATaskByName set the Task attribute ischeck to true
    @Test
    fun checkATaskByName() {
        myToDoApp.addNewTask(ToDoApp.TaskToDo("name0", false))
        // Test method :
        myToDoApp.checkATaskByName("name0")
        // Execution :
        expectThat(myToDoApp.getActualToDoList()[0]?.isCheck).isEqualTo(true)
    }

    // The method unCheckATaskByIndex set the Task attribute ischeck to false
    @Test
    fun unCheckATaskByIndex() {
        // Context :
        val myToDoApp = ToDoApp()
        myToDoApp.addNewTask(ToDoApp.TaskToDo("name0", true))
        // Test method :
        myToDoApp.unCheckATaskById(0)
        // Execution :
        expectThat(myToDoApp.getActualToDoList()[0]?.isCheck).isEqualTo(false)
    }

    // The method unCheckATaskByName set the Task attribute ischeck to false
    @Test
    fun unCheckATaskByName() {
        myToDoApp.addNewTask(ToDoApp.TaskToDo("name0", true))
        // Test method :
        myToDoApp.unCheckATaskByName("name0")
        // Execution :
        expectThat(myToDoApp.getActualToDoList()[0]?.isCheck).isEqualTo(false)
    }

    // ----------------
    //  SORT METHODS
    // ----------------

    // The method setSortingSelection set the Sorting of the list referenced by the ToDoApp.SortingSelectionEnum :
    @Test
    fun changeListSortSelection() {
        myToDoApp.addNewTask(ToDoApp.TaskToDo("name0", true))
        myToDoApp.addNewTask(ToDoApp.TaskToDo("name3", false))
        myToDoApp.addNewTask(ToDoApp.TaskToDo("name2", true))
        myToDoApp.addNewTask(ToDoApp.TaskToDo("name5", false))
        myToDoApp.addNewTask(ToDoApp.TaskToDo("name4", false))
        myToDoApp.addNewTask(ToDoApp.TaskToDo("name1", false))
        // Test method :
        myToDoApp.setSortingSelection(ToDoApp.SortingSelectionEnum.BYNAME)
        // Execution :
        expectThat(myToDoApp.getActualToDoList().size).isEqualTo(6)
        expectThat(myToDoApp.getActualToDoList()[0]?.name).isEqualTo("name0")
        expectThat(myToDoApp.getActualToDoList()[0]?.isCheck).isEqualTo(true)
        expectThat(myToDoApp.getActualToDoList()[1]?.name).isEqualTo("name1")
        expectThat(myToDoApp.getActualToDoList()[1]?.isCheck).isEqualTo(false)
        expectThat(myToDoApp.getActualToDoList()[2]?.name).isEqualTo("name2")
        expectThat(myToDoApp.getActualToDoList()[2]?.isCheck).isEqualTo(true)
        expectThat(myToDoApp.getActualToDoList()[3]?.name).isEqualTo("name3")
        expectThat(myToDoApp.getActualToDoList()[3]?.isCheck).isEqualTo(false)
        expectThat(myToDoApp.getActualToDoList()[4]?.name).isEqualTo("name4")
        expectThat(myToDoApp.getActualToDoList()[4]?.isCheck).isEqualTo(false)
        expectThat(myToDoApp.getActualToDoList()[5]?.name).isEqualTo("name5")
        expectThat(myToDoApp.getActualToDoList()[5]?.isCheck).isEqualTo(false)
        // Test method :
        myToDoApp.setSortingSelection(ToDoApp.SortingSelectionEnum.BYCHECKEDTHENBYNAME)
        // Execution :
        expectThat(myToDoApp.getActualToDoList().size).isEqualTo(6)
        expectThat(myToDoApp.getActualToDoList()[0]?.name).isEqualTo("name1")
        expectThat(myToDoApp.getActualToDoList()[0]?.isCheck).isEqualTo(false)
        expectThat(myToDoApp.getActualToDoList()[1]?.name).isEqualTo("name3")
        expectThat(myToDoApp.getActualToDoList()[1]?.isCheck).isEqualTo(false)
        expectThat(myToDoApp.getActualToDoList()[2]?.name).isEqualTo("name4")
        expectThat(myToDoApp.getActualToDoList()[2]?.isCheck).isEqualTo(false)
        expectThat(myToDoApp.getActualToDoList()[3]?.name).isEqualTo("name5")
        expectThat(myToDoApp.getActualToDoList()[3]?.isCheck).isEqualTo(false)
        expectThat(myToDoApp.getActualToDoList()[4]?.name).isEqualTo("name0")
        expectThat(myToDoApp.getActualToDoList()[4]?.isCheck).isEqualTo(true)
        expectThat(myToDoApp.getActualToDoList()[5]?.name).isEqualTo("name2")
        expectThat(myToDoApp.getActualToDoList()[5]?.isCheck).isEqualTo(true)
    }

}