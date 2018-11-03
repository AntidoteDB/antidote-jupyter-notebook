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

        IntegerKey key = (IntegerKey)service.getKey("integer_test_assign_key");
        antidoteService.applyUpdate(service.assignInteger(key, 1));

        long readValue = (Long) antidoteService.readByKey(key);
        assertEquals(1, readValue);
    }

    @Test
    public void testIncrementInteger() {

        IntegerKey key = (IntegerKey)service.getKey("integer_test_increment_key");
        antidoteService.applyUpdate(service.assignInteger(key, 1));
        antidoteService.applyUpdate(service.incrementInteger(key, 3));

        long readValue = (Long) antidoteService.readByKey(key);
        assertEquals(4, readValue);
    }

}
