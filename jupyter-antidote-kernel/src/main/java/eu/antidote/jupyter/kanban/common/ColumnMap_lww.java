package eu.antidote.jupyter.kanban.common;


import java.util.List;

public class ColumnMap_lww {

	public String column_name;
	public BoardId board_id;
	public List<String> tasks;

	public ColumnMap_lww(String columnname, BoardId boardid, List<String> task_list) {
		board_id = boardid;
		column_name = columnname;
		tasks = task_list;
	}

	public String toString() {
		StringBuilder res = new StringBuilder();
		res.append("\nColumn Name: " + column_name);

		if (tasks != null)
			res.append("\nList of Tasks:   " + tasks);

		return  res.toString();
	} 
}
