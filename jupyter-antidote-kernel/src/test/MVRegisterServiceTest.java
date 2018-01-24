import eu.antidote.jupyter.antidote.crdt.MultiValueRegisterService;
import eu.antidote.jupyter.antidote.crdt.RegisterService;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class MVRegisterServiceTest extends AbstractAntidoteTest{

    MultiValueRegisterService service;
    public MVRegisterServiceTest(){
        super();
        service = new MultiValueRegisterService(antidoteService);
    }

    @Test
    public void testUpdateRegister(){
        service.updateRegister("key1", "testValue");

        List<String> readValue = service.readRegister("key1");
        assertEquals(readValue.get(0), "testValue");
    }

    @Test
    public void testUpdateMultiValue(){
        service.updateRegister("key2", "testValue1");
        service.updateRegister("key2", "testValue2");

        List<String> readValues = service.readRegister("key2");
        assertEquals(2, readValues.size());
        assertEquals("testValue2", readValues.get(1));
    }
}
