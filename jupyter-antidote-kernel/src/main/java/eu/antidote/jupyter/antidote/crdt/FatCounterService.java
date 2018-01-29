package eu.antidote.jupyter.antidote.crdt;

import eu.antidotedb.client.CounterKey;
import eu.antidotedb.client.Key;
import eu.antidotedb.client.UpdateOp;

public class FatCounterService {

    public UpdateOp incrementFatCounter(CounterKey fatCounterKey, Integer incrementValue) {
        return fatCounterKey.increment(incrementValue);
    }

    public UpdateOp resetFatCounter(CounterKey fatCounterKey){
        return fatCounterKey.reset();
    }

    public CounterKey getKey(String fatCounterId){
        return Key.fatCounter(fatCounterId);
    }
}
