package eu.antidote.jupyter.antidote.crdt;

import eu.antidotedb.client.Key;
import eu.antidotedb.client.RegisterKey;
import eu.antidotedb.client.UpdateOp;

public class RegisterService{

    public UpdateOp assignRegister(RegisterKey<String> registerKey, String registerValue){
        return registerKey.assign(registerValue);
    }

    public RegisterKey<String> getKey(String keyId){
        return Key.register(keyId);
    }

}
