package eu.antidote.jupyter.antidote.crdt;

import eu.antidote.jupyter.antidote.AntidoteService;
import eu.antidotedb.client.Key;
import eu.antidotedb.client.SetKey;

import java.util.List;

public class SetRWService {

    AntidoteService antidoteService;

    public SetRWService(AntidoteService service) {
        antidoteService = service;
    }

    public String addToRWSet(String setKeyId, String... values) {
        SetKey<String> setKey = Key.set_removeWins(setKeyId);
        if(values.length>1) {
            antidoteService.getBucket().update(antidoteService.getAntidoteClient().noTransaction(), setKey.addAll(values));
        }else{
            antidoteService.getBucket().update(antidoteService.getAntidoteClient().noTransaction(), setKey.add(values[0]));
        }
        return setKey.toString();
    }

    public List<String> readRWSet(String setKeyId) {
        SetKey<String> setKey = Key.set_removeWins(setKeyId);
        return antidoteService.getBucket().read(antidoteService.getAntidoteClient().noTransaction(), setKey);
    }

    public void removeFromRWSet(String setKeyId, String... values){
        SetKey<String> setKey = Key.set_removeWins(setKeyId);
        if(values.length>1) {
            antidoteService.getBucket().update(antidoteService.getAntidoteClient().noTransaction(), setKey.removeAll(values));
        }else{
            antidoteService.getBucket().update(antidoteService.getAntidoteClient().noTransaction(), setKey.remove(values[0]));
        }
    }
}
