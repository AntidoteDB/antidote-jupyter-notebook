import eu.antidote.jupyter.antidote.crdt.FatCounterService;
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
        service = new FatCounterService(antidoteService);
    }

    @Test
    public void testIncrementInteger() {

        antidoteService.applyUpdate(service.incrementFatCounter("key1", 1));
        antidoteService.applyUpdate(service.incrementFatCounter("key1", 2));

        int readValue = service.readFatCounter("key1");
        assertEquals(3, readValue);
    }

    @Test
    public void testResetInteger() {
        antidoteService.applyUpdate(service.resetFatCounter("key1"));

        int readValue = service.readFatCounter("key1");
        assertEquals(0, readValue);
    }
}
