import eu.antidote.jupyter.antidote.crdt.IntegerService;
import eu.antidotedb.client.IntegerKey;
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
        service = new IntegerService();
    }

    @Test
    public void testAssignInteger(){

        IntegerKey key1 = (IntegerKey)service.getKey("integer_test_key1");
        antidoteService.applyUpdate(service.assignInteger(key1, 1));

        long readValue = (Long) antidoteService.readByKey(key1);
        assertEquals(1, readValue);
    }

    @Test
    public void testIncrementInteger() {

        IntegerKey key2 = (IntegerKey)service.getKey("integer_test_key2");
        antidoteService.applyUpdate(service.assignInteger(key2, 1));
        antidoteService.applyUpdate(service.incrementInteger(key2, 3));

        long readValue = (Long) antidoteService.readByKey(key2);
        assertEquals(4, readValue);
    }

}
