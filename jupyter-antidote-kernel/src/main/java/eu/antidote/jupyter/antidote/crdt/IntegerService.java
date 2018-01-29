package eu.antidote.jupyter.antidote.crdt;

import eu.antidote.jupyter.antidote.AntidoteService;
import eu.antidotedb.client.*;

public class IntegerService {

    private AntidoteService antidoteService;

    public IntegerService(AntidoteService service){
        antidoteService = service;
    }

    public UpdateOp assignInteger(String integerId, Integer integerValue){
        IntegerKey integerKey = Key.integer(integerId);
        return integerKey.assign(integerValue);
    }

    public UpdateOp incrementInteger(String integerId, Integer incrementValue) {
        IntegerKey integerKey = Key.integer(integerId);
        return integerKey.increment(incrementValue);
    }

    public Key getKey(String integerId){
        return Key.integer(integerId);
    }
}
