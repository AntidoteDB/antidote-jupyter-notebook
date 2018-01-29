package eu.antidote.jupyter.antidote.crdt;

import eu.antidotedb.client.*;

public class IntegerService {

    public UpdateOp assignInteger(IntegerKey integerKey, Integer integerValue){
        return integerKey.assign(integerValue);
    }

    public UpdateOp incrementInteger(IntegerKey integerKey, Integer incrementValue) {
        return integerKey.increment(incrementValue);
    }

    public IntegerKey getKey(String integerId){
        return Key.integer(integerId);
    }
}
