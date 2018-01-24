import eu.antidote.jupyter.antidote.crdt.SetSetvice;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class SetServiceTest extends AbstractAntidoteTest {
    SetSetvice service;
    public SetServiceTest(){
        super();
        service = new SetSetvice(antidoteService);
    }

    @Test
    public void testAddOneValueToSet(){
        service.addToSet("key1", "testval");
        assertEquals("testval", service.readSet("key1").get(0));
    }

    @Test
    public void testAddValuesToSet(){
        service.addToSet("key2", "testval1", "testval2");
        assertEquals(2, service.readSet("key2").size());
        assertEquals("testval2", service.readSet("key2").get(1));
    }

    @Test
    public void testRemoveValueFromSet(){
        service.addToSet("key3", "testval1", "testval2");
        service.removeFromSet("key3", "testval2");

        assertEquals(1, service.readSet("key3").size());
        assertEquals("testval1", service.readSet("key3").get(0));
    }

    @Test
    public void testRemoveValuesFromSet(){
        service.addToSet("key4", "testval1", "testval2", "testval3");
        service.removeFromSet("key4", "testval1", "testval3");

        assertEquals(1, service.readSet("key4").size());
        assertEquals("testval2", service.readSet("key4").get(0));
    }
}
