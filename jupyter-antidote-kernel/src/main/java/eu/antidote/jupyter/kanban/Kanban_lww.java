package eu.antidote.jupyter.kanban;

import eu.antidote.jupyter.kanban.common.*;
import eu.antidotedb.client.AntidoteClient;

import java.util.Date;
import java.util.List;

public class Kanban_lww {

    public Kanban_lww(AntidoteClient currentSession) {
        this.currentSession = currentSession;
    }
	
	AntidoteClient currentSession;
	
	Board board = new Board();

	
	public UserId createuser(String email) {
		return (new User().createUser(currentSession, email));
	}
	
	
	public List<BoardId> listboards() {
		return board.listBoards();
	}

	
	public BoardId createboard(String name) {
		return board.createBoard(currentSession, name);
	}

	
	public void renameboard(BoardId board_id, String newName) {
		 board.renameBoard(currentSession, board_id, newName);
	}

	
	public ColumnId addcolumn(BoardId board_id, String name) {

		return Column_lww.addColumn(currentSession, board_id, name);
	}

	
	public void renamecolumn(ColumnId column_id, String newName) {
		(new Column_lww(column_id)).renameColumn(currentSession, column_id, newName);
	}

	
	public void deletecolumn(ColumnId column_id) {
		(new Column_lww(column_id)).deleteColumn(currentSession, column_id);

	}

	 
	public void createtask(String title, ColumnId column_id) {
		(new Column_lww(column_id)).createTask(currentSession, column_id, title);
	}
	
//	public void movetask(TaskId task_id, ColumnId newcolumn_id) {
//		task.moveTask(currentSession, task_id, newcolumn_id);
//	}

	
//	public void deletetask(TaskId task_id) {
//		task.deleteTask(currentSession, task_id);
//	}
	
	
	public BoardMap getboard(BoardId board_id) {
		return board.getBoard_x(currentSession, board_id);
	}
	
	
	public ColumnMap_lww getcolumn(ColumnId column_id) {
		return (new Column_lww(column_id) ).getColumn(currentSession, column_id);
	}


}
