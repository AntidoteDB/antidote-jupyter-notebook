package eu.antidote.jupyter.antidote.crdt;

import eu.antidote.jupyter.antidote.AntidoteService;
import eu.antidotedb.client.AntidoteClient;
import eu.antidotedb.client.Bucket;
import eu.antidotedb.client.Key;
import eu.antidotedb.client.RegisterKey;

public class RegisterService{

    AntidoteService antidoteService;

    public RegisterService(AntidoteService service){
        antidoteService = service;
    }

    public String updateRegister(String registerId, String registerValue){
        RegisterKey<String> registerKey = Key.register(registerId);
        antidoteService.getBucket().update(antidoteService.getAntidoteClient().noTransaction(), registerKey.assign(registerValue));
        return registerKey.toString();
    }

}
