package eu.antidote.jupyter.kanban.common;

import java.util.UUID;
import com.google.protobuf.ByteString;
import eu.antidotedb.client.ValueCoder;

public class BoardId {
	static int i;
	private String id;
	
	public BoardId(String uniqueID) {
		id = uniqueID;
	}

	public static BoardId generateId() {
		String uniqueID = "BOARD_" + i++;//UUID.randomUUID().toString();
		return new BoardId(uniqueID);
	}
	
	public String getId() {
		return id;
	}
	public String toString() {
		return id;
	}

	@Override
	public boolean equals(Object o) {

		if (o instanceof BoardId) {
			BoardId c = (BoardId) o;
			if (this.id.equals(c.id)) return true;
		}
		return false;
	}

	static class Coder implements ValueCoder<BoardId> {

		public BoardId decode(ByteString b) {
			return new BoardId(b.toStringUtf8());
		}

		public ByteString encode(BoardId f) {
			return ByteString.copyFromUtf8(f.id);
		}	
	}
}

