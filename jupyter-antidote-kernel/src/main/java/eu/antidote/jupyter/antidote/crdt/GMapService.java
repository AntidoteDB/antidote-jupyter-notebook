package eu.antidote.jupyter.antidote.crdt;

import eu.antidotedb.client.Key;
import eu.antidotedb.client.MapKey;
import eu.antidotedb.client.UpdateOp;

public class GMapService {

    public UpdateOp updateMap(MapKey mapKey, UpdateOp... updates) {
        return mapKey.update(updates);
    }

    public MapKey getKey(String mapId){ return Key.map_g(mapId); }

}
