/*
 * Copyright 2015 Johan Andrén
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
 */

package com.markatta.scalenium


/* Allows for customization of how failures are handled to adapt
 * the library to a specific testing framework.
 */

final class MissingElementException(message: String) extends RuntimeException(message)
final class AwaitFailedException(msg: String) extends RuntimeException(msg)

/** default simple failure handler, will throw an [[com.markatta.scalenium.AwaitFailedException]],
  * see [[com.markatta.scalenium.Specs2Integration]] for an example of
  * testing framework integration */
object SimpleFailureHandler extends MissingElementFailureHandler with TimeoutFailureHandler {

  def fail(timeout: Timeout, msg: String): Unit =  {
    throw new AwaitFailedException("Waited " + Time.humanText(timeout.inner) + " but " + msg)
  }

  /** fail with an explanation */
  def noSuchElement(msg: String): Unit =  {
    throw new MissingElementException(msg)
  }
}


object MissingElementFailureHandler {
  implicit val defaultMissingElementHandler = SimpleFailureHandler
}

trait MissingElementFailureHandler {
  /**
   * An element was asked for but not found inside the page
   *
   * @param elementSelector The css selector that was missing
   * @return
   */
  def noSuchElement(elementSelector: String): Unit
}

object TimeoutFailureHandler {
  implicit val defaultTimeoutFailureHandler = SimpleFailureHandler
}

trait TimeoutFailureHandler {
  /**
   * Some kind of async query timed out, an element was not shown onscreen etc.
   * @param timeout
   * @param message
   * @return
   */
  def fail(timeout: Timeout, message: String): Unit
}
