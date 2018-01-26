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

    public long readInteger(String integerId){
        IntegerKey integerKey = Key.integer(integerId);
        return (Long) antidoteService.getBucket().read(antidoteService.getAntidoteClient().noTransaction(), integerKey);
    }
}
