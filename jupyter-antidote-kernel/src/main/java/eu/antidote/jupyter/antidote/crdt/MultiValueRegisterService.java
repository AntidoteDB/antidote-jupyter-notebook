package eu.antidote.jupyter.antidote.crdt;

import eu.antidotedb.client.Key;
import eu.antidotedb.client.MVRegisterKey;
import eu.antidotedb.client.UpdateOp;
import eu.antidotedb.client.ValueCoder;

public class MultiValueRegisterService<T> {

    public UpdateOp assignRegister(MVRegisterKey<T> registerKey, T registerValue){
        return registerKey.assign(registerValue);
    }

    public MVRegisterKey<T> getKey(String keyId, ValueCoder<T> format){
        return Key.multiValueRegister(keyId, format);
    }

    public MVRegisterKey<String> getKey(String keyId){
        return Key.multiValueRegister(keyId);
    }


    public UpdateOp resetMVRegister(MVRegisterKey<T> registerKey){
        return registerKey.reset();
    }
}
