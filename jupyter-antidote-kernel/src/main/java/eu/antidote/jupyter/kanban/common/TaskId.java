package eu.antidote.jupyter.kanban.common;


import java.util.UUID;

import com.google.protobuf.ByteString;
import eu.antidotedb.client.ValueCoder;

public class TaskId {
	static int i;
	private String id;
	
	public TaskId(String uniqueID) {
		id = uniqueID;
	}
	
	public static TaskId generateId() {
		String uniqueID = "TASK_" + i++;//UUID.randomUUID().toString();
		return new TaskId(uniqueID);
	}
	
	public String getId() {
		return id;
	}
	
	public String toString() {
		return id;
	}

	@Override
	public boolean equals(Object o) {

		if (o instanceof TaskId) {
			TaskId c = (TaskId) o;
			if (this.id.equals(c.id)) return true;
		}
		return false;
	}


	static class Coder implements ValueCoder<TaskId> {


		public TaskId decode(ByteString b) {
			return new TaskId(b.toStringUtf8());
		}


		public ByteString encode(TaskId f) {
			return ByteString.copyFromUtf8(f.id);
		}		
	}
}
