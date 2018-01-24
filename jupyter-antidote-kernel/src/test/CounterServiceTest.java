import eu.antidote.jupyter.antidote.AntidoteService;
import eu.antidote.jupyter.antidote.crdt.CounterService;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Test for RegisterService
 * Make sure you have an AntidoteDB running on port 8087
 */
public class CounterServiceTest extends AbstractAntidoteTest{

    CounterService service;
    public CounterServiceTest(){
        super();
        service = new CounterService(antidoteService);
    }

    @Test
    public void testIncrementIntger() {

        service.incrementCounter("key1", 1);
        service.incrementCounter("key1", 2);

        int readValue = service.readCounter("key1");
        assertEquals(3, readValue);
    }

}
