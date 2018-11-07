package eu.antidote.jupyter.kanban.common;

import java.util.List;

public class BoardMap {
	public String board_name;
	public List<ColumnId> columnids;
	public List<ColumnMap> columns;
	public List<ColumnMap_lww> columns_lww;


	public BoardMap(String boardname, List<ColumnId> columnid_list, List<ColumnMap> column_list) {
		board_name = boardname;
		columnids = columnid_list;
		columns = column_list;
	}
	public BoardMap(String boardname, List<ColumnId> columnid_list, List<ColumnMap_lww> column_list, boolean x) {
		board_name = boardname;
		columnids = columnid_list;
		columns_lww = column_list;
	}


	public String toString() {
		StringBuilder res = new StringBuilder();
		res.append("Board Name: " + board_name);

		res.append("\nList of ColumnIds: " + columnids);


		if (columns_lww != null)
			res.append("\nList of Columns:   " + columns_lww);

		if (columns != null)
			res.append("\nList of Columns:   " + columns);

		return  res.toString();
	}	
}
