package eu.antidote.jupyter.antidote.crdt;

import eu.antidote.jupyter.antidote.AntidoteService;
import eu.antidotedb.client.Key;
import eu.antidotedb.client.RegisterKey;
import eu.antidotedb.client.UpdateOp;

public class RegisterService{

    AntidoteService antidoteService;

    public RegisterService(AntidoteService service){
        antidoteService = service;
    }

    public UpdateOp assignRegister(String registerKeyId, String registerValue){
        RegisterKey<String> registerKey = Key.register(registerKeyId);
        return registerKey.assign(registerValue);
    }

    public String readRegister(String registerKeyId){
        RegisterKey<String> registerKey = Key.register(registerKeyId);
        return antidoteService.getBucket().read(antidoteService.getAntidoteClient().noTransaction(), registerKey);
    }

}
