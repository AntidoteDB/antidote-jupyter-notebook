package eu.antidote.jupyter.kanban.common;


import java.util.List;

public class ColumnMap {

	public String column_name;
	public BoardId board_id;
	public List<TaskId> taskids;
	public List<TaskMap> tasks;
	
	public ColumnMap(String columnname, BoardId boardid, List<TaskId> taskid_list, List<TaskMap> task_list) {
		board_id = boardid;
		column_name = columnname;
		taskids = taskid_list;
		tasks = task_list;
	}
	
	public String toString() {
		return "Column Name - " + column_name + "\n" + "Board Id of column - " + board_id + "\n" + "List of TaskIds - " + taskids + "\n" + "List of Tasks - " + tasks;
	} 
}
