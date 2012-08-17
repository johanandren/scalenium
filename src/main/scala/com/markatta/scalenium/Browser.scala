package com.markatta.scalenium

import org.openqa.selenium._

class Browser(protected val driver: WebDriver)
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

  def searchContext = driver

  /** close the current window, quit of no windows left */
  def close() {
    driver.close()
  }

  /** shut down the selenium browser */
  def quit() {
    driver.quit()
  }

}

object Browser {

}