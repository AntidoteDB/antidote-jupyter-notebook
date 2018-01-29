package eu.antidote.jupyter.antidote.crdt;

import eu.antidotedb.client.Key;
import eu.antidotedb.client.RegisterKey;
import eu.antidotedb.client.UpdateOp;

public class RegisterService{

    public UpdateOp assignRegister(String registerKeyId, String registerValue){
        RegisterKey<String> registerKey = Key.register(registerKeyId);
        return registerKey.assign(registerValue);
    }

    public Key getKey(String keyId){
        return Key.register(keyId);
    }

}
