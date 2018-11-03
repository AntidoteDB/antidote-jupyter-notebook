import eu.antidote.jupyter.antidote.crdt.RegisterService;
import eu.antidotedb.client.RegisterKey;
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
        service = new RegisterService();
    }

    @Test
    public void testUpdateRegister() {
        RegisterKey<String> registerKey = service.getKey("regkey1");
        UpdateOp registerUpdate = service.assignRegister(registerKey, "testValue");
        antidoteService.applyUpdate(registerUpdate);
        String readValue = (String) antidoteService.readByKey(registerKey);
        assertEquals(readValue, "testValue");
    }

}
