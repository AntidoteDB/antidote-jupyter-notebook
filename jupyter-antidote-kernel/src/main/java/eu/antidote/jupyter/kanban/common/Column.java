package eu.antidote.jupyter.kanban.common;


import static eu.antidotedb.client.Key.map_rr;
import static eu.antidotedb.client.Key.register;
import static eu.antidotedb.client.Key.set;

import java.util.ArrayList;
import java.util.List;
import eu.antidotedb.client.AntidoteClient;
import eu.antidotedb.client.Bucket;
import eu.antidotedb.client.MapKey;
import eu.antidotedb.client.RegisterKey;
import eu.antidotedb.client.SetKey;
import eu.antidotedb.client.MapKey.MapReadResult;


public class Column {

		private static final RegisterKey<String> namefield = register("Name");
		private static final RegisterKey<BoardId> boardidfield = register("BoardId", new BoardId.Coder());
		public static final SetKey<TaskId> taskidfield = set("TaskId", new TaskId.Coder());

		Bucket cbucket = Bucket.bucket("bucket");

		public ColumnId column_id = null;
		public Column(ColumnId column_id) {
			this.column_id = column_id;
		}

		public Column() {
		}

		public static MapKey columnMap(ColumnId column_id) {
			return map_rr(column_id.getId());
		}

		public ColumnId addColumn(AntidoteClient client, BoardId board_id, String name) {
			MapKey boardKey = Board.boardMap(board_id);
			ColumnId column_id = ColumnId.generateId();
			MapKey columnKey = columnMap(column_id);
			cbucket.update(client.noTransaction(), columnKey.update(namefield.assign(name)));
			cbucket.update(client.noTransaction(), columnKey.update(boardidfield.assign(board_id)));
			cbucket.update(client.noTransaction(), boardKey.update(Board.columnidfield.add(column_id)));
			return column_id;
		}

		public void renameColumn(AntidoteClient client, ColumnId column_id, String newName) {
			MapKey columnKey = columnMap(column_id);
			cbucket.update(client.noTransaction(), columnKey.update(namefield.assign(newName)));
		}

		public void deleteColumn(AntidoteClient client, ColumnId column_id) {
			MapKey columnKey = columnMap(column_id);
			MapReadResult columnmap = cbucket.read(client.noTransaction(), columnKey);
			BoardId boardid = columnmap.get(boardidfield);
			MapKey boardKey = Board.boardMap(boardid);
			cbucket.update(client.noTransaction(), boardKey.update(Board.columnidfield.remove(column_id)));
		}

		public ColumnMap getColumn(AntidoteClient client, ColumnId column_id) {
			List<TaskMap> task_list = new ArrayList<TaskMap>();
			MapKey columnKey = columnMap(column_id);
			MapReadResult columnmap = cbucket.read(client.noTransaction(), columnKey);
			String columnname = columnmap.get(namefield);
			BoardId boardid = columnmap.get(boardidfield);
			List<TaskId> taskid_list = columnmap.get(taskidfield);
			for(int i = 0; i < taskid_list.size(); i++) {
				TaskMap task = new Task().getTask(client, taskid_list.get(i));
				task_list.add(task);
			}
			return new ColumnMap(columnname, boardid, taskid_list, task_list);
		}
	}
