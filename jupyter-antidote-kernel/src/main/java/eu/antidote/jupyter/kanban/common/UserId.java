package eu.antidote.jupyter.kanban.common;


import java.util.UUID;

import com.google.protobuf.ByteString;
import eu.antidotedb.client.ValueCoder;

public class UserId {
	
	private String id;
	
	public UserId(String uniqueID) {
		id = uniqueID;
	}
	
	public String getId() {
		return id;
	}

	public static UserId generateId() {
		String uniqueID = UUID.randomUUID().toString();
		return new UserId(uniqueID);
	}
	
	static class Coder implements ValueCoder<UserId> {


		public UserId decode(ByteString b) {
			return new UserId(b.toStringUtf8());
		}

		public ByteString encode(UserId f) {
			return ByteString.copyFromUtf8(f.id);
		}
		
	}

}
