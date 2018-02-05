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
    public void testIncrementFatCounter() {

        CounterKey key = service.getKey("fatcounter_test_increment_key");
        antidoteService.applyUpdate(service.incrementFatCounter(key, 1));
        antidoteService.applyUpdate(service.incrementFatCounter(key, 2));

        int readValue = (Integer) antidoteService.readByKey(key);
        assertEquals(3, readValue);
    }

    @Test
    public void testResetFatCounter() {

        CounterKey key = service.getKey("fatcounter_test_reset_key");

        antidoteService.applyUpdate(service.incrementFatCounter(key, 1));

        int readValue = (Integer) antidoteService.readByKey(key);
        assertEquals(1, readValue);

        antidoteService.applyUpdate(service.resetFatCounter(key));

        readValue = (Integer) antidoteService.readByKey(key);
        assertEquals(0, readValue);
    }
}
