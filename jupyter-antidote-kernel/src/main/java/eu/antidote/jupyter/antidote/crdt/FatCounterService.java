package eu.antidote.jupyter.antidote.crdt;

import eu.antidote.jupyter.antidote.AntidoteService;
import eu.antidotedb.client.CounterKey;
import eu.antidotedb.client.Key;

public class FatCounterService {

    AntidoteService antidoteService;

    public FatCounterService(AntidoteService service){
        antidoteService = service;
    }

    public String incrementFatCounter(String fatCounterId, Integer incrementValue) {
        CounterKey fatCounterKey = Key.fatCounter(fatCounterId);
        antidoteService.getBucket().update(antidoteService.getAntidoteClient().noTransaction(), fatCounterKey.increment(incrementValue));
        return fatCounterKey.toString();
    }

    public int readFatCounter(String fatCounterId){
        CounterKey fatCounterKey = Key.fatCounter(fatCounterId);
        return (Integer) antidoteService.getBucket().read(antidoteService.getAntidoteClient().noTransaction(), fatCounterKey);
    }

    public void resetFatCounter(String fatCounterId){
        CounterKey fatCounterKey = Key.fatCounter(fatCounterId);
        antidoteService.getBucket().update(antidoteService.getAntidoteClient().noTransaction(), fatCounterKey.reset());
    }
}
