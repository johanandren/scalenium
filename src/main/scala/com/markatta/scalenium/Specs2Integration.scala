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

import org.specs2.execute.{Failure, FailureException}

/** To avoid bringin in hard dependencies on specs2 across the entire library
  * all integration should be kept in this module (and possibly broken out into
  * a separate library)
  */
object Specs2Integration {

  /**
   * Throw failures as FailureExceptions that specs2 will treat as failures instead
   * of errors.
   */
  implicit object specs2FailureHandler extends MissingElementFailureHandler with TimeoutFailureHandler {

    def noSuchElement(msg: String): Unit = {
      throw new FailureException(Failure(msg))
    }

    def fail(timeout: Timeout, msg: String): Unit = {
      throw new FailureException(Failure("Waited " + Time.humanText(timeout.inner) + " but " + msg))
    }

  }
}
