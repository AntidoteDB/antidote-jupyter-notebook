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
		StringBuilder res = new StringBuilder();
		res.append("\nColumn Name: " + column_name);

		if (taskids != null)
			res.append("\nList of TaskIds:   " + taskids);

		if (tasks != null)
			res.append("\nList of Tasks:   " + taskids);

		return  res.toString();
	}
}
