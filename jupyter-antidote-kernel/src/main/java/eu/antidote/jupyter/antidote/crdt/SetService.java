package eu.antidote.jupyter.antidote.crdt;

import eu.antidotedb.client.Key;
import eu.antidotedb.client.SetKey;
import eu.antidotedb.client.UpdateOp;


public class SetService {

    public UpdateOp addToSet(SetKey<String> setKey, String... values) {
        UpdateOp updateOp;
        if(values.length>1) {
            updateOp = setKey.addAll(values);
        }else{
            updateOp = setKey.add(values[0]);
        }
        return updateOp;
    }

    public SetKey<String> getKey(String keyId){
        return Key.set(keyId);
    }

    public UpdateOp removeFromSet(SetKey<String> setKey, String... values){
        UpdateOp setUpdate;
        if(values.length>1) {
            setUpdate = setKey.removeAll(values);
        }else{
            setUpdate = setKey.remove(values[0]);
        }
        return setUpdate;
    }

    public UpdateOp resetSet(SetKey<String> setKey){
        return setKey.reset();
    }

}
