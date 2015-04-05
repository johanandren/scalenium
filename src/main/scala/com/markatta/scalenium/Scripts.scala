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

import org.openqa.selenium.JavascriptExecutor

trait Scripts { this: HasDriver =>
  def executeScript(script: String, args: String*): this.type = {
    driver match {
      case j: JavascriptExecutor => j.executeScript(script, args: _*)
      case x => throw new IllegalStateException("Driver " + x + " is not a javascript executor")
    }
    this
  }

  def executeAsyncScript(script: String, args: String*): this.type = {
    driver match {
      case j: JavascriptExecutor => j.executeAsyncScript(script, args: _*)
      case x => throw new IllegalStateException("Driver " + x + " is not a javascript executor")
    }
    this
  }
}
