package eu.antidote.jupyter.antidote.crdt;

import eu.antidote.jupyter.antidote.AntidoteService;
import eu.antidotedb.client.CounterKey;
import eu.antidotedb.client.Key;

public class CounterService {

    AntidoteService antidoteService;

    public CounterService(AntidoteService service){
        antidoteService = service;
    }

    public String incrementCounter(String counterId, Integer incrementValue) {
        CounterKey counterKey = Key.counter(counterId);
        antidoteService.getBucket().update(antidoteService.getAntidoteClient().noTransaction(), counterKey.increment(incrementValue));
        return counterKey.toString();
    }

    public int readCounter(String counterId){
        CounterKey counterKey = Key.counter(counterId);
        return (Integer) antidoteService.getBucket().read(antidoteService.getAntidoteClient().noTransaction(), counterKey);
    }

}
