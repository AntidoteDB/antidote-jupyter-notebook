package eu.antidote.jupyter.antidote.crdt;

import eu.antidote.jupyter.antidote.AntidoteService;
import eu.antidotedb.client.CounterKey;
import eu.antidotedb.client.Key;
import eu.antidotedb.client.UpdateOp;

public class FatCounterService {

    AntidoteService antidoteService;

    public FatCounterService(AntidoteService service){
        antidoteService = service;
    }

    public UpdateOp incrementFatCounter(String fatCounterId, Integer incrementValue) {
        CounterKey fatCounterKey = Key.fatCounter(fatCounterId);
        return fatCounterKey.increment(incrementValue);
    }

    public UpdateOp resetFatCounter(String fatCounterId){
        CounterKey fatCounterKey = Key.fatCounter(fatCounterId);
        return fatCounterKey.reset();
    }

    public int readFatCounter(String fatCounterId){
        CounterKey fatCounterKey = Key.fatCounter(fatCounterId);
        return (Integer) antidoteService.getBucket().read(antidoteService.getAntidoteClient().noTransaction(), fatCounterKey);
    }
}
