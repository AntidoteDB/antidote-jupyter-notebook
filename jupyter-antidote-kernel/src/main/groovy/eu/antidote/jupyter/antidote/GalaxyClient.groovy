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
import com.github.jmchilton.blend4j.galaxy.GalaxyInstanceFactory
import com.github.jmchilton.blend4j.galaxy.HistoriesClient
import com.github.jmchilton.blend4j.galaxy.ToolsClient
import com.github.jmchilton.blend4j.galaxy.beans.History
import com.github.jmchilton.blend4j.galaxy.beans.HistoryContents
import groovy.util.logging.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Based on
 * @author Keith Suderman
 * TODO Adapt to antidote
 */
@Slf4j('logger')
class GalaxyClient {
//    static final Logger logger = LoggerFactory.getLogger(GalaxyClient)

    GalaxyInstance galaxy
    HistoriesClient histories
    ToolsClient tools
    History history

    public GalaxyClient() {

    }

    public GalaxyClient(String url, String key) {
        connect(url, key)
    }

    void connect(String url, String key) {
        logger.info("Connecting to {}", url)
        galaxy = GalaxyInstanceFactory.get(url, key)
        if (galaxy) {
            logger.debug("Created a Galaxy client for {}", url)
            histories = galaxy.historiesClient
            tools = galaxy.toolsClient
            history = histories.histories.get(0)
        }
        else {
            logger.warn("Unable to get a Galaxy instance.")
        }
    }

    void put(String path) {
        put(new File(path))
    }

    void put(File file) {
        logger.info("Attempting to put file {} to history {}", file.path, history.id)
        ToolsClient.FileUploadRequest request = new ToolsClient.FileUploadRequest(history.id, file)
        tools.uploadRequest(request)
    }

    File get(Integer hid) {
        logger.info("Getting history item {}", hid)
        List<HistoryContents> contentsList = histories.showHistoryContents(history.id)
        logger.debug("History contains {} items", contentsList.size())
        HistoryContents contents = contentsList.find { it.hid == hid }
        if (contents) {
            URL url = new URL(galaxy.galaxyUrl + contents.url + "/display?hmac=${galaxy.apiKey}")
            logger.debug("GET {}", url)
            File file = File.createTempFile("groovy-kernel-", ".dat")
            file.text = url.text
            logger.info("Found file {}", file.path)
            return file
        }
        else {
            logger.info("No such history item: ${hid}")
            return null
        }
    }

    boolean selectHistory(String name) {
        logger.info("Selecting history {}", name)
        history = histories.histories.find { it.name == name }
        return history != null
    }
}
