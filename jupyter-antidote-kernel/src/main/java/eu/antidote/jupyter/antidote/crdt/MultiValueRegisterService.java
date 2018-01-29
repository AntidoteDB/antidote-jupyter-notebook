package eu.antidote.jupyter.antidote.crdt;

import eu.antidote.jupyter.antidote.AntidoteService;
import eu.antidotedb.client.Key;
import eu.antidotedb.client.MVRegisterKey;
import eu.antidotedb.client.RegisterKey;
import eu.antidotedb.client.UpdateOp;

import java.util.List;

public class MultiValueRegisterService {

    public UpdateOp AssignRegister(String registerKeyId, String registerValue){
        MVRegisterKey<String> registerKey = Key.multiValueRegister(registerKeyId);
        return registerKey.assign(registerValue);
    }

    public Key getKey(String keyId){
        return Key.multiValueRegister(keyId);
    }
}
