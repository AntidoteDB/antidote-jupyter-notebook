package eu.antidote.jupyter.kanban.common;

import static eu.antidotedb.client.Key.map_rr;
import static eu.antidotedb.client.Key.register;
import static eu.antidotedb.client.Key.set;

import java.util.ArrayList;
import java.util.List;
import eu.antidotedb.client.AntidoteClient;
import eu.antidotedb.client.Bucket;
import eu.antidotedb.client.MapKey;
import eu.antidotedb.client.MapKey.MapReadResult;
import eu.antidotedb.client.RegisterKey;
import eu.antidotedb.client.SetKey;

public class Board {

	private static final RegisterKey<String> namefield = register("Name");
	public static final SetKey<ColumnId> columnidfield = set("ColumnId", new ColumnId.Coder());

	static Bucket cbucket = Bucket.bucket("bucket");
	public static List<BoardId> list_boards = new ArrayList<BoardId>();

	public static MapKey boardMap(BoardId board_id) {
		return map_rr(board_id.getId());
	}

	public BoardId createBoard(AntidoteClient client, String name) {
		BoardId board_id = BoardId.generateId();
		MapKey boardKey = boardMap(board_id);
		cbucket.update(client.noTransaction(), boardKey.update(namefield.assign(name)));
        cbucket.update(client.noTransaction(), boardKey.update(columnidfield.reset()));
        list_boards.add(board_id);
		return board_id;
	}

	public void renameBoard(AntidoteClient client, BoardId board_id , String newName) {
		MapKey boardKey = boardMap(board_id);
		cbucket.update(client.noTransaction(), boardKey.update(namefield.assign(newName)));
	}

	public List<BoardId> listBoards(){
		return list_boards;
	}

	public BoardMap getBoard(AntidoteClient client, BoardId board_id) {
        MapKey boardKey = boardMap(board_id);
        MapReadResult boardmap = cbucket.read(client.noTransaction(), boardKey);

        String boardname = boardmap.get(namefield);

        List<ColumnMap> column_list = new ArrayList<ColumnMap>();
		List<ColumnId> columnid_list = boardmap.get(columnidfield);

		for(ColumnId cid : columnid_list) {
			ColumnMap column = new Column().getColumn(client, cid);
			column_list.add(column);
		}
		return new BoardMap(boardname, columnid_list, column_list);
	}


    public BoardMap getBoard_x(AntidoteClient client, BoardId board_id) {
        MapKey boardKey = boardMap(board_id);
        MapReadResult boardmap = cbucket.read(client.noTransaction(), boardKey);

        String boardname = boardmap.get(namefield);

        List<ColumnMap_lww> column_list = new ArrayList<ColumnMap_lww>();
        List<ColumnId> columnid_list = boardmap.get(columnidfield);
        for(ColumnId cid : columnid_list) {
            ColumnMap_lww column = new Column_lww(cid).getColumn(client, cid);
            column_list.add(column);
        }
        return new BoardMap(boardname, columnid_list, column_list, true);
    }
}
