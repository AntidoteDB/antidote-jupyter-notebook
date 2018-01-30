import eu.antidote.jupyter.antidote.crdt.RWSetService;
import eu.antidotedb.client.SetKey;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class RWSetServiceTest extends AbstractAntidoteTest{
    private RWSetService service;
    public RWSetServiceTest(){
        super();
        service = new RWSetService();
    }

    @Test
    public void testAddOneValueToRWSet(){
        SetKey<String> setKey = service.getKey("key1");
        antidoteService.applyUpdate(service.addToRWSet(setKey, "testval"));
        assertEquals("testval", ((List<String>)antidoteService.readByKey(setKey)).get(0));
    }

    @Test
    public void testAddValuesToRWSet(){
        SetKey<String> setKey = service.getKey("key2");
        antidoteService.applyUpdate(service.addToRWSet(setKey, "testval1", "testval2"));
        List<String> readValues = (List<String>) antidoteService.readByKey(setKey);
        assertEquals(2, readValues.size());
        assertEquals("testval2", readValues.get(1));
    }

    @Test
    public void testRemoveValueFromRWSet(){
        SetKey<String> setKey = service.getKey("key3");
        antidoteService.applyUpdate(service.addToRWSet(setKey, "testval1", "testval2"));
        antidoteService.applyUpdate(service.removeFromRWSet(setKey, "testval2"));
        List<String> readValues = (List<String>) antidoteService.readByKey(setKey);
        assertEquals(1, readValues.size());
        assertEquals("testval1", readValues.get(0));
    }

    @Test
    public void testRemoveValuesFromRWSet(){
        SetKey<String> setKey = service.getKey("key4");
        antidoteService.applyUpdate(service.addToRWSet(setKey, "testval1", "testval2", "testval3"));
        antidoteService.applyUpdate(service.removeFromRWSet(setKey, "testval1", "testval3"));
        List<String> readValues = (List<String>) antidoteService.readByKey(setKey);

        assertEquals(1, readValues.size());
        assertEquals("testval2", readValues.get(0));
    }
}
