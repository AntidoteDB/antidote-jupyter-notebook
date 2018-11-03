import eu.antidote.jupyter.antidote.crdt.*;
import eu.antidotedb.client.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RRMapServiceTest extends AbstractAntidoteTest {
    private RRMapService map_service;
    private CounterService counter_service;
    private SetService set_service;

    public RRMapServiceTest() {
        super();
        map_service = new RRMapService();
        counter_service = new CounterService();
        set_service = new SetService();
    }

    @Test
    public void testUpdatesMapRR() {

        MapKey mapKey = map_service.getKey("map_rr_test_updates_map_key");
        CounterKey z_key = counter_service.getKey("map_rr_test_updates_z_key");

        UpdateOp update = map_service.updateMap(mapKey, counter_service.incrementCounter(z_key,3));

        antidoteService.applyUpdate(update);

        int readValue_z = (Integer) antidoteService.readKeyInMap(mapKey, z_key);
        assertEquals(3, readValue_z);

    }

    @Test
    public void testRemovesMapRR() {

        MapKey mapKey = map_service.getKey("map_rr_test_removes_map_key");
        SetKey x_key = set_service.getKey("map_rr_test_removes_x_key");
        SetKey y_key = set_service.getKey("map_rr_test_removes_y_key");

        antidoteService.applyUpdate(map_service.updateMap(mapKey, set_service.addToSet(x_key,"A")));
        antidoteService.applyUpdate(map_service.removeKey(mapKey, x_key));
        antidoteService.applyUpdate(map_service.updateMap(mapKey, set_service.addToSet(y_key,"A")));
        antidoteService.applyUpdate(map_service.updateMap(mapKey, set_service.removeFromSet(y_key,"A")));

        MapKey.MapReadResult readValue_map = (MapKey.MapReadResult) antidoteService.readByKey(mapKey);
        assertEquals(0, readValue_map.size());
    }

    @Test
    public void testResetMapRR() {

        MapKey mapKey = map_service.getKey("map_rr_test_reset_map_key");
        SetKey x_key = set_service.getKey("map_rr_test_reset_x_key");
        SetKey y_key = set_service.getKey("map_rr_test_reset_y_key");

        antidoteService.applyUpdate(map_service.updateMap(mapKey, set_service.addToSet(x_key, "1","2","3")));
        antidoteService.applyUpdate(map_service.updateMap(mapKey, set_service.addToSet(y_key, "4","5")));
        antidoteService.applyUpdate(map_service.reset(mapKey));

        MapKey.MapReadResult readValue_map = (MapKey.MapReadResult) antidoteService.readByKey(mapKey);
        assertTrue(readValue_map.isEmpty());

    }


}

