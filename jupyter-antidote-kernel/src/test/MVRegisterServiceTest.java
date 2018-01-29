import eu.antidote.jupyter.antidote.crdt.MultiValueRegisterService;
import eu.antidotedb.client.MVRegisterKey;
import eu.antidotedb.client.UpdateOp;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class MVRegisterServiceTest extends AbstractAntidoteTest{

    MultiValueRegisterService service;
    public MVRegisterServiceTest(){
        super();
        service = new MultiValueRegisterService();
    }

    @Test
    public void testUpdateRegister(){
        MVRegisterKey<String> mvRegisterKey = service.getKey("key1");
        UpdateOp update = service.assignRegister(mvRegisterKey, "testValue");
        antidoteService.applyUpdate(update);
        List<String> readValue = (List<String>) antidoteService.readByKey(mvRegisterKey);
        assertEquals(readValue.get(0), "testValue");
    }

    @Ignore("Needs to be done with concurrent databases")
    @Test
    public void testUpdateMultiValue(){
        MVRegisterKey<String> mvRegisterKey1 = service.getKey("key1");
        MVRegisterKey<String> mvRegisterKey2 = service.getKey("key2");
        service.assignRegister(mvRegisterKey1, "testValue1");
        service.assignRegister(mvRegisterKey2, "testValue2");

        List<String> readValues = (List<String>) antidoteService.readByKey(mvRegisterKey2);
        assertEquals(2, readValues.size());
        assertEquals("testValue2", readValues.get(1));
    }
}
