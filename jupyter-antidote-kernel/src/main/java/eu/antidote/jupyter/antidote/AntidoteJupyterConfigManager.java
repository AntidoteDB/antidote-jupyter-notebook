package eu.antidote.jupyter.antidote;

import java.net.InetSocketAddress;
import java.util.LinkedList;
import java.util.List;

public class AntidoteJupyterConfigManager {
    //TODO the default host should be the IP address of the antidote container
    public static final String DEFAULT_HOST = "192.168.99.100";
    public static final int DEFAULT_PORT = 8087;

    public List<InetSocketAddress> getConfigHosts() {
        //String cfgPath = System.getProperty("user.dir") + "/" + DEFAULT_FILE;
        //return getConfigHosts(cfgPath);
        List<InetSocketAddress> list = new LinkedList();
        list.add(new InetSocketAddress(AntidoteKernel.ANTIDOTE_IP, AntidoteKernel.ANTIDOTE_PORT));
        return list;
    }
}
