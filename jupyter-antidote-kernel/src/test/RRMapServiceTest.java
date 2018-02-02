import eu.antidote.jupyter.antidote.crdt.CounterService;
import eu.antidote.jupyter.antidote.crdt.IntegerService;
import eu.antidote.jupyter.antidote.crdt.RRMapService;
import eu.antidote.jupyter.antidote.crdt.SetService;
import eu.antidotedb.client.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RRMapServiceTest extends AbstractAntidoteTest {
    private RRMapService map_service;
    private IntegerService int_service;
    private CounterService counter_service;
    private SetService set_service;

    public RRMapServiceTest() {
        super();
        map_service = new RRMapService();
        int_service = new IntegerService();
        counter_service = new CounterService();
        set_service = new SetService();
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
        IntegerKey y_key = int_service.getKey("map_rr_test_removes_y_key");
        CounterKey z_key = counter_service.getKey("map_rr_test_removes_z_key");

        InteractiveTransaction tx = antidoteService.startTransaction();
        antidoteService.addToTransaction(tx, map_service.updateMap(mapKey, int_service.assignInteger(y_key, 1),
                                                                            counter_service.incrementCounter(z_key,3)));
        antidoteService.addToTransaction(tx, map_service.removeKey(mapKey, y_key, z_key));
        antidoteService.commitTransaction(tx);

        long readValue_y = (Long) antidoteService.readKeyInMap(mapKey, y_key);
        assertEquals(0, readValue_y);

        int readValue_z = (Integer) antidoteService.readKeyInMap(mapKey, z_key);
        assertEquals(0, readValue_z);
    }

    @Test
    public void testResetMapRR() {

        MapKey mapKey = map_service.getKey("map_rr_test_reset_map_key");
        IntegerKey y_key = int_service.getKey("map_rr_test_reset_y_key");

        InteractiveTransaction tx = antidoteService.startTransaction();
        antidoteService.addToTransaction(tx, map_service.updateMap(mapKey, int_service.assignInteger(y_key, 1)));
        antidoteService.addToTransaction(tx, map_service.updateMap(mapKey, map_service.reset(mapKey)));
        antidoteService.commitTransaction(tx);

        long readValue_y = (Long) antidoteService.readKeyInMap(mapKey, y_key);
        assertEquals(0, readValue_y);

    }


}

