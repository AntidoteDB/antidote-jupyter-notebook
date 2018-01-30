package eu.antidote.jupyter.antidote.crdt;

import eu.antidotedb.client.CounterKey;
import eu.antidotedb.client.Key;
import eu.antidotedb.client.UpdateOp;

public class CounterService {

    public UpdateOp incrementCounter(CounterKey counterKey, Integer incrementValue) {
        return counterKey.increment(incrementValue);
    }

    public CounterKey getKey(String counterId){
        return Key.counter(counterId);
    }

}
