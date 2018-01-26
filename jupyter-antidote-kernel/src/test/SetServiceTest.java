import eu.antidote.jupyter.antidote.crdt.SetService;
import static org.junit.Assert.assertEquals;

import eu.antidotedb.client.UpdateOp;
import org.junit.Test;

public class SetServiceTest extends AbstractAntidoteTest {
    SetService service;
    public SetServiceTest(){
        super();
        service = new SetService(antidoteService);
    }

    @Test
    public void testAddOneValueToSet(){
        UpdateOp updateOp=service.addToSet("key1", "testval");
        antidoteService.applyUpdate(updateOp);
        assertEquals("testval", service.readSet("key1").get(0));
    }

    @Test
    public void testAddValuesToSet(){
        antidoteService.applyUpdate(service.addToSet("key2", "testval1", "testval2"));
        assertEquals(2, service.readSet("key2").size());
        assertEquals("testval2", service.readSet("key2").get(1));
    }

    @Test
    public void testRemoveValueFromSet(){
        antidoteService.applyUpdate(service.addToSet("key3", "testval1", "testval2"));
        antidoteService.applyUpdate(service.removeFromSet("key3", "testval2"));

        assertEquals(1, service.readSet("key3").size());
        assertEquals("testval1", service.readSet("key3").get(0));
    }

    @Test
    public void testRemoveValuesFromSet(){
        antidoteService.applyUpdate(service.addToSet("key4", "testval1", "testval2", "testval3"));
        antidoteService.applyUpdate(service.removeFromSet("key4", "testval1", "testval3"));

        assertEquals(1, service.readSet("key4").size());
        assertEquals("testval2", service.readSet("key4").get(0));
    }
}
