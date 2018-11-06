package eu.antidote.jupyter.antidote.crdt;

import eu.antidotedb.client.Key;
import eu.antidotedb.client.SetKey;
import eu.antidotedb.client.UpdateOp;
import eu.antidotedb.client.ValueCoder;

public class RWSetService<T> {

    public UpdateOp addToRWSet(SetKey<T> setKey, T... values) {
        UpdateOp op;
        if(values.length>1) {
            op = setKey.addAll(values);
        }else{
            op = setKey.add(values[0]);
        }
        return op;
    }

    public SetKey<T> getKey(String keyId, ValueCoder<T> format){
         return Key.set_removeWins(keyId, format);
    }


    public SetKey<String> getKey(String keyId){
        return Key.set_removeWins(keyId);
    }

    public UpdateOp removeFromRWSet(SetKey<T> setKey, T... values){
        UpdateOp op;
        if(values.length>1) {
            op = setKey.removeAll(values);
        }else{
            op = setKey.remove(values[0]);
        }
        return op;
    }

    public UpdateOp resetRWSet(SetKey<T> setKey){
        return setKey.reset();
    }
}
