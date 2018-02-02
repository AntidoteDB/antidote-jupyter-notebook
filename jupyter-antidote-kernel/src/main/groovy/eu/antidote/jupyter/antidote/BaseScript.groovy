/*
 * Copyright (c) 2016 The Language Application Grid
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package eu.antidote.jupyter.antidote

import eu.antidotedb.client.*
/**
 * Based on work of
 * @author Keith Suderman
 */
abstract class BaseScript extends Script {

    static {
        Collection.metaClass.map    = { delegate.collect it }
        Collection.metaClass.reduce = { object,closure -> delegate.inject(object,closure) }
        Collection.metaClass.filter = { delegate.grep it }
    }

    static AntidoteService antidote1
    static AntidoteService antidote2
    static AntidoteService currentAntidote
    static String sessionId;

    String init() {
        sessionId = generateId();
        if(antidote1 == null){
            antidote1 = new AntidoteService(1, sessionId)
        }
        if(antidote2 == null){
            antidote2 = new AntidoteService(2, sessionId)
        }
        currentAntidote = antidote1
        return "Antidote session created. Connected to Antidote node 1."
    }

    String switchAntidote(int node){
        String session=""
        if(node == 1){
            if(antidote1 == null){
                antidote1 = new AntidoteService(1, sessionId)
                session = "Antidote 2 session created. "
            }
            currentAntidote = antidote1
        }else if(node == 2){
            if(antidote2 == null){
                antidote2 = new AntidoteService(2, sessionId)
                session = "Antidote 2 session created. "
            }
            currentAntidote = antidote2
        }
        return session + "Connected to Antidote " + node
    }

    /**
     * Execute the docker command to emulate network connection between antidote notes
     * executed in Antidote1
     */
    String connectAntidotes(){
        Runtime.getRuntime().exec("docker exec antidote2 tc qdisc replace dev eth0 root netem loss 0%")
        return "Connecting Antidote nodes."
    }

    /**
     * Execute the docker command to emulate network disconnection between antidote notes
     * executed in Antidote1
     */
    String disconnectAntidotes(){
        Runtime.getRuntime().exec("docker exec antidote2 tc qdisc replace dev eth0 root netem loss 100%")
        return "Disconnecting Antidote nodes."
    }

    AntidoteTransaction startTransaction(){
        return currentAntidote.startTransaction()
    }

    String addToTransaction(InteractiveTransaction tx, UpdateOp updateOp){
        currentAntidote.addToTransaction(tx, updateOp)
        return  "Added key '" + updateOp.getKey() + "' to transaction"
    }

    String commitTransaction(InteractiveTransaction tx){
        currentAntidote.commitTransaction(tx)
        return "Transaction committed on Antidote " + currentAntidote.nodeId
    }

    String applyUpdate(UpdateOp updateOperation){
        currentAntidote.applyUpdate(updateOperation)
        return "Updated key '"+ updateOperation.getKey() + "' on Antidote "+ currentAntidote.nodeId
    }

    Object read(Key key) {
        return currentAntidote.readByKey(key)
    }

    Object readFromMap(Key mapKey, Key elementKey) {
        return currentAntidote.readKeyInMap(mapKey, elementKey);
    }

    //-----------------LWREGISTER METHODS----------------------------//

    RegisterKey<String> getLWRegisterKey(String keyid){
        return currentAntidote.getRegisterService().getKey(keyid)
    }

    UpdateOp assignLWRegister(RegisterKey<String> registerKey, String value){
        return currentAntidote.getRegisterService().assignRegister(registerKey, value)
    }

    //-------------------MVREGISTER----------------------------------//
    MVRegisterKey<String> getMVRegisterKey(String keyId){
        return currentAntidote.getMvRegisterService().getKey(keyId)
    }

    UpdateOp assignMVRegister(MVRegisterKey<String> registerKey, String value){
        return currentAntidote.getMvRegisterService().assignRegister(registerKey, value)
    }

    UpdateOp resetMVRegister(MVRegisterKey<String> registerKey){
        return currentAntidote.getMvRegisterService().resetMVRegister(registerKey)
    }

    //-----------------SET------------------------------------------//
    SetKey<String> getSetKey(String keyId){
        return currentAntidote.getSetService().getKey(keyId)
    }

    UpdateOp addToSet(SetKey<String> setKey, String... values){
       return currentAntidote.getSetService().addToSet(setKey, values)
    }

    UpdateOp removeFromSet(SetKey<String> setKey, String... values){
        return currentAntidote.getSetService().removeFromSet(setKey, values)
    }

    UpdateOp resetSet(SetKey<String> setKey){
        return currentAntidote.getSetService().resetSet(setKey)
    }

    //-----------------RWSET------------------------------------------//
    SetKey<String> getRWSetKey(String keyId){
        return currentAntidote.getRwSetService().getKey(keyId)
    }

    UpdateOp addToRWSet(SetKey<String> setKey, String... values){
        currentAntidote.getRwSetService().addToRWSet(setKey, values)
    }

    UpdateOp removeFromRWSet(SetKey<String> setKey, String... values){
        currentAntidote.getRwSetService().removeFromRWSet(setKey, values)
    }

    //IntegerKey
    UpdateOp assignInteger(IntegerKey integerKey, int value){
        return currentAntidote.getIntegerService().assignInteger(integerKey, value);
    }

    UpdateOp incrementInteger(IntegerKey integerKey, int incrementValue){
        return currentAntidote.getIntegerService().incrementInteger(integerKey, incrementValue);
    }

    IntegerKey getIntegerKey(String integerKey){
        return currentAntidote.getIntegerService().getKey(integerKey);
    }

    //CounterKey
    UpdateOp incrementCounter(CounterKey counterKey, int incrementValue) {
        return currentAntidote.getCounterService().incrementCounter(counterKey, incrementValue);
    }

    CounterKey getCounterKey(String counterKey){
        return currentAntidote.getCounterService().getKey(counterKey);
    }

    //FatCounterKey
    UpdateOp incrementFatCounter(CounterKey fatCounterKey, int incrementValue) {
        return currentAntidote.getFatCounterService().incrementFatCounter(fatCounterKey, incrementValue);
    }

    UpdateOp resetFatCounter(CounterKey fatCounterKey) {
        return currentAntidote.getFatCounterService().resetFatCounter(fatCounterKey);
    }

    CounterKey getFatCounterKey(String fatCounterKey) {
        return currentAntidote.getFatCounterService().getKey(fatCounterKey);
    }

    //Map_AW Key
    UpdateOp updateAWMap(MapKey mapKey, UpdateOp... update) {
        return currentAntidote.getAWMapService().updateMap(mapKey, update);
    }

    UpdateOp removeFromAWMap(MapKey mapKey, Key... key) {
        return currentAntidote.getAWMapService().removeKey(mapKey, key);
    }

    MapKey getAWMapKey(String mapKey) {
        return currentAntidote.getAWMapService().getKey(mapKey);
    }

    //Map_RR Key
    UpdateOp updateRRMap(MapKey mapKey, UpdateOp... update) {
        return currentAntidote.getRRMapService().updateMap(mapKey, update);
    }

    UpdateOp removeFromRRMap(MapKey mapKey, Key... key) {
        return currentAntidote.getRRMapService().removeKey(mapKey, key);
    }

    UpdateOp resetRRMap(MapKey mapKey) {
        return currentAntidote.getRRMapService().reset(mapKey);
    }

    MapKey getRRMapKey(String mapKey) {
        return currentAntidote.getRRMapService().getKey(mapKey);
    }

    //Map_G Key
    UpdateOp updateGMap(MapKey mapKey, UpdateOp... update) {
        return currentAntidote.getGMapService().updateMap(mapKey, update);
    }

    MapKey getGMapKey(String mapKey) {
        return currentAntidote.getGMapService().getKey(mapKey);
    }

    String version() {
        String groovy = eu.antidote.jupyter.groovy.Version.getVersion()
        String antidote =  Version.getVersion()
        sprintf("Kernel Versions\nGroovy : %s\nANTIDOTE    : %s", groovy, antidote)
    }

    void exit() {
        System.exit(0)
    }

    String generateId() {
        String uniqueID = UUID.randomUUID().toString()
        return uniqueID
    }

}
