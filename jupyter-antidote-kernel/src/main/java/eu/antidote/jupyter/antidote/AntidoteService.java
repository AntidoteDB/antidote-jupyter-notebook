package eu.antidote.jupyter.antidote;

import eu.antidote.jupyter.antidote.crdt.*;
import eu.antidotedb.client.*;
import eu.antidotedb.client.transformer.CountingTransformer;
import eu.antidotedb.client.transformer.TransformerFactory;

import java.util.ArrayList;
import java.util.List;

public class AntidoteService {

    private AntidoteClient antidoteClient;
    final int nodeId;
    private Bucket bucket;
    private RegisterService registerService;
    private IntegerService integerService;
    private MultiValueRegisterService mvRegisterService;
    private SetService setService;
    private RWSetService rwSetService;
    private CounterService counterService;
    private FatCounterService fatCounterService;
    private AWMapService AWMapService;
    private RRMapService RRMapService;
    private GMapService GMapService;

    public AntidoteService(int node, String buckerKey) {
        nodeId = node;
        List<TransformerFactory> transformers = new ArrayList();
        transformers.add(new CountingTransformer());
        AntidoteJupyterConfigManager antidoteJupyterConfigManager = new AntidoteJupyterConfigManager();
        if(node == 1) {
            this.antidoteClient = new AntidoteClient(transformers, antidoteJupyterConfigManager.getAntidote1ConfigHosts());
        }else if(node == 2){
            this.antidoteClient = new AntidoteClient(transformers, antidoteJupyterConfigManager.getAntidote2ConfigHosts());
        }else {
            this.antidoteClient = new AntidoteClient(transformers, antidoteJupyterConfigManager.getAntidoteLocalConfigHosts());
        }
        this.bucket = Bucket.bucket(buckerKey);

    }

    public InteractiveTransaction startTransaction(){
        return antidoteClient.startTransaction();
    }

    public void applyUpdate(InteractiveTransaction tx, UpdateOp updateOp){
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

    public Object readInTransaction(InteractiveTransaction tx, Key key) {
        return bucket.read(tx, key);
    }

    public Object readKeyInMap(Key mapKey, Key elementKey) {
        MapKey.MapReadResult mapReadResult = (MapKey.MapReadResult) readByKey(mapKey);
        return mapReadResult.get(elementKey);
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

    public RWSetService getRwSetService() {
        if(rwSetService == null){
            rwSetService = new RWSetService();
        }
        return rwSetService;
    }

    public AWMapService getAWMapService() {
        if(AWMapService == null){
            AWMapService = new AWMapService();
        }
        return AWMapService;
    }

    public RRMapService getRRMapService() {
        if(RRMapService == null){
            RRMapService = new RRMapService();
        }
        return RRMapService;
    }

    public GMapService getGMapService() {
        if(GMapService == null){
            GMapService = new GMapService();
        }
        return GMapService;
    }
}
