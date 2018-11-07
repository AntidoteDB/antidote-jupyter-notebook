package eu.antidote.jupyter.kanban.common;


import com.sun.corba.se.pept.protocol.ClientInvocationInfo;
import eu.antidotedb.client.*;
import eu.antidotedb.client.MapKey.MapReadResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static eu.antidotedb.client.Key.*;


public class Column_lww {

    private final RegisterKey<String> namefield;
    private final RegisterKey<BoardId> boardidfield;
    private  final RegisterKey<String> tasks;

    static Bucket cbucket = Bucket.bucket("bucket");

    public ColumnId column_id;

    public Column_lww(ColumnId column_id) {
        this.namefield = register("Name" + column_id);
        this.boardidfield = register("BoardId" + column_id, new BoardId.Coder());
        this.tasks = register("");
        this.column_id = column_id;
    }

    public static MapKey columnMap(ColumnId column_id) {
        return map_rr(column_id.getId());
    }

    private static Column_lww createColumn(AntidoteClient client, String name,BoardId board_id) {
        ColumnId cid = ColumnId.generateId();
        Column_lww c = new Column_lww(cid);
        MapKey columnKey = columnMap(cid);
        cbucket.update(client.noTransaction(), columnKey.update(c.namefield.assign(name)));
        cbucket.update(client.noTransaction(), columnKey.update(c.boardidfield.assign(board_id)));
        cbucket.update(client.noTransaction(), columnKey.update(c.tasks.assign("")));
      return c;
    }


    public static ColumnId addColumn(AntidoteClient client, BoardId board_id, String name) {
        MapKey boardKey = Board.boardMap(board_id);
        ColumnId column_id = ColumnId.generateId();
        Column_lww c = new Column_lww(column_id);//createColumn(client, name, board_id);
        MapKey columnKey = columnMap(column_id);
        cbucket.update(client.noTransaction(), columnKey.update(c.namefield.assign(name)));
        cbucket.update(client.noTransaction(), columnKey.update(c.boardidfield.assign(board_id)));
        cbucket.update(client.noTransaction(), columnKey.update(c.tasks.assign("")));

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

    public ColumnMap_lww getColumn(AntidoteClient client, ColumnId column_id) {
        MapKey columnKey = columnMap(column_id);
        MapReadResult columnmap = cbucket.read(client.noTransaction(), columnKey);

        String columnname = columnmap.get(namefield);
        BoardId boardid = columnmap.get(boardidfield);
        String str = columnmap.get(tasks);

        List<String> t = new ArrayList<String>();
        if (str != null && !str.equals("")) {
            String clean = str.substring(1, str.length() - 1);
            t = Arrays.asList(clean.split("\\s*,\\s*"));
        }
        return new ColumnMap_lww(columnname, boardid, t);
    }

    public void createTask(AntidoteClient client, ColumnId column_id, String title) {
        MapKey columnKey = Column.columnMap(column_id);
        MapReadResult columnmap = cbucket.read(client.noTransaction(), columnKey);

        String str = columnmap.get(tasks);
        List<String> t = new ArrayList<String>();
        if (str != null && !str.equals("")) {
            //System.out.println(str);
            String clean = str.substring(1, str.length()-1);
            //System.out.println(clean);
            t = new ArrayList<String>(Arrays.asList(clean.split("\\s*,\\s*")));
            //System.out.println(t);

        }

        t.add(title);
        //System.out.println(t.toString());

        cbucket.update(client.noTransaction(), columnKey.update(tasks.assign(t.toString()))); }
}