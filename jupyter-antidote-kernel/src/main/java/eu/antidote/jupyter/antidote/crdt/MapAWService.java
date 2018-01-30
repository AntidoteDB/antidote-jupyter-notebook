package eu.antidote.jupyter.antidote.crdt;

import eu.antidotedb.client.Key;
import eu.antidotedb.client.MapKey;
import eu.antidotedb.client.UpdateOp;

import java.util.Arrays;
import java.util.Map;

public class MapAWService {

    public UpdateOp updateMap(MapKey mapKey, UpdateOp... updates) {
        return mapKey.update(updates);
    }

    public UpdateOp removeKey(MapKey mapKey, Key... removedKeys) {
        return mapKey.removeKeys(removedKeys);
    }

    public MapKey getKey(String mapId){
        return Key.map_aw(mapId);
    }

}
