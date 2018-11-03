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

import groovy.util.logging.Slf4j
import org.lappsgrid.jupyter.groovy.GroovyKernel

/** A Jupyter kernel for Antidote DB
 * Based on
 * @author Keith Suderman
 */
@Slf4j('logger')
class AntidoteKernel extends GroovyKernel {
    public static String ANTIDOTE_IP = "127.0.0.1"  //"192.168.99.100"
    public static String ANTIDOTE1_IP = "antidote1"
    public static String ANTIDOTE2_IP = "antidote2"
    public static int ANTIDOTE_PORT = 8087

    public AntidoteKernel() {
        super(new AntidoteContext())
    }

    Map info() {
        return [
                protocol_version: '5.0',
                implementation: 'antidote',
                implementation_version: '0.1.0',
                language_info: [
                        name: 'Antidote',
                        version: '0.1.0',
                        mimetype: '',
                        file_extension: '.anb',
                        pygments_lexer: '',
                        codemirror_mode: '',
                        nbconverter_exporter: ''
                ],
                banner: 'Antidote Database',
                help_links: []
        ]
    }

    static void main(String[] args) {
        if (args.length < 1) {
            logger.error "No connection file passed to the Antidote kernel."
            System.exit(1)
        }
        File config = new File(args[0])
        if (!config.exists()) {
            logger.error "Kernel configuration not found."
            System.exit(1)
        }

        GroovyKernel kernel = new AntidoteKernel()
        kernel.connectionFile = config
        kernel.run()
    }
}
