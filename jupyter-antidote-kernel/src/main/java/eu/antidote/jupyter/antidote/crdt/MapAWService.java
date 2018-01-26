package eu.antidote.jupyter.antidote.crdt;

import eu.antidote.jupyter.antidote.AntidoteService;
import eu.antidotedb.client.CounterKey;
import eu.antidotedb.client.Key;
import eu.antidotedb.client.MapKey;
import eu.antidotedb.client.SetKey;

import java.util.List;

public class MapAWService {

    AntidoteService antidoteService;

    public MapAWService(AntidoteService service){
        antidoteService = service;
    }

    public MapKey.MapReadResult readMapAW(String mapId) {
        MapKey mapKey = Key.map_aw(mapId);
        return antidoteService.getBucket().read(antidoteService.getAntidoteClient().noTransaction(), mapKey);
    }

}
