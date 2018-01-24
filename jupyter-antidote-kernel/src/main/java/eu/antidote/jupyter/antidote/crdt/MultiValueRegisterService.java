package eu.antidote.jupyter.antidote.crdt;

import eu.antidote.jupyter.antidote.AntidoteService;
import eu.antidotedb.client.Key;
import eu.antidotedb.client.MVRegisterKey;
import eu.antidotedb.client.RegisterKey;

import java.util.List;

public class MultiValueRegisterService {

    AntidoteService antidoteService;

    public MultiValueRegisterService(AntidoteService service){
        antidoteService = service;
    }

    public String updateRegister(String registerKeyId, String registerValue){
        MVRegisterKey<String> registerKey = Key.multiValueRegister(registerKeyId);
        antidoteService.getBucket().update(antidoteService.getAntidoteClient().noTransaction(), registerKey.assign(registerValue));
        return registerKey.toString();
    }

    public List<String> readRegister(String registerKeyId){
        MVRegisterKey<String> registerKey = Key.multiValueRegister(registerKeyId);
        return antidoteService.getBucket().read(antidoteService.getAntidoteClient().noTransaction(), registerKey);
    }

    public void resetRegister(String registerKeyId){
        RegisterKey<String> registerKey = Key.register(registerKeyId);
        antidoteService.getBucket().update(antidoteService.getAntidoteClient().noTransaction(), registerKey.reset());
    }
}
