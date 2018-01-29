package eu.antidote.jupyter.antidote.crdt;

import eu.antidotedb.client.Key;
import eu.antidotedb.client.MapKey;
import eu.antidotedb.client.UpdateOp;

public class MapAWService {

    public UpdateOp update(MapKey mapKey, UpdateOp update) {
        return mapKey.update(update);
    }

    public Key getKey(String mapId){
        return Key.map_aw(mapId);
    }

}
