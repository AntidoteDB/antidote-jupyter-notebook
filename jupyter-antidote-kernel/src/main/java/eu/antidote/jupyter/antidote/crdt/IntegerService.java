package eu.antidote.jupyter.antidote.crdt;

import eu.antidote.jupyter.antidote.AntidoteService;
import eu.antidotedb.client.*;

public class IntegerService {

    AntidoteService antidoteService;

    public IntegerService(AntidoteService service){
        antidoteService = service;
    }

    public String assignInteger(String integerId, Integer integerValue){
        IntegerKey integerKey = Key.integer(integerId);
        antidoteService.getBucket().update(antidoteService.getAntidoteClient().noTransaction(), integerKey.assign(integerValue));
        return integerKey.toString();
    }

    public String incrementInteger(String integerId, Integer incrementValue) {
        IntegerKey integerKey = Key.integer(integerId);
        antidoteService.getBucket().update(antidoteService.getAntidoteClient().noTransaction(), integerKey.increment(incrementValue));
        return integerKey.toString();
    }

    public long readInteger(String integerId){
        IntegerKey integterKey = Key.integer(integerId);
        return (Long) antidoteService.getBucket().read(antidoteService.getAntidoteClient().noTransaction(), integterKey);
    }
}
