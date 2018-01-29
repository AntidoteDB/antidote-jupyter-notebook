package eu.antidote.jupyter.antidote.crdt;

import eu.antidote.jupyter.antidote.AntidoteService;
import eu.antidotedb.client.CounterKey;
import eu.antidotedb.client.Key;
import eu.antidotedb.client.UpdateOp;

public class CounterService {

    private AntidoteService antidoteService;

    public CounterService(AntidoteService service){
        antidoteService = service;
    }

    public UpdateOp incrementCounter(String counterId, Integer incrementValue) {
        CounterKey counterKey = Key.counter(counterId);
        return counterKey.increment(incrementValue);
    }

    public Key getKey(String counterId){
        return Key.counter(counterId);
    }

}
