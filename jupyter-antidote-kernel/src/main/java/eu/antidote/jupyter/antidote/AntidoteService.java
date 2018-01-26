package eu.antidote.jupyter.antidote;

import eu.antidote.jupyter.antidote.crdt.*;
import eu.antidotedb.client.*;
import eu.antidotedb.client.transformer.CountingTransformer;
import eu.antidotedb.client.transformer.TransformerFactory;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class AntidoteService {

    final private AntidoteClient antidoteClient;
    final CountingTransformer messageCounter;
    final private Bucket bucket;
    final String bucketKey;
    final SecureRandom random;
    private RegisterService registerService;
    private IntegerService integerService;
    private MultiValueRegisterService mvRegisterService;
    private SetService setService;
    private SetRWService rwSetService;
    private CounterService counterService;
    private FatCounterService fatCounterService;
    private InteractiveTransaction tx;

    public AntidoteService() {

        List<TransformerFactory> transformers = new ArrayList();
        transformers.add(messageCounter = new CountingTransformer());
        AntidoteJupyterConfigManager antidoteJupyterConfigManager = new AntidoteJupyterConfigManager();
        this.antidoteClient = new AntidoteClient(transformers, antidoteJupyterConfigManager.getConfigHosts());
        this.random = new SecureRandom();
        this.bucketKey = nextSessionId();
        this.bucket = Bucket.bucket(bucketKey);

    }

    /**
     * From the Antidote Java Client AntidoteTest.
     */
    public void doStaticTransaction(){
        CounterKey lowCounter = Key.counter("testCounter");
        IntegerKey lowInt = Key.integer("testInteger");
        SetKey<String> orSetKey = Key.set("testorSetRef", ValueCoder.utf8String);
        AntidoteStaticTransaction tx = antidoteClient.createStaticTransaction();
        bucket.update(tx, lowInt.increment(3));
        bucket.update(tx, lowCounter.increment(4));
        bucket.update(tx, orSetKey.add("Hi"));
        bucket.update(tx, orSetKey.add("Bye"));
        bucket.update(tx, orSetKey.add("yo"));
        tx.commitTransaction();
    }

    public int staticTransactionWithRead(int val1, int val2, int val3){
        CounterKey c1 = Key.counter("c1");
        CounterKey c2 = Key.counter("c2");
        CounterKey c3 = Key.counter("c3");

        AntidoteStaticTransaction stx = antidoteClient.createStaticTransaction();
        bucket.update(stx, c1.increment(val1));
        bucket.update(stx, c2.increment(val2));
        bucket.update(stx, c3.increment(val3));
        stx.commitTransaction();

        BatchRead batchRead = antidoteClient.newBatchRead();
        BatchReadResult<Integer> c1val = bucket.read(batchRead, c1);
        BatchReadResult<Integer> c2val = bucket.read(batchRead, c2);
        BatchReadResult<Integer> c3val = bucket.read(batchRead, c3);
        batchRead.commit(antidoteClient.noTransaction());

        int sum = c1val.get() + c2val.get() + c3val.get();
        return sum;
    }

    public String createAWMap(String mapId){
        MapKey mapKey = Key.map_aw(mapId);
        return mapKey.toString();
    }

    public String registerInMap(String mapKeyId, String registerKeyId, String registerValue){
        MapKey mapKey = Key.map_aw(mapKeyId);
        RegisterKey<String> registerKey = Key.register(registerKeyId);
        bucket.update(antidoteClient.noTransaction(), mapKey.update(registerKey.assign(registerValue)));
        return mapKey.toString();
    }

    public String readRegisterInMap(String mapKeyId, String registerKeyId){
        MapKey mapKey = Key.map_aw(mapKeyId);
        RegisterKey<String> registerKey = Key.register(registerKeyId);
        MapKey.MapReadResult mapReadResult = bucket.read(antidoteClient.noTransaction(), mapKey);
        return mapReadResult.get(registerKey);
    }

    public String createMultiValueRegister(String registerKeyId, String registerValue){
        MVRegisterKey<String> mvRegisterKey = Key.multiValueRegister(registerKeyId);
        bucket.update(antidoteClient.noTransaction(), mvRegisterKey.assign(registerValue));
        return registerKeyId;
    }

    public InteractiveTransaction startTransaction(){
        return antidoteClient.startTransaction();
    }

    public void addToTransaction(InteractiveTransaction tx, UpdateOp updateOp){
        bucket.update(tx, updateOp);
    }

    public void commitTransaction(InteractiveTransaction tx){
            tx.commitTransaction();
    }

    public void applyUpdate(UpdateOp update){
        bucket.update(antidoteClient.noTransaction(), update);
    }

    public String nextSessionId() {
        return new BigInteger(130, random).toString(32);
    }

    public Bucket getBucket() {
        return bucket;
    }

    public AntidoteClient getAntidoteClient() {
        return antidoteClient;
    }

    public RegisterService getRegisterService(){
        if(registerService == null){
            registerService = new RegisterService(this);
        }
        return registerService;
    }

    public IntegerService getIntegerService() {
         if(integerService == null) {
             integerService = new IntegerService(this);
         }
         return integerService;
    }

    public CounterService getCounterService() {
        if(counterService == null) {
            counterService = new CounterService(this);
        }
        return counterService;
    }

    public FatCounterService getFatCounterService() {
        if(fatCounterService == null) {
            fatCounterService = new FatCounterService(this);
        }
        return fatCounterService;
    }

    public MultiValueRegisterService getMvRegisterService() {
        if(mvRegisterService == null){
            mvRegisterService = new MultiValueRegisterService(this);
        }
        return mvRegisterService;
    }

    public SetService getSetService() {
        if(setService == null){
            setService = new SetService(this);
        }
        return setService;
    }

    public SetRWService getRwSetService() {
        if(rwSetService == null){
            rwSetService = new SetRWService(this);
        }
        return rwSetService;
    }
}
