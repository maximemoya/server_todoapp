package gradle_project_start.todoapp

class ToDoApp {

    data class TaskToDo(
        val name: String,
        var isCheck: Boolean
    )

    enum class SortingSelectionEnum {
        BYNAME, BYCHECKEDTHENBYNAME
    }

    //----------------------
    // ATTRIBUTES PRIVATES :
    //---------------------

    private var toDoList = arrayOf<TaskToDo?>()
    private var sortingSelection = SortingSelectionEnum.BYCHECKEDTHENBYNAME.ordinal

    // -------------
    //  ADD METHOD
    // -------------

    fun addNewTask(task: TaskToDo) {
        toDoList = toDoList.copyOf(newSize = (toDoList.size + 1))
        toDoList[toDoList.size - 1] = task
        sort()
    }

    // ----------------
    //  REMOVE METHOD
    // ----------------

    fun removeTaskById(idTask: Int) {
        when (idTask) {

            // remove first one :
            0 -> {
                toDoList = toDoList.copyOfRange(1, toDoList.size)
            }
            // remove last one :
            toDoList.size - 1 -> {
                toDoList = toDoList.copyOfRange(0, toDoList.size - 1)
            }
            // remove inside :
            else -> {
                val listBefore = toDoList.copyOfRange(0,idTask)
                val listAfter = toDoList.copyOfRange(idTask + 1, toDoList.size)
                toDoList = listBefore + listAfter
            }

        }
        sort()
    }

    // ------------------
    //  CHECKBOX METHODS
    // ------------------

    fun checkATaskById(idTask: Int) {
        if (idTask < toDoList.size) {
            toDoList[idTask]?.isCheck = true
            toDoList[idTask]?.isCheck
        }
    }

    fun checkATaskByName(taskName: String) {
        toDoList.find { it?.name == taskName }?.isCheck = true
    }

    fun unCheckATaskById(idTask: Int) {
        if (idTask < toDoList.size) {
            toDoList[idTask]?.isCheck = false
        } else {
            true
        }
    }

    fun unCheckATaskByName(taskName: String) {
        toDoList.find { it?.name == taskName }?.isCheck = false
    }

    // ----------------
    //  SORT METHODS
    // ----------------

    fun setSortingSelection(sortingSelectionEnum: SortingSelectionEnum) {
        sortingSelection = sortingSelectionEnum.ordinal
        sort()
    }

    private fun sortToDoListByTaskNames() {
        toDoList.sortBy { it?.name }
    }

    private fun sortToDoListByTaskChecked() {
        toDoList.sortWith(compareBy<TaskToDo?> { it?.isCheck }.thenBy { it?.name })
    }

    private fun sort() {

        when (sortingSelection) {

            SortingSelectionEnum.BYNAME.ordinal -> {
                sortToDoListByTaskNames()
            }
            SortingSelectionEnum.BYCHECKEDTHENBYNAME.ordinal -> {
                sortToDoListByTaskChecked()
            }
            else -> {
                mutableListOf<TaskToDo>()
            }

        }

    }


    // -----------
    //  GETTERS :
    // ---------

    fun getActualToDoList(): Array<TaskToDo?> {
        return toDoList
    }
}