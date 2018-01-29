package eu.antidote.jupyter.antidote.crdt;

import eu.antidotedb.client.Key;
import eu.antidotedb.client.MVRegisterKey;
import eu.antidotedb.client.UpdateOp;

public class MultiValueRegisterService {

    public UpdateOp assignRegister(MVRegisterKey<String> registerKey, String registerValue){
        return registerKey.assign(registerValue);
    }

    public MVRegisterKey<String> getKey(String keyId){
        return Key.multiValueRegister(keyId);
    }
}
