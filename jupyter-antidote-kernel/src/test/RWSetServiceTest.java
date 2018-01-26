import eu.antidote.jupyter.antidote.crdt.SetRWService;
import eu.antidote.jupyter.antidote.crdt.SetService;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RWSetServiceTest extends AbstractAntidoteTest{
    SetRWService service;
    public RWSetServiceTest(){
        super();
        service = new SetRWService(antidoteService);
    }

    @Test
    public void testAddOneValueToRWSet(){
        service.addToRWSet("key1", "testval");
        assertEquals("testval", service.readRWSet("key1").get(0));
    }

    @Test
    public void testAddValuesToRWSet(){
        service.addToRWSet("key2", "testval1", "testval2");
        assertEquals(2, service.readRWSet("key2").size());
        assertEquals("testval2", service.readRWSet("key2").get(1));
    }

    @Test
    public void testRemoveValueFromRWSet(){
        service.addToRWSet("key3", "testval1", "testval2");
        service.removeFromRWSet("key3", "testval2");

        assertEquals(1, service.readRWSet("key3").size());
        assertEquals("testval1", service.readRWSet("key3").get(0));
    }

    @Test
    public void testRemoveValuesFromRWSet(){
        service.addToRWSet("key4", "testval1", "testval2", "testval3");
        service.removeFromRWSet("key4", "testval1", "testval3");

        assertEquals(1, service.readRWSet("key4").size());
        assertEquals("testval2", service.readRWSet("key4").get(0));
    }
}
