package eu.antidote.jupyter.kanban.common;

import java.util.List;

public class BoardMap {
	public String board_name;
	public List<ColumnId> columnids;
	public List<ColumnMap> columns;
	
	public BoardMap(String boardname, List<ColumnId> columnid_list, List<ColumnMap> column_list) {
		board_name = boardname;
		columnids = columnid_list;
		columns = column_list;
	}

	public String toString() {
		return "Board Name - " + board_name + "\n" + "List of Column Ids - " + columnids + "\n" + "List of Columns - " + columns;
	}	
}
