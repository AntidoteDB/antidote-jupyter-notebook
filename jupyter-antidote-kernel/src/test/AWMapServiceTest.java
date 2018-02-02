import eu.antidote.jupyter.antidote.crdt.CounterService;
import eu.antidote.jupyter.antidote.crdt.IntegerService;
import eu.antidote.jupyter.antidote.crdt.AWMapService;
import eu.antidote.jupyter.antidote.crdt.SetService;
import eu.antidotedb.client.*;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AWMapServiceTest extends AbstractAntidoteTest {
    private AWMapService map_service;
    private IntegerService int_service;
    private CounterService counter_service;
    private SetService set_service;

    public AWMapServiceTest() {
        super();
        map_service = new AWMapService();
        int_service = new IntegerService();
        counter_service = new CounterService();
        set_service = new SetService();
    }

    @Test
    public void testUpdatesMapAW() {

        MapKey mapKey = map_service.getKey("map_aw_test_updates_map_key");
        IntegerKey y_key = int_service.getKey("map_aw_test_updates_y_key");
        CounterKey z_key = counter_service.getKey("map_aw_test_updates_z_key");

        UpdateOp update = map_service.updateMap(mapKey, int_service.assignInteger(y_key, 1),
                                                        counter_service.incrementCounter(z_key,3));

        antidoteService.applyUpdate(update);

        long readValue_y = (Long) antidoteService.readKeyInMap(mapKey, y_key);
        assertEquals(1, readValue_y);

        int readValue_z = (Integer) antidoteService.readKeyInMap(mapKey, z_key);
        assertEquals(3, readValue_z);

    }

    @Test
    public void testRemovesMapAW() {

        MapKey mapKey = map_service.getKey("map_aw_test_removes_map_key");
        IntegerKey y_key = int_service.getKey("map_aw_test_removes_y_key");
        CounterKey z_key = counter_service.getKey("map_aw_test_removes_z_key");

        antidoteService.applyUpdate(map_service.updateMap(mapKey, int_service.assignInteger(y_key, 1),
                counter_service.incrementCounter(z_key,3)));
        antidoteService.applyUpdate(map_service.removeKey(mapKey, y_key, z_key));

        long readValue_y = (Long) antidoteService.readKeyInMap(mapKey, y_key);
        assertEquals(0, readValue_y);

        int readValue_z = (Integer) antidoteService.readKeyInMap(mapKey, z_key);
        assertEquals(0, readValue_z);
    }

}

