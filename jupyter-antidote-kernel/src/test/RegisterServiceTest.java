import eu.antidote.jupyter.antidote.AntidoteService;
import eu.antidote.jupyter.antidote.crdt.RegisterService;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Test for RegisterService
 * Make sure you have an AntidoteDB running on port 8087
 */
public class RegisterServiceTest extends AbstractAntidoteTest{

    RegisterService service;
    public RegisterServiceTest(){
        super();
        service = new RegisterService(antidoteService);
    }

    @Test
    public void testUpdateRegister(){
        service.updateRegister("key1", "testValue");

        String readValue = service.readRegister("key1");
        assertEquals(readValue, "testValue");
    }

}
