import eu.antidote.jupyter.antidote.crdt.CounterService;
import eu.antidote.jupyter.antidote.crdt.GMapService;
import eu.antidotedb.client.CounterKey;
import eu.antidotedb.client.Key;
import eu.antidotedb.client.MapKey;
import eu.antidotedb.client.UpdateOp;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GMapServiceTest extends AbstractAntidoteTest {
    private GMapService map_service;
    private CounterService counter_service;

    public GMapServiceTest() {
        super();
        map_service = new GMapService();
        counter_service = new CounterService();
    }

    @Test
    public void testUpdatesMapG() {

        MapKey mapKey = map_service.getKey("map_g_test_update_map_key");
        CounterKey z_key = counter_service.getKey("map_g_test_update_z");

        UpdateOp update = map_service.updateMap(mapKey, counter_service.incrementCounter(z_key,3));

        antidoteService.applyUpdate(update);

        int readValue_z = (Integer) antidoteService.readKeyInMap(mapKey, z_key);
        assertEquals(3, readValue_z);

    }


    @Test
    public void testMapInMap(){
        MapKey mapKeyParent = map_service.getKey("map_g_parent_mapkey");
        MapKey mapKeyChild = map_service.getKey("map_g_child_mapkey");
        CounterKey counter_key = counter_service.getKey("map_g_int_in_child_key");
        UpdateOp update = map_service.updateMap(mapKeyParent, map_service.updateMap(mapKeyChild,
                counter_service.incrementCounter(counter_key, 42)));

        antidoteService.applyUpdate(update);
        MapKey.MapReadResult a = (MapKey.MapReadResult) antidoteService.readKeyInMap(mapKeyParent, mapKeyChild);
        int intVal =(Integer) antidoteService.readKeyInMapResult(a, counter_key);

        assertEquals(42, intVal);
    }
}

