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
            updateOp = setKey.addAll(values);
        }else{
            updateOp = setKey.add(values[0]);
        }
        return updateOp;
    }

    public List<String> readSet(String setKeyId) {
        SetKey<String> setKey = Key.set(setKeyId);
        return antidoteService.getBucket().read(antidoteService.getAntidoteClient().noTransaction(), setKey);
    }

    public UpdateOp removeFromSet(String setKeyId, String... values){
        SetKey<String> setKey = Key.set(setKeyId);
        UpdateOp setUpdate;
        if(values.length>1) {
            setUpdate = setKey.removeAll(values);
        }else{
            setUpdate = setKey.remove(values[0]);
        }
        return setUpdate;
    }

}
