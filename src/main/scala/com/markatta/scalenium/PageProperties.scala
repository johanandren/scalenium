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

trait PageProperties { this: HasDriver =>

  /** @return the title of the current page */
  def pageTitle: String = driver.getTitle

  def currentUrl: String = driver.getCurrentUrl

  def pageSource: String = driver.getPageSource

  def focusedElement: Element = new Element("", driver, driver.switchTo.activeElement())

}
