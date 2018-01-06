package eu.antidote.jupyter.antidote;

import eu.antidotedb.client.*;
import eu.antidotedb.client.transformer.CountingTransformer;
import eu.antidotedb.client.transformer.TransformerFactory;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class AntidoteService {

    final AntidoteClient antidoteClient;
    final CountingTransformer messageCounter;
    final Bucket bucket;
    final String bucketKey;
    final SecureRandom random;

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

    public String nextSessionId() {
        return new BigInteger(130, random).toString(32);
    }
}
