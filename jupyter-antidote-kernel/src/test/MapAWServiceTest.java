import eu.antidote.jupyter.antidote.crdt.CounterService;
import eu.antidote.jupyter.antidote.crdt.IntegerService;
import eu.antidote.jupyter.antidote.crdt.MapAWService;
import eu.antidote.jupyter.antidote.crdt.SetService;
import eu.antidotedb.client.*;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static eu.antidotedb.client.Key.map_g;
import static eu.antidotedb.client.Key.set;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static sun.security.krb5.Confounder.longValue;

public class MapAWServiceTest extends AbstractAntidoteTest {
    MapAWService map_service;
    IntegerService int_service;
    CounterService counter_service;
    SetService set_service;

    public MapAWServiceTest() {
        super();
        map_service = new MapAWService();
        int_service = new IntegerService();
        counter_service = new CounterService();
        set_service = new SetService();
    }

    @Test
    public void testUpdateMapAW() {

        MapKey mapKey = map_service.getKey("key1");
        IntegerKey x_key = int_service.getKey("x");

        UpdateOp update = map_service.updateMap(mapKey, int_service.assignInteger(x_key, 1));
        antidoteService.applyUpdate(update);

        long readValue = (Long) antidoteService.readKeyInMap(mapKey, x_key);
        assertEquals(1, readValue);

    }

    @Test
    public void testRemoveMapAW() {

        MapKey mapKey = map_service.getKey("key1");
        IntegerKey x_key = int_service.getKey("x");

        UpdateOp update = map_service.removeKey(mapKey, x_key);
        antidoteService.applyUpdate(update);

        long readValue = (Long) antidoteService.readKeyInMap(mapKey, x_key);
        assertEquals(0, readValue);

    }

    @Test
    public void testUpdatesMapAW() {

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
    public void testRemovesMapAW() {

        MapKey mapKey = map_service.getKey("key2");
        IntegerKey y_key = int_service.getKey("y");
        CounterKey z_key = counter_service.getKey("z");

        UpdateOp update = map_service.removeKey(mapKey, y_key, z_key);
        antidoteService.applyUpdate(update);

        long readValue_y = (Long) antidoteService.readKeyInMap(mapKey, y_key);
        assertEquals(0, readValue_y);

        int readValue_z = (Integer) antidoteService.readKeyInMap(mapKey, z_key);
        assertEquals(0, readValue_z);

    }

    @Test
    public void testNotExistsMapAW() {

        MapKey mapKey = map_service.getKey("key2");
        SetKey<String> notExistsSetKey = set_service.getKey("notExistsSet");

        List<String> res1 = (List<String>) antidoteService.readKeyInMap(mapKey, notExistsSetKey);
        assertEquals(Collections.emptyList(), res1);

    }


}

