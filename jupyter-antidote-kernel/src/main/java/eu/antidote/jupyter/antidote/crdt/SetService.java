package eu.antidote.jupyter.antidote.crdt;

import eu.antidotedb.client.Key;
import eu.antidotedb.client.SetKey;
import eu.antidotedb.client.UpdateOp;


public class SetService {

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

    public Key getKey(String keyId){
        return Key.set(keyId);
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
