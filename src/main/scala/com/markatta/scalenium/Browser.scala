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
