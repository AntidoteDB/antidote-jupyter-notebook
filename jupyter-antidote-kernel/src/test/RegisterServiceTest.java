import eu.antidote.jupyter.antidote.crdt.RegisterService;
import eu.antidotedb.client.UpdateOp;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Test for RegisterService
 *
 */
public class RegisterServiceTest extends AbstractAntidoteTest{

    RegisterService service;
    public RegisterServiceTest(){
        super();
        service = new RegisterService(antidoteService);
    }

    @Test
    public void testUpdateRegister(){
        UpdateOp registerUpdate = service.assignRegister("key1", "testValue");
        antidoteService.applyUpdate(registerUpdate);
        String readValue = service.readRegister("key1");
        assertEquals(readValue, "testValue");
    }

}
