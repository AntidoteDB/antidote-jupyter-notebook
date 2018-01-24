import eu.antidote.jupyter.antidote.AntidoteService;
import eu.antidote.jupyter.antidote.crdt.IntegerService;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Test for RegisterService
 * Make sure you have an AntidoteDB running on port 8087
 */
public class IntegerServiceTest extends AbstractAntidoteTest{

    IntegerService service;
    public IntegerServiceTest(){
        super();
        service = new IntegerService(antidoteService);
    }

    @Test
    public void testNewInteger(){

        service.newInteger("key1", 1);

        long readValue = service.readInteger("key1");
        assertEquals(readValue, 1);
    }

    @Test
    public void testIncrementIntger() {

        service.newInteger("key2", 1);
        service.incrementInteger("key2", 3);

        long readValue = service.readInteger("key2");
        assertEquals(readValue, 4);
    }

}
