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

trait Forms { this: MarkupSearch =>

  final class FieldWriter private[Forms](text: String) {
    def into(cssSelector: String)(implicit failureHandler: MissingElementFailureHandler): Unit = {
      first(cssSelector).map(_.write(text))
    }

    def intoAll(cssSelector: String)(implicit failureHandler: MissingElementFailureHandler): Unit = {
      find(cssSelector).foreach(_.write(text))
    }
  }

  final def write(text: String): FieldWriter = new FieldWriter(text)

  /** Fill each field by name with the corresponding value
    * @param vals name -> value
    */
  def fillByName(vals: (String, String)*)(implicit failureHandler: MissingElementFailureHandler): Unit = {
    fill(vals, "name['" + _ + "']")(failureHandler)
  }

  /** Fill each field by id with the corresponding value
   * @param vals id -> value
   */
  def fillById(vals: (String, String)*)(implicit failureHandler: MissingElementFailureHandler): Unit = {
    fill(vals, "#" + _)(failureHandler)
  }

  /** Fill each field by css selector with the corresponding value
    * @param vals cssSelector -> value
    */
  def fill(vals: (String, String)*)(implicit failureHandler: MissingElementFailureHandler): Unit = {
    fill(vals, key => key)(failureHandler)
  }

  private def fill(vals: Seq[(String, String)], f: String => String)(failureHandler: MissingElementFailureHandler): Unit = {
    vals.foreach { case (key, value) => find(f(key)).write(value)(failureHandler)}
  }
}
