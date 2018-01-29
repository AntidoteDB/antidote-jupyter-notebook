import eu.antidote.jupyter.antidote.crdt.FatCounterService;
import eu.antidotedb.client.CounterKey;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test for RegisterService
 * Make sure you have an AntidoteDB running on port 8087
 */
public class FatCounterServiceTest extends AbstractAntidoteTest{

    private FatCounterService service;
    public FatCounterServiceTest(){
        super();
        service = new FatCounterService();
    }

    @Test
    public void testIncrementInteger() {

        CounterKey key1 = (CounterKey)service.getKey("key1");
        antidoteService.applyUpdate(service.incrementFatCounter(key1, 1));
        antidoteService.applyUpdate(service.incrementFatCounter(key1, 2));

        int readValue = (Integer) antidoteService.readByKey(key1);
        assertEquals(3, readValue);
    }

    @Test
    public void testResetInteger() {

        CounterKey key1 = (CounterKey)service.getKey("key1");
        antidoteService.applyUpdate(service.resetFatCounter(key1));

        int readValue = (Integer) antidoteService.readByKey(key1);
        assertEquals(0, readValue);
    }
}
