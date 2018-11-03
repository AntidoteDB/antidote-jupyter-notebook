package eu.antidote.jupyter.kanban.common;


import java.util.UUID;
import com.google.protobuf.ByteString;
import eu.antidotedb.client.ValueCoder;

public class ColumnId {
	
	private String id;
		
	public ColumnId(String uniqueID) {
		id = uniqueID;
	}

	public static ColumnId generateId() {
		String uniqueID = UUID.randomUUID().toString();
		return new ColumnId(uniqueID);
	}
	
	public String getId() {
		return id;
	}

	public String toString() {
		return id;
	}

	static class Coder implements ValueCoder<ColumnId> {

		public ColumnId decode(ByteString b) {
			return new ColumnId(b.toStringUtf8());
		}


		public ByteString encode(ColumnId f) {
			return ByteString.copyFromUtf8(f.id);
		}
	}
}
