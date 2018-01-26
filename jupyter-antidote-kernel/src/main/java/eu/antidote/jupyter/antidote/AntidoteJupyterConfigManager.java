package eu.antidote.jupyter.antidote;

import java.net.InetSocketAddress;
import java.util.LinkedList;
import java.util.List;

public class AntidoteJupyterConfigManager {

    public List<InetSocketAddress> getAntidote1ConfigHosts() {
        List<InetSocketAddress> list = new LinkedList();
        list.add(new InetSocketAddress(AntidoteKernel.ANTIDOTE_IP, AntidoteKernel.ANTIDOTE1_PORT));
        return list;
    }

    public List<InetSocketAddress> getAntidote2ConfigHosts() {
        List<InetSocketAddress> list = new LinkedList();
        list.add(new InetSocketAddress(AntidoteKernel.ANTIDOTE_IP, AntidoteKernel.ANTIDOTE2_PORT));
        return list;
    }
}
