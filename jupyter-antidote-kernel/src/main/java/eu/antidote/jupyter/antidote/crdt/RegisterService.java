package eu.antidote.jupyter.antidote.crdt;

import eu.antidotedb.client.AntidoteClient;
import eu.antidotedb.client.Bucket;
import eu.antidotedb.client.Key;
import eu.antidotedb.client.RegisterKey;

public class RegisterService {

    Bucket bucket;
    AntidoteClient client;

    public RegisterService(Bucket bucket, AntidoteClient client){
        this.bucket = bucket;
        this.client = client;
    }

    public String updateRegister(String registerId, String registerValue){
        RegisterKey<String> registerKey = Key.register(registerId);
        bucket.update(client.noTransaction(), registerKey.assign(registerValue));
        return registerKey.toString();
    }

}
