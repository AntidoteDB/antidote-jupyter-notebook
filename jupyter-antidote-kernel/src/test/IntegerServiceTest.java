import eu.antidote.jupyter.antidote.crdt.IntegerService;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Test for RegisterService
 * Make sure you have an AntidoteDB running on port 8087
 */
public class IntegerServiceTest extends AbstractAntidoteTest{

    private IntegerService service;
    public IntegerServiceTest(){
        super();
        service = new IntegerService(antidoteService);
    }

    @Test
    public void testAssignInteger(){

        antidoteService.applyUpdate(service.assignInteger("key1", 1));

        long readValue = service.readInteger("key1");
        assertEquals(1, readValue);
    }

    @Test
    public void testIncrementInteger() {

        antidoteService.applyUpdate(service.assignInteger("key2", 1));
        antidoteService.applyUpdate(service.incrementInteger("key2", 3));

        long readValue = service.readInteger("key2");
        assertEquals(4, readValue);
    }

}
