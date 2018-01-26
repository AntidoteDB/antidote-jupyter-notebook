package eu.antidote.jupyter.antidote.crdt;

import eu.antidote.jupyter.antidote.AntidoteService;
import eu.antidotedb.client.Key;
import eu.antidotedb.client.RegisterKey;
import eu.antidotedb.client.SetKey;
import eu.antidotedb.client.UpdateOp;

import java.util.List;

public class SetService {

    AntidoteService antidoteService;

    public SetService(AntidoteService service) {
        antidoteService = service;
    }

    public UpdateOp addToSet(String setKeyId, String... values) {
        SetKey<String> setKey = Key.set(setKeyId);
        UpdateOp updateOp;
        if(values.length>1) {
           // antidoteService.getBucket().update(antidoteService.getAntidoteClient().noTransaction(), setKey.addAll(values));
            updateOp = setKey.addAll(values);
        }else{
            //antidoteService.getBucket().update(antidoteService.getAntidoteClient().noTransaction(), setKey.add(values[0]));
            updateOp = setKey.add(values[0]);
        }
        return updateOp;
    }

    public List<String> readSet(String setKeyId) {
        SetKey<String> setKey = Key.set(setKeyId);
        return antidoteService.getBucket().read(antidoteService.getAntidoteClient().noTransaction(), setKey);
    }

    public void removeFromSet(String setKeyId, String... values){
        SetKey<String> setKey = Key.set(setKeyId);
        if(values.length>1) {
            antidoteService.getBucket().update(antidoteService.getAntidoteClient().noTransaction(), setKey.removeAll(values));
        }else{
            antidoteService.getBucket().update(antidoteService.getAntidoteClient().noTransaction(), setKey.remove(values[0]));
        }
    }

}
