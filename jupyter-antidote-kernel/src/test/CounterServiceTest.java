import eu.antidote.jupyter.antidote.crdt.CounterService;
import eu.antidotedb.client.CounterKey;
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
        service = new CounterService();
    }

    @Test
    public void testIncrementInteger() {

        CounterKey key1 = (CounterKey)service.getKey("key1");
        antidoteService.applyUpdate(service.incrementCounter(key1, 1));
        antidoteService.applyUpdate(service.incrementCounter(key1, 2));

        int readValue = (Integer) antidoteService.readByKey(key1);
        assertEquals(3, readValue);
    }

}
