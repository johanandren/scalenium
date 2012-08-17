package com.markatta.scalenium

import collection.JavaConversions._
import org.openqa.selenium._
import org.openqa.selenium.interactions.Actions


trait MarkupSearch { this: HasDriver with HasSearchContext =>

  /** @return all matching elements or empty sequence if not found or any other type of error */
  def find(cssSelector: String): Seq[Element] =
    try {
      searchContext.findElements(By.cssSelector(cssSelector)).map(e => new Element(cssSelector, driver, e))
    } catch {
      case e: RuntimeException => Seq()
    }

  /** alias for find */
  def select(cssSelector: String) = find(cssSelector)

  def first(cssSelector: String): Option[Element] = find(cssSelector).headOption

}


