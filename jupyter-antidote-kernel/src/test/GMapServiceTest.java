import eu.antidote.jupyter.antidote.crdt.CounterService;
import eu.antidote.jupyter.antidote.crdt.IntegerService;
import eu.antidote.jupyter.antidote.crdt.GMapService;
import eu.antidote.jupyter.antidote.crdt.SetService;
import eu.antidotedb.client.*;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class GMapServiceTest extends AbstractAntidoteTest {
    private GMapService map_service;
    private IntegerService int_service;
    private CounterService counter_service;
    private SetService set_service;

    public GMapServiceTest() {
        super();
        map_service = new GMapService();
        int_service = new IntegerService();
        counter_service = new CounterService();
        set_service = new SetService();
    }

    @Test
    public void testUpdateMapG() {

        MapKey mapKey = map_service.getKey("key1");
        IntegerKey x_key = int_service.getKey("x");

        UpdateOp update = map_service.updateMap(mapKey, int_service.assignInteger(x_key, 1));
        antidoteService.applyUpdate(update);

        long readValue = (Long) antidoteService.readKeyInMap(mapKey, x_key);
        assertEquals(1, readValue);

    }

    @Test
    public void testUpdatesMapG() {

        MapKey mapKey = map_service.getKey("key2");
        IntegerKey y_key = int_service.getKey("y");
        CounterKey z_key = counter_service.getKey("z");

        UpdateOp update = map_service.updateMap(mapKey, int_service.assignInteger(y_key, 1),
                                                        counter_service.incrementCounter(z_key,3));

        antidoteService.applyUpdate(update);

        long readValue_y = (Long) antidoteService.readKeyInMap(mapKey, y_key);
        assertEquals(1, readValue_y);

        int readValue_z = (Integer) antidoteService.readKeyInMap(mapKey, z_key);
        assertEquals(3, readValue_z);

    }

    @Test
    public void testNotExistsMapG() {

        MapKey mapKey = map_service.getKey("key2");
        SetKey<String> notExistsSetKey = set_service.getKey("notExistsSet");

        List<String> res1 = (List<String>) antidoteService.readKeyInMap(mapKey, notExistsSetKey);
        assertEquals(Collections.emptyList(), res1);

    }


}

