import eu.antidote.jupyter.antidote.AntidoteJupyterConfigManager;
import eu.antidote.jupyter.antidote.AntidoteService;
import eu.antidotedb.client.AntidoteClient;
import eu.antidotedb.client.AntidoteConfigManager;
import eu.antidotedb.client.Bucket;
import eu.antidotedb.client.transformer.CountingTransformer;
import eu.antidotedb.client.transformer.LogTransformer;
import eu.antidotedb.client.transformer.TransformerFactory;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * hint: before running the test start Antidote. For example with docker:
 * docker run --rm -p "8087:8087" antidotedb/antidote
 */
public class AbstractAntidoteTest {

    AntidoteService antidoteService;

    public AbstractAntidoteTest() {

        antidoteService = new AntidoteService(1);
    }


}
