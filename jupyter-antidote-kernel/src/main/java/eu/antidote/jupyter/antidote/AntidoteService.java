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

    private AntidoteClient antidoteClient;
    final private Bucket bucket;
    final String bucketKey;
    SecureRandom random;
    private RegisterService registerService;
    private IntegerService integerService;
    private MultiValueRegisterService mvRegisterService;
    private SetService setService;
    private SetRWService rwSetService;
    private CounterService counterService;
    private FatCounterService fatCounterService;
    private MapAWService mapAWService;

    public AntidoteService(int node) {

        List<TransformerFactory> transformers = new ArrayList();
        transformers.add(new CountingTransformer());
        AntidoteJupyterConfigManager antidoteJupyterConfigManager = new AntidoteJupyterConfigManager();
        if(node == 1) {
            this.antidoteClient = new AntidoteClient(transformers, antidoteJupyterConfigManager.getAntidote1ConfigHosts());
        }else if(node == 2){
            this.antidoteClient = new AntidoteClient(transformers, antidoteJupyterConfigManager.getAntidote2ConfigHosts());
        }
        this.random = new SecureRandom();
        this.bucketKey = nextSessionId();
        this.bucket = Bucket.bucket(bucketKey);

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

    public Object readByKey(Key key) {
        return bucket.read(antidoteClient.noTransaction(), key);
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
            registerService = new RegisterService();
        }
        return registerService;
    }

    public IntegerService getIntegerService() {
         if(integerService == null) {
             integerService = new IntegerService();
         }
         return integerService;
    }

    public CounterService getCounterService() {
        if(counterService == null) {
            counterService = new CounterService();
        }
        return counterService;
    }

    public FatCounterService getFatCounterService() {
        if(fatCounterService == null) {
            fatCounterService = new FatCounterService();
        }
        return fatCounterService;
    }

    public MultiValueRegisterService getMvRegisterService() {
        if(mvRegisterService == null){
            mvRegisterService = new MultiValueRegisterService();
        }
        return mvRegisterService;
    }

    public SetService getSetService() {
        if(setService == null){
            setService = new SetService();
        }
        return setService;
    }

    public SetRWService getRwSetService() {
        if(rwSetService == null){
            rwSetService = new SetRWService();
        }
        return rwSetService;
    }

    public MapAWService getMapAWService() {
        if(mapAWService == null){
            mapAWService = new MapAWService();
        }
        return mapAWService;
    }
}
