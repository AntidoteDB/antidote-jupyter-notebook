import eu.antidote.jupyter.antidote.crdt.SetService;
import eu.antidotedb.client.SetKey;
import eu.antidotedb.client.UpdateOp;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class SetServiceTest extends AbstractAntidoteTest {
    SetService service;
    public SetServiceTest(){
        super();
        service = new SetService();
    }

    @Test
    public void testAddOneValueToSet(){
        SetKey<String> setKey = service.getKey("key1");
        UpdateOp updateOp=service.addToSet(setKey, "testval");
        antidoteService.applyUpdate(updateOp);

        List<String> readValues = (List<String>) antidoteService.readByKey(setKey);
        assertEquals("testval", readValues.get(0));
    }

    @Test
    public void testAddValuesToSet(){
        SetKey<String> setKey = service.getKey("key2");
        antidoteService.applyUpdate(service.addToSet(setKey, "testval1", "testval2"));
        List<String> readValues = (List<String>) antidoteService.readByKey(setKey);
        assertEquals(2, readValues.size());
        assertEquals("testval2", readValues.get(1));
    }

    @Test
    public void testRemoveValueFromSet(){

        SetKey<String> setKey = service.getKey("key3");
        antidoteService.applyUpdate(service.addToSet(setKey, "testval1", "testval2"));
        antidoteService.applyUpdate(service.removeFromSet(setKey, "testval2"));
        List<String> readValues = (List<String>) antidoteService.readByKey(setKey);

        assertEquals(1, readValues.size());
        assertEquals("testval1",readValues.get(0));
    }

    @Test
    public void testRemoveValuesFromSet(){
        SetKey<String> setKey = service.getKey("key4");
        antidoteService.applyUpdate(service.addToSet(setKey, "testval1", "testval2", "testval3"));
        antidoteService.applyUpdate(service.removeFromSet(setKey, "testval1", "testval3"));

        List<String> readValues = (List<String>) antidoteService.readByKey(setKey);
        assertEquals(1, readValues.size());
        assertEquals("testval2", readValues.get(0));
    }
}
