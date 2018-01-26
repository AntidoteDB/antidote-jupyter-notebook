import eu.antidote.jupyter.antidote.crdt.CounterService;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Test for RegisterService
 * Make sure you have an AntidoteDB running on port 8087
 */
public class CounterServiceTest extends AbstractAntidoteTest{

    private CounterService service;
    public CounterServiceTest(){
        super();
        service = new CounterService(antidoteService);
    }

    @Test
    public void testIncrementInteger() {

        antidoteService.applyUpdate(service.incrementCounter("key1", 1));
        antidoteService.applyUpdate(service.incrementCounter("key1", 2));

        int readValue = service.readCounter("key1");
        assertEquals(3, readValue);
    }

}
