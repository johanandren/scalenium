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

import org.openqa.selenium._

object Browser {
  def apply(driver: WebDriver): Browser = new Browser(driver)
}

/**
 * Represents the browser, use it to do stuff you do with your browser, but automated.
 * @param driver Selenium WebDriver to use as backend
 */
final class Browser(protected val driver: WebDriver)
  extends HasDriver
  with HasSearchContext
  with PageProperties
  with Cookies
  with ScreenShots
  with Navigation
  with Scripts
  with MarkupSearch
  with Await
  with Forms {

  override val searchContext = driver

  /** close the current window, quit of no windows left */
  def close(): Unit =  {
    driver.close()
  }

  /** shut down the selenium browser */
  def quit(): Unit =  {
    driver.quit()
  }

}
