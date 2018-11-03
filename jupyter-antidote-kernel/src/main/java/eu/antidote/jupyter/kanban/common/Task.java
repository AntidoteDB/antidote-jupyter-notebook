package eu.antidote.jupyter.kanban.common;


import static eu.antidotedb.client.Key.map_rr;
import static eu.antidotedb.client.Key.register;

import java.text.DateFormat;
import java.util.Date;
import eu.antidotedb.client.AntidoteClient;
import eu.antidotedb.client.Bucket;
import eu.antidotedb.client.InteractiveTransaction;
import eu.antidotedb.client.MapKey;
import eu.antidotedb.client.RegisterKey;
import eu.antidotedb.client.MapKey.MapReadResult;

public class Task {

	private static final RegisterKey<String> titlefield = register("Title");
	private static final RegisterKey<String> duedatefield = register("DueDate");
	private static final RegisterKey<ColumnId> columnidfield = register("ColumnId", new ColumnId.Coder());

	Bucket cbucket = Bucket.bucket("bucket");

	public TaskId task_id = null;

	public Task(TaskId task_id) {
		this.task_id = task_id;
	}

	public Task() {
	}

	public static MapKey taskMap(TaskId task_id) {
		return map_rr(task_id.getId());
	}

	public TaskId createTask(AntidoteClient client, ColumnId column_id, String title) {
		MapKey columnKey = Column.columnMap(column_id);
		TaskId task_id = TaskId.generateId();
		MapKey taskKey = taskMap(task_id);
		cbucket.update(client.noTransaction(), taskKey.update(titlefield.assign(title)));
		cbucket.update(client.noTransaction(), taskKey.update(columnidfield.assign(column_id)));
		cbucket.update(client.noTransaction(), columnKey.update(Column.taskidfield.add(task_id)));
		return task_id;
	}

	public void updateTitle(AntidoteClient client, TaskId task_id, String newTitle) {
		MapKey taskKey = taskMap(task_id);
		cbucket.update(client.noTransaction(), taskKey.update(titlefield.assign(newTitle)));
	}

	public void updateDueDate(AntidoteClient client, TaskId task_id, Date dueDate ) {
		MapKey taskKey = taskMap(task_id);
		DateFormat df = DateFormat.getDateInstance();
		String dueDateString = df.format(dueDate);
		cbucket.update(client.noTransaction(), taskKey.update(duedatefield.assign(dueDateString)));
	}

	public void deleteTask(AntidoteClient client, TaskId task_id) {
		MapKey taskKey = taskMap(task_id);
		MapReadResult taskmap = cbucket.read(client.noTransaction(), taskKey);
		ColumnId columnid = taskmap.get(columnidfield);
		MapKey columnKey = Column.columnMap(columnid);
		cbucket.update(client.noTransaction(), columnKey.update(Column.taskidfield.remove(task_id)));
	}
	
	public void moveTask(AntidoteClient client, TaskId task_id, ColumnId newcolumn_id) {
		MapKey taskKey = taskMap(task_id);

		InteractiveTransaction tx = client.startTransaction();
		ColumnId oldcolumn_id = cbucket.read(tx, columnidfield);
		MapKey oldcolumnKey = Column.columnMap(oldcolumn_id);
		cbucket.update(tx, oldcolumnKey.update(Column.taskidfield.remove(task_id)));
		MapKey newcolumnKey = Column.columnMap(newcolumn_id);
		cbucket.update(tx, newcolumnKey.update(Column.taskidfield.add(task_id)));
		cbucket.update(client.noTransaction(), taskKey.update(columnidfield.assign(newcolumn_id)));
		tx.commitTransaction();
		
	}

	public TaskMap getTask(AntidoteClient client, TaskId task_id) {
		MapKey taskKey = taskMap(task_id);
		MapReadResult taskmap = cbucket.read(client.noTransaction(), taskKey);
		String tasktitle = taskmap.get(titlefield);
		ColumnId columnid = taskmap.get(columnidfield);
		String duedate = taskmap.get(duedatefield);
		return new TaskMap(tasktitle, columnid, duedate);
	}
}
