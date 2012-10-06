package com.markatta.scalenium

import org.openqa.selenium.{Dimension, WebElement, WebDriver}
import org.openqa.selenium.interactions.Actions



class Element(
     cssSelector: String,
     protected val driver: WebDriver,
     val webElement: WebElement)
  extends HasDriver
  with HasSearchContext
  with MarkupSearch
  with Await {

  val searchContext = webElement

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

  def write(text: String)(implicit failureHandler: MissingElementFailureHandler): Element = {
    operationAsEither {
      clear()
      webElement.sendKeys(text)
    }(failureHandler)
  }

  def clear()(implicit failureHandler: MissingElementFailureHandler): Element = {
    operationAsEither {
      webElement.clear()
    }(failureHandler)
  }

  def click()(implicit failureHandler: MissingElementFailureHandler): Element = {
    operationAsEither {
      webElement.click()
    }(failureHandler)
  }

  def doubleClick()(implicit failureHandler: MissingElementFailureHandler): Element = {
    operationAsEither {
      val action = new Actions(driver).doubleClick(webElement).build()
      action.perform()
    }(failureHandler)
  }

  /** submit the form that the element belongs to, if the element is a form element */
  def submit()(implicit failureHandler: MissingElementFailureHandler): Element = {
    operationAsEither {
      webElement.click()
    }(failureHandler)
  }

  private def operationAsEither(b: => Any)(failureHandler: MissingElementFailureHandler): Element = {
    try {
      b
      this
    } catch {
      case e: NoSuchElementException => {
        failureHandler.noSuchElement(cssSelector)
        // we will never get here because all failure handlers will throw exceptions!
        this
      }
    }
  }

}