package eu.antidote.jupyter.antidote.crdt;

import eu.antidote.jupyter.antidote.AntidoteService;
import eu.antidotedb.client.Key;
import eu.antidotedb.client.SetKey;
import eu.antidotedb.client.UpdateOp;

import java.util.List;

public class SetRWService {

    public UpdateOp addToRWSet(String setKeyId, String... values) {
        SetKey<String> setKey = Key.set_removeWins(setKeyId);
        UpdateOp op;
        if(values.length>1) {
            op = setKey.addAll(values);
        }else{
            op = setKey.add(values[0]);
        }
        return op;
    }

    public Key getKey(String keyId){
         return Key.set_removeWins(keyId);
    }

    public UpdateOp removeFromRWSet(String setKeyId, String... values){
        SetKey<String> setKey = Key.set_removeWins(setKeyId);
        UpdateOp op;
        if(values.length>1) {
            op = setKey.removeAll(values);
        }else{
            op = setKey.remove(values[0]);
        }
        return op;
    }
}
