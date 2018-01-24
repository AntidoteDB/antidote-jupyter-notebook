package eu.antidote.jupyter.antidote.crdt;

import eu.antidote.jupyter.antidote.AntidoteService;
import eu.antidotedb.client.Key;
import eu.antidotedb.client.RegisterKey;

public class RegisterService{

    AntidoteService antidoteService;

    public RegisterService(AntidoteService service){
        antidoteService = service;
    }

    public String updateRegister(String registerKeyId, String registerValue){
        RegisterKey<String> registerKey = Key.register(registerKeyId);
        antidoteService.getBucket().update(antidoteService.getAntidoteClient().noTransaction(), registerKey.assign(registerValue));
        return registerKey.toString();
    }

    public String readRegister(String registerKeyId){
        RegisterKey<String> registerKey = Key.register(registerKeyId);
        return antidoteService.getBucket().read(antidoteService.getAntidoteClient().noTransaction(), registerKey);
    }

    public void resetRegister(String registerKeyId){
        RegisterKey<String> registerKey = Key.register(registerKeyId);
        antidoteService.getBucket().update(antidoteService.getAntidoteClient().noTransaction(), registerKey.reset());
    }

}
