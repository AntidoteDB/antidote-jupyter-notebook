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
    public void testIncrement() {

        CounterKey key = (CounterKey)service.getKey("counter_test_increment_key");
        antidoteService.applyUpdate(service.incrementCounter(key, 1));
        antidoteService.applyUpdate(service.incrementCounter(key, 2));

        int readValue = (Integer) antidoteService.readByKey(key);
        assertEquals(3, readValue);
    }

}
