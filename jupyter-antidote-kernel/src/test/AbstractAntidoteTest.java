import eu.antidote.jupyter.antidote.AntidoteService;

/**
 * hint: before running the test start Antidote. For example with docker:
 * docker run --rm -p "8087:8087" antidotedb/antidote
 */
public class AbstractAntidoteTest {

    AntidoteService antidoteService;

    public AbstractAntidoteTest() {

        antidoteService = new AntidoteService(3);
    }


}
