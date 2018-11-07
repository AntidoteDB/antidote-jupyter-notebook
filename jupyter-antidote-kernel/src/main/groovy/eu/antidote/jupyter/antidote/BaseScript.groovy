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

import eu.antidote.jupyter.kanban.common.*
import eu.antidotedb.client.*


import java.text.DateFormat
import java.text.SimpleDateFormat

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
    static String sessionId

    String init() {
        sessionId = generateId();
        if(antidote1 == null){
            antidote1 = new AntidoteService(1, sessionId)
        }
        currentAntidote = antidote1
        return formattedTimestamp()+ "Setup created. Connected to Replica 1."
    }

    String onReplica(int node){
        String session=""
        if(node == 1){
            if(antidote1 == null){
                antidote1 = new AntidoteService(1, sessionId)
                session = " Replica 1 session created. "
            }
            currentAntidote = antidote1
        }else if(node == 2){
            if(antidote2 == null){
                antidote2 = new AntidoteService(2, sessionId)
                session = " Replica 2 session created. "
            }
            currentAntidote = antidote2
        }
        return formattedTimestamp()+ session + " Connected to Replica " + node +"."
    }

    /**
     * Execute the docker command to emulate network connection between antidote notes
     * executed in Antidote1
     */
    String connect(){
        Runtime.getRuntime().exec("docker exec antidote2 tc qdisc replace dev eth0 root netem loss 0%")
        return formattedTimestamp()+ " Connecting replicas."
    }

    /**
     * Execute the docker command to emulate network disconnection between antidote notes
     * executed in Antidote1
     */
    String disconnect(){
        Runtime.getRuntime().exec("docker exec antidote2 tc qdisc replace dev eth0 root netem loss 100%")
        return formattedTimestamp()+ " Disconnecting replicas."
    }

    AntidoteTransaction startTransaction(){
        return currentAntidote.startTransaction()
    }

    String applyUpdateWithTransaction(InteractiveTransaction tx, UpdateOp updateOp){
        currentAntidote.applyUpdate(tx, updateOp)
        return  formattedTimestamp()+ " updated key '" + updateOp.getKey() + "' with transaction on Antidote " + currentAntidote.nodeId
    }

    String commitTransaction(InteractiveTransaction tx){
        currentAntidote.commitTransaction(tx)
        return formattedTimestamp()+ " Transaction committed on Antidote " + currentAntidote.nodeId
    }

    String applyUpdate(UpdateOp updateOperation){
        currentAntidote.applyUpdate(updateOperation)
        return formattedTimestamp()+ " Updated key '"+ updateOperation.getKey() + "' on Antidote "+ currentAntidote.nodeId
    }

    Object read(Key key) {
        return currentAntidote.readByKey(key)
    }

    Object readInTransaction(InteractiveTransaction tx, Key key) {
        return currentAntidote.readInTransaction(tx, key)
    }

    Object readFromMap(Key mapKey, Key elementKey) {
        return currentAntidote.readKeyInMap(mapKey, elementKey)
    }

    Object readFromMapResult(MapKey.MapReadResult mapResult, Key elementKey){
        return currentAntidote.readKeyInMapResult(mapResult, elementKey)
    }

    //-----------------LWWREGISTER METHODS----------------------------//

    RegisterKey<String> getLWWRegisterKey(String keyid){
        return currentAntidote.getRegisterService().getKey(keyid)
    }

    UpdateOp assignLWWRegister(RegisterKey<String> registerKey, String value){
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

    UpdateOp resetRWSet(SetKey<String> setKey){
        currentAntidote.getRwSetService().resetRWSet(setKey);
    }

    //CounterKey
    UpdateOp incrementCounter(CounterKey counterKey, int incrementValue) {
        return currentAntidote.getCounterService().incrementCounter(counterKey, incrementValue)
    }

    CounterKey getCounterKey(String counterKey){
        return currentAntidote.getCounterService().getKey(counterKey)
    }

    //Map_RR Key
    UpdateOp updateRRMap(MapKey mapKey, UpdateOp... update) {
        return currentAntidote.getRRMapService().updateMap(mapKey, update)
    }

    UpdateOp removeFromRRMap(MapKey mapKey, Key... key) {
        return currentAntidote.getRRMapService().removeKey(mapKey, key)
    }

    UpdateOp resetRRMap(MapKey mapKey) {
        return currentAntidote.getRRMapService().reset(mapKey)
    }

    MapKey getRRMapKey(String mapKey) {
        return currentAntidote.getRRMapService().getKey(mapKey)
    }

    //Map_G Key
    UpdateOp updateGMap(MapKey mapKey, UpdateOp... update) {
        return currentAntidote.getGMapService().updateMap(mapKey, update)
    }

    MapKey getGMapKey(String mapKey) {
        return currentAntidote.getGMapService().getKey(mapKey)
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

    private String formattedTimestamp(){
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss")
        Date date = new Date()
        return dateFormat.format(date)
    }

    //Kanban board commands

    String hello() {
        return currentAntidote.kanban.hello()
    }

    List<BoardId> listboards() {
        return currentAntidote.kanban.listboards()
    }

    BoardId createboard(String boardname) {
        return currentAntidote.kanban.createboard(boardname)
    }

    String renameboard(BoardId bid, String name) {
        currentAntidote.kanban.renameboard(bid, name)
        return "Renamed board"
    }

    BoardMap getboard(BoardId bid) {
        return currentAntidote.kanban.getboard(bid)
    }


    ColumnId addcolumn(BoardId bid, String columnname) {
        return currentAntidote.kanban.addcolumn(bid, columnname)
    }

    String deletecolumn(ColumnId cid) {
        currentAntidote.kanban.deletecolumn(cid)
        return "Deleted column"
    }

    TaskId createtask(String taskname, ColumnId cid) {
        return currentAntidote.kanban.createtask(taskname, cid)
    }

    String deletetask(TaskId tid) {
        currentAntidote.kanban.deletetask(tid)
        return "Deleted task"
    }

    String movetask(TaskId tid, ColumnId cid) {
        currentAntidote.kanban.movetask(tid, cid)
        return "Moved task"
    }

    // Kanban board with LWW Register for tasks

    BoardId createboard_x(String boardname) {
        return currentAntidote.kanban_lww.createboard(boardname)
    }

//    String renameboard_x(BoardId bid, String name) {
//        currentAntidote.kanban_lww.renameboard(bid, name)
//        return "Renamed board"
//    }

    BoardMap getboard_x(BoardId bid) {
        return currentAntidote.kanban_lww.getboard(bid)
    }


    ColumnId addcolumn_x(BoardId bid, String columnname) {
        return currentAntidote.kanban_lww.addcolumn(bid, columnname)
    }

    String deletecolumn_x(ColumnId cid) {
        currentAntidote.kanban_lww.deletecolumn(cid)
        return "Deleted column"
    }

    TaskId createtask_x(String taskname, ColumnId cid) {
        return currentAntidote.kanban_lww.createtask(taskname, cid)
    }

}

