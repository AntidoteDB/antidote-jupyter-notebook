package eu.antidote.jupyter.antidote.crdt;

import eu.antidote.jupyter.antidote.AntidoteService;
import eu.antidotedb.client.AntidoteClient;
import eu.antidotedb.client.Bucket;
import eu.antidotedb.client.IntegerKey;
import eu.antidotedb.client.Key;

public class IntegerService {

    AntidoteService antidoteService;

    public IntegerService(AntidoteService service){
        antidoteService = service;
    }

    public String newInteger(String integerId, long integerValue){
        IntegerKey integerKey = Key.integer(integerId);
        antidoteService.getBucket().update(antidoteService.getAntidoteClient().noTransaction(), integerKey.assign(integerValue));
        return integerKey.toString();
    }

    public String incrementInteger(String integerId, long incrementValue) {
        IntegerKey integerKey = Key.integer(integerId);
        antidoteService.getBucket().update(antidoteService.getAntidoteClient().noTransaction(), integerKey.increment(incrementValue));
        return integerKey.toString();
    }

}
