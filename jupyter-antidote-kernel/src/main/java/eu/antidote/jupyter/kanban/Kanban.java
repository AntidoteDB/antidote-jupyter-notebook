package eu.antidote.jupyter.kanban;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Date;
import java.util.List;

import eu.antidotedb.client.AntidoteClient;
import eu.antidote.jupyter.kanban.common.*;

public class Kanban {

    public Kanban(AntidoteClient currentSession) {
        this.currentSession = currentSession;
    }
	
	AntidoteClient currentSession;
	
	Board board = new Board();
	Column column = new Column();
	Task task = new Task();


	public String hello() {
	    return "Hello, World!";
	}
	
	
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
		return column.addColumn(currentSession, board_id, name);
	}

	
	public void renamecolumn(ColumnId column_id, String newName) {
		column.renameColumn(currentSession, column_id, newName);
	}

	
	public void deletecolumn(ColumnId column_id) {
		column.deleteColumn(currentSession, column_id);

	}

	 
	public TaskId createtask(String title, ColumnId column_id) {
		return task.createTask(currentSession, column_id, title);
	}
	
	
	public void updatetitle(TaskId task_id, String newTitle) {
		task.updateTitle(currentSession, task_id, newTitle);
	}	
	
	
	public void updateduedate(TaskId task_id, Date dueDate ) {
		task.updateDueDate(currentSession, task_id, dueDate);
	}
	
	
	public void movetask(TaskId task_id, ColumnId newcolumn_id) {
		task.moveTask(currentSession, task_id, newcolumn_id);
	}

	
	public void deletetask(TaskId task_id) {
		task.deleteTask(currentSession, task_id);
	}
	
	
	public BoardMap getboard(BoardId board_id) {
		return board.getBoard(currentSession, board_id);
	}
	
	
	public ColumnMap getcolumn(ColumnId column_id) {
		return column.getColumn(currentSession, column_id);
	}

	
	public TaskMap gettask(TaskId task_id) {
		return task.getTask(currentSession, task_id);
	}


}
