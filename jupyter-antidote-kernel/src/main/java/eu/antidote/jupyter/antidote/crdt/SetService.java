package eu.antidote.jupyter.antidote.crdt;

import eu.antidotedb.client.Key;
import eu.antidotedb.client.SetKey;
import eu.antidotedb.client.UpdateOp;
import eu.antidotedb.client.ValueCoder;


public class SetService<T> {

    public UpdateOp addToSet(SetKey<T> setKey, T... values) {
        UpdateOp updateOp;
        if(values.length>1) {
            updateOp = setKey.addAll(values);
        }else{
            updateOp = setKey.add(values[0]);
        }
        return updateOp;
    }

    public SetKey<T> getKey(String keyId, ValueCoder<T> format){
        return Key.set(keyId, format);
    }

    public SetKey<String> getKey(String keyId){
        return Key.set(keyId);
    }


    public UpdateOp removeFromSet(SetKey<T> setKey, T... values){
        UpdateOp setUpdate;
        if(values.length>1) {
            setUpdate = setKey.removeAll(values);
        }else{
            setUpdate = setKey.remove(values[0]);
        }
        return setUpdate;
    }

    public UpdateOp resetSet(SetKey<T> setKey){
        return setKey.reset();
    }

}
