package eu.antidote.jupyter.kanban.common;


import eu.antidotedb.client.AntidoteClient;
import eu.antidotedb.client.Bucket;
import static eu.antidotedb.client.Key.*;
import eu.antidotedb.client.MapKey;
import eu.antidotedb.client.RegisterKey;

public class User {
	private static final RegisterKey<String> emailfield = register("Email");

	public UserId user_id = null;
	
	Bucket cbucket = Bucket.bucket("userbucket");

	public User(UserId user_id) {
		this.user_id = user_id;
	}
	
	public User() {
	}

	public MapKey userMap(UserId user_id) {
		return map_aw(user_id.getId());
	}

	public UserId createUser(AntidoteClient client, String email) {
		UserId user_id = UserId.generateId();
		MapKey user = userMap(user_id);
		cbucket.update(client.noTransaction(), user.update(emailfield.assign(email)));
		return user_id;
	}
}
