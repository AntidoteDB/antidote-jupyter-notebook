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

    static AntidoteService antidote
    void init() {
        antidote = new AntidoteService()
    }

    String showString(String name){
        return  "Hello thr "+ name
    }

    int numberMethod(int x, int y){
        return x + y
    }
    //Test antidote client call
    int doAntidoteTx(int x, int y, int z){
        return antidote.staticTransactionWithRead(x, y, z)
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

    String createBucket(String bucketName){
        Bucket bucket = Bucket.bucket(bucketName)

    }

    AntidoteTransaction startTransaction(){
        return antidote.startTransaction()
    }

    void addToTransaction(InteractiveTransaction tx, UpdateOp updateOp){
        antidote.addToTransaction(tx, updateOp)
    }

    void commitTransaction(InteractiveTransaction tx){
        antidote.commitTransaction(tx)
    }

    void applyUpdate(UpdateOp updateOperation){
        antidote.applyUpdate(updateOperation)
    }

    Object read(Key key) {
        return antidote.readByKey(key);
    }

    //-----------------LWREGISTER METHODS----------------------------//

    RegisterKey<String> getLWRegisterKey(String keyid){
        return antidote.getRegisterService().getKey(keyid)
    }

    UpdateOp assignLWRegister(RegisterKey<String> registerKey, String value){
        return antidote.getRegisterService().assignRegister(registerKey, value)
    }

    //-------------------MVREGISTER----------------------------------//
    MVRegisterKey<String> getMVRegisterKey(String keyId){
        return antidote.getMvRegisterService().getKey(keyId)
    }

    UpdateOp assignMVRegister(MVRegisterKey<String> registerKey, String value){
        return antidote.getMvRegisterService().assignRegister(registerKey, value)
    }

    //-----------------SET------------------------------------------//
    SetKey<String> getSetKey(String keyId){
        return antidote.getSetService().getKey(keyId)
    }

    UpdateOp addToSet(SetKey<String> setKey, String... values){
       return antidote.getSetService().addToSet(setKey, values)
    }

    UpdateOp removeFromSet(SetKey<String> setKey, String... values){
        return antidote.getSetService().removeFromSet(setKey, values)
    }

    //-----------------RWSET------------------------------------------//
    SetKey<String> getRWSetKey(String keyId){
        return antidote.getRwSetService().getKey(keyId)
    }

    UpdateOp addToRWSet(SetKey<String> setKey, String... values){
        antidote.getRwSetService().addToRWSet(setKey, values)
    }

    UpdateOp removeFromRWSet(SetKey<String> setKey, String... values){
        antidote.getRwSetService().removeFromRWSet(setKey, values)
    }

    String createAWMap(String mapId){
        return antidote.createAWMap(mapId)
    }

    String storeRegisterInMap(String mapKeyId, String registerKeyId, String registerValue){
        return antidote.registerInMap(mapKeyId, registerKeyId, registerValue)
    }

    String readRegisterInMap(String mapId, String registerKeyId){
        return antidote.readRegisterInMap(mapId, registerKeyId)
    }

    //IntegerKey
    UpdateOp assignInteger(String integerId, int value){
        return antidote.getIntegerService().assignInteger(integerId, value);
    }

    UpdateOp incrementInteger(String integerId, int incrementValue){
        return antidote.getIntegerService().incrementInteger(integerId, incrementValue);
    }

    Key getIntegerKey(String integerKey){
        return antidote.getIntegerService().getKey(integerKey);
    }

    //CounterKey
    UpdateOp incrementCounter(String counterKey, int incrementValue) {
        return antidote.getCounterService().incrementCounter(counterKey, incrementValue);
    }

    String readCounter(String counterKey){
        return antidote.getCounterService().readCounter(counterKey);
    }

    //FatCounterKey
    UpdateOp incrementFatCounter(String fatCounterKey, int incrementValue) {
        return antidote.getFatCounterService().incrementFatCounter(fatCounterKey, incrementValue);
    }

    UpdateOp resetFatCounter(String fatCounterKey) {
        return antidote.getFatCounterService().resetFatCounter(fatCounterKey);
    }

    String readFatCounter(String fatCounterKey) {
        return antidote.getFatCounterService().readFatCounter(fatCounterKey);
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
        return antidote.getMapAWService().readMapAW(mapKey);
    }

    /*void updateMapAW(String mapKey, String elementKey) {
        return antidote
    }*/
}
