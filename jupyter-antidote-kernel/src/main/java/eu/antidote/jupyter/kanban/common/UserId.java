package eu.antidote.jupyter.kanban.common;


import java.util.UUID;

import com.google.protobuf.ByteString;
import eu.antidotedb.client.ValueCoder;

public class UserId {
	static int i;
	private String id;
	
	public UserId(String uniqueID) {
		id = uniqueID;
	}
	
	public String getId() {
		return id;
	}

	public static UserId generateId() {
		String uniqueID = "USER_" + i++; //UUID.randomUUID().toString();
		return new UserId(uniqueID);
	}

	@Override
	public boolean equals(Object o) {

		if (o instanceof UserId) {
			UserId c = (UserId) o;
			if (this.id.equals(c.id)) return true;
		}
		return false;
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
