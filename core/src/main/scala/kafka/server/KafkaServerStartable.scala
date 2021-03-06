/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package kafka.server

import kafka.utils.Logging


class KafkaServerStartable(val serverConfig: KafkaConfig) extends Logging {
  private var server : KafkaServer = null

  init

  private def init() {
    server = new KafkaServer(serverConfig)
  }

  def startup() {
    try {
      server.startup()
    }
    catch {
      case e =>
        fatal("Fatal error during KafkaServerStable startup. Prepare to shutdown", e)
        shutdown()
        System.exit(1)
    }
  }

  def shutdown() {
    try {
      server.shutdown()
    }
    catch {
      case e =>
        fatal("Fatal error during KafkaServerStable shutdown. Prepare to halt", e)
        System.exit(1)
    }
  }

  def awaitShutdown() = 
    server.awaitShutdown

}


