package eu.antidote.jupyter.antidote.crdt;

import eu.antidotedb.client.Key;
import eu.antidotedb.client.SetKey;
import eu.antidotedb.client.UpdateOp;

public class RWSetService {

    public UpdateOp addToRWSet(SetKey<String> setKey, String... values) {
        UpdateOp op;
        if(values.length>1) {
            op = setKey.addAll(values);
        }else{
            op = setKey.add(values[0]);
        }
        return op;
    }

    public SetKey<String> getKey(String keyId){
         return Key.set_removeWins(keyId);
    }

    public UpdateOp removeFromRWSet(SetKey<String> setKey, String... values){
        UpdateOp op;
        if(values.length>1) {
            op = setKey.removeAll(values);
        }else{
            op = setKey.remove(values[0]);
        }
        return op;
    }
}
