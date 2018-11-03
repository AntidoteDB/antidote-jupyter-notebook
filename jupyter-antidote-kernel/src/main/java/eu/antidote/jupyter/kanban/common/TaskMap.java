package eu.antidote.jupyter.kanban.common;

public class TaskMap {

	private String task_title;
	private ColumnId column_id;
	private String due_date;
	
	public TaskMap(String tasktitle, ColumnId columnid, String duedate) {
		column_id = columnid;
		task_title = tasktitle;
		due_date = duedate;
	}
	
	public String toString() {
		return "Task Name - " + task_title + "\n" + "Column Id of task - " + column_id + "\n" + "Due Date - " + due_date;
	}
}
