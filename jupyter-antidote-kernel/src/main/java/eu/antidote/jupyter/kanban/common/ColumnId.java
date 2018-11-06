package eu.antidote.jupyter.kanban.common;


import java.util.UUID;
import com.google.protobuf.ByteString;
import eu.antidotedb.client.ValueCoder;

public class ColumnId {
	static int i;
	private String id;
		
	public ColumnId(String uniqueID) {
		id = uniqueID;
	}

	public static ColumnId generateId() {
		String uniqueID = "COLUMN_" + i++;//UUID.randomUUID().toString();
		return new ColumnId(uniqueID);
	}
	
	public String getId() {
		return id;
	}

	public String toString() {
		return id;
	}

    @Override
    public boolean equals(Object o) {

        if (o instanceof ColumnId) {
            ColumnId c = (ColumnId) o;
            if (this.id.equals(c.id)) return true;
        }
        return false;
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
