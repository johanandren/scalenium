package com.markatta.scalenium

trait PageProperties { this: HasDriver =>

  /** @return the title of the current page */
  def pageTitle: String = driver.getTitle

  def currentUrl: String = driver.getCurrentUrl

  def pageSource: String = driver.getPageSource

  def focusedElement: Element = new Element("", driver, driver.switchTo.activeElement())

}
