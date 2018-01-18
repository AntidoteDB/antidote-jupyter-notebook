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

import com.github.jmchilton.blend4j.galaxy.GalaxyInstance
import com.github.jmchilton.blend4j.galaxy.HistoriesClient
import com.github.jmchilton.blend4j.galaxy.ToolsClient
import com.github.jmchilton.blend4j.galaxy.beans.History
import org.lappsgrid.serialization.Data
import org.lappsgrid.serialization.Serializer
import eu.antidote.*

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

    GalaxyClient galaxy //= new GalaxyClient(AntidoteKernel.ANTIDOTE_HOST, AntidoteKernel.GALAXY_KEY)
    AntidoteService antidote
    void init() {
        if (galaxy == null) {
            galaxy = new GalaxyClient(AntidoteKernel.ANTIDOTE_HOST, AntidoteKernel.GALAXY_KEY)
        }
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
        antidote = new AntidoteService()
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

    File get(Integer hid) {
//        if (galaxy == null) {
//            println "Not connected to a Galaxy instance."
//            return null
//        }
        init()
        println "Getting history item $hid"
        File file = galaxy.get(hid)
        if (file == null) {
            println "Galaxy client returned a null object."
        }
        else if (!file.exists()) {
            println "File not found: ${file.path}"
        }
        return file
    }

    void put(String path) {
        put(new File(path))
    }

    void put(File file) {
        println "Adding ${file.path} to the current history."
        init()
        galaxy.put(file)
    }

    Object parse(String json) {
        return parse(json, Data)
    }

    Object parse(String json, Class theClass) {
        return Serializer.parse(json, theClass)
    }

    String toJson(Object o) {
        return Serializer.toJson(o)
    }

    String toPrettyJson(Object o) {
        return Serializer.toPrettyJson(o)
    }

    String selectHistory(String name) {
        init()
        if (!galaxy.selectHistory(name)) {
            return "No history named '$name' was found."
        }
        return galaxy.history.id
    }

    String version() {
        String groovy = eu.antidote.jupyter.groovy.Version.getVersion()
        String antidote =  Version.getVersion()
        sprintf("Kernel Versions\nGroovy : %s\nANTIDOTE    : %s", groovy, antidote)
    }
    
    GalaxyInstance galaxy() { init(); return galaxy.galaxy }
    HistoriesClient histories() { init(); return galaxy.histories }
    ToolsClient tools() { init(); return galaxy.tools }
    History history() { init(); return galaxy.history }

    void exit() {
        System.exit(0)
    }
}
