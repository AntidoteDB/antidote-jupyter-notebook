package eu.antidote.jupyter.antidote.crdt;

import eu.antidotedb.client.Key;
import eu.antidotedb.client.RegisterKey;
import eu.antidotedb.client.UpdateOp;
import eu.antidotedb.client.ValueCoder;

public class RegisterService<T>{

    public UpdateOp assignRegister(RegisterKey<T> registerKey, T registerValue){
        return registerKey.assign(registerValue);
    }

    public RegisterKey<T> getKey(String keyId, ValueCoder<T> format){
        return Key.register(keyId, format);
    }
    public RegisterKey<String> getKey(String keyId){
        return Key.register(keyId);
    }

}
