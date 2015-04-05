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

import org.openqa.selenium.{Dimension, WebElement, WebDriver}
import org.openqa.selenium.interactions.Actions

final class Element private[scalenium](
     protected val cssSelector: String,
     protected val driver: WebDriver,
     val webElement: WebElement)
  extends HasDriver
  with ElementInteractions
  with HasSearchContext
  with MarkupSearch
  with Await {

  override val searchContext = webElement

  /** @return the value of the given attribute name */
  def apply(attributeName: String): String = webElement.getAttribute(attributeName)

  def tagName: String = webElement.getTagName
  def value: String = this("value")
  def id: String = this("id")
  def name: String = this("name")

  def text: String = webElement.getText

  def html: String = this("innerHTML")

  def enabled: Boolean = webElement.isEnabled
  def disabled: Boolean = !enabled

  /** @return is the element shown onscreen */
  def visible: Boolean = webElement.isDisplayed
  def hidden: Boolean = !visible

  /** is a checkbox or radio button element selected */
  def selected: Boolean = webElement.isSelected


  /** @return onscreen dimensions of the element */
  def size: Dimension = webElement.getSize



}
