import eu.antidote.jupyter.antidote.crdt.SetRWService;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SetRWServiceTest extends AbstractAntidoteTest{
    private SetRWService service;
    public SetRWServiceTest(){
        super();
        service = new SetRWService(antidoteService);
    }

    @Test
    public void testAddOneValueToRWSet(){
        antidoteService.applyUpdate(service.addToRWSet("key1", "testval"));
        assertEquals("testval", service.readRWSet("key1").get(0));
    }

    @Test
    public void testAddValuesToRWSet(){
        antidoteService.applyUpdate(service.addToRWSet("key2", "testval1", "testval2"));
        assertEquals(2, service.readRWSet("key2").size());
        assertEquals("testval2", service.readRWSet("key2").get(1));
    }

    @Test
    public void testRemoveValueFromRWSet(){
        antidoteService.applyUpdate(service.addToRWSet("key3", "testval1", "testval2"));
        antidoteService.applyUpdate(service.removeFromRWSet("key3", "testval2"));

        assertEquals(1, service.readRWSet("key3").size());
        assertEquals("testval1", service.readRWSet("key3").get(0));
    }

    @Test
    public void testRemoveValuesFromRWSet(){
        antidoteService.applyUpdate(service.addToRWSet("key4", "testval1", "testval2", "testval3"));
        antidoteService.applyUpdate(service.removeFromRWSet("key4", "testval1", "testval3"));

        assertEquals(1, service.readRWSet("key4").size());
        assertEquals("testval2", service.readRWSet("key4").get(0));
    }
}
