import eu.antidote.jupyter.antidote.crdt.*;
import eu.antidotedb.client.*;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RRMapServiceTest extends AbstractAntidoteTest {
    private RRMapService map_service;
    private IntegerService int_service;
    private CounterService counter_service;
    private FatCounterService fatcounter_service;
    private SetService set_service;
    private RegisterService register_service;

    public RRMapServiceTest() {
        super();
        map_service = new RRMapService();
        int_service = new IntegerService();
        counter_service = new CounterService();
        fatcounter_service = new FatCounterService();
        set_service = new SetService();
        register_service = new RegisterService();
    }

    @Test
    public void testUpdatesMapRR() {

        MapKey mapKey = map_service.getKey("map_rr_test_updates_map_key");
        IntegerKey y_key = int_service.getKey("map_rr_test_updates_y_key");
        CounterKey z_key = counter_service.getKey("map_rr_test_updates_z_key");

        UpdateOp update = map_service.updateMap(mapKey, int_service.assignInteger(y_key, 1),
                                                        counter_service.incrementCounter(z_key,3));

        antidoteService.applyUpdate(update);

        long readValue_y = (Long) antidoteService.readKeyInMap(mapKey, y_key);
        assertEquals(1, readValue_y);

        int readValue_z = (Integer) antidoteService.readKeyInMap(mapKey, z_key);
        assertEquals(3, readValue_z);

    }

    @Test
    public void testRemovesMapRR() {

        MapKey mapKey = map_service.getKey("map_rr_test_removes_map_key");
        CounterKey x_key = fatcounter_service.getKey("map_rr_test_removes_x_key");
        CounterKey y_key = fatcounter_service.getKey("map_rr_test_removes_y_key");

        antidoteService.applyUpdate(map_service.updateMap(mapKey, fatcounter_service.incrementFatCounter(x_key,1),
                                                                fatcounter_service.incrementFatCounter(y_key,2)));

        int readValue_y = (Integer) antidoteService.readKeyInMap(mapKey, x_key);
        assertEquals(1, readValue_y);

        int readValue_z = (Integer) antidoteService.readKeyInMap(mapKey, y_key);
        assertEquals(2, readValue_z);

        antidoteService.applyUpdate(map_service.updateMap(mapKey, fatcounter_service.resetFatCounter(y_key)));
        antidoteService.applyUpdate(map_service.removeKey(mapKey, x_key));
        antidoteService.applyUpdate(map_service.removeKey(mapKey, y_key));

        MapKey.MapReadResult readValue_map = (MapKey.MapReadResult) antidoteService.readByKey(mapKey);
        assertEquals(1, readValue_map.size());
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

