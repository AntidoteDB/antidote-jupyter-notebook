package eu.antidote.jupyter.kanban.common;

import java.util.UUID;
import com.google.protobuf.ByteString;
import eu.antidotedb.client.ValueCoder;

public class BoardId {
	
	private String id;
	
	public BoardId(String uniqueID) {
		id = uniqueID;
	}

	public static BoardId generateId() {
		String uniqueID = UUID.randomUUID().toString();
		return new BoardId(uniqueID);
	}
	
	public String getId() {
		return id;
	}
	public String toString() {
		return id;
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

