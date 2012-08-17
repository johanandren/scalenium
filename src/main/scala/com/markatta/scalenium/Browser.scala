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
  with Await {

  def searchContext = driver

}

object Browser {

}