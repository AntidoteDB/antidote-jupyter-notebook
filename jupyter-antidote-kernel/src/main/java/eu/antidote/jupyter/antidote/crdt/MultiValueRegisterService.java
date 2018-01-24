package eu.antidote.jupyter.antidote.crdt;

import eu.antidote.jupyter.antidote.AntidoteService;
import eu.antidotedb.client.Key;
import eu.antidotedb.client.MVRegisterKey;

public class MultiValueRegisterService {

    AntidoteService antidoteService;

    public MultiValueRegisterService(AntidoteService service){
        antidoteService = service;
    }

    
}
