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
 * TODO adapt to Antidote
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
    String init() {
        antidote1 = new AntidoteService(1)
        currentAntidote = antidote1
        return "Antidote session created. Connected to Antidote node 1."
    }

    String switchAntidote(int node){
        String session=""
        String connected
        if(node == 1){
            currentAntidote = antidote1
        }else if(node == 2){
            if(antidote2 == null){
                antidote2 = new AntidoteService(2)
                session = "Antidote 2 session created."
            }
            currentAntidote = antidote2
        }
        return session + "Connected to Antidote " + currentAntidote.nodeId
    }

    /**
     * Execute the docker command to emulate network connection between antidote notes
     * executed in Antidote1
     */
    String connectAntidotes(){
        Runtime.getRuntime().exec("docker exec antidote1 tc qdisc replace dev eth0 root netem loss 0%")
        return "Connecting Antidote nodes."
    }

    /**
     * Execute the docker command to emulate network disconnection between antidote notes
     * executed in Antidote1
     */
    String disconnectAntidotes(){
        Runtime.getRuntime().exec("docker exec antidote1 tc qdisc replace dev eth0 root netem loss 100%")
        return "Disconnecting Antidote nodes."
    }

    AntidoteTransaction startTransaction(){
        return currentAntidote.startTransaction()
    }

    String addToTransaction(InteractiveTransaction tx, UpdateOp updateOp){
        currentAntidote.addToTransaction(tx, updateOp)
        return  "Update to key '" + updateOp.getKey().key.toString() + "' added to transaction."
    }

    String commitTransaction(InteractiveTransaction tx){
        currentAntidote.commitTransaction(tx)
        return "Transaction committed on Antidote " + currentAntidote.nodeId
    }

    String applyUpdate(UpdateOp updateOperation){
        currentAntidote.applyUpdate(updateOperation)
        return "Update to key '"+ updateOperation.getKey().key.toString() + "' applied."
    }

    Object read(Key key) {
        return currentAntidote.readByKey(key)
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

    String createAWMap(String mapId){
        return currentAntidote.createAWMap(mapId)
    }

    String storeRegisterInMap(String mapKeyId, String registerKeyId, String registerValue){
        return currentAntidote.registerInMap(mapKeyId, registerKeyId, registerValue)
    }

    String readRegisterInMap(String mapId, String registerKeyId){
        return currentAntidote.readRegisterInMap(mapId, registerKeyId)
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


    //Map_aw key
    String readMapAW(String mapKey) {
        return currentAntidote.getMapAWService().readMapAW(mapKey);
    }

    /*void updateMapAW(String mapKey, String elementKey) {
        return antidote
    }*/
}
