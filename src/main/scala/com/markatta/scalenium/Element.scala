package com.markatta.scalenium

import org.openqa.selenium.{Dimension, WebElement, WebDriver}
import org.openqa.selenium.interactions.Actions

class MissingElementException(message: String, cause: Throwable) extends RuntimeException(message, cause)

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

  def write(text: String): Either[MissingElementException, Element] = {
    operationAsEither {
      clear()
      webElement.sendKeys(text)
    }
  }

  def clear(): Either[MissingElementException, Element] = {
    operationAsEither {
      webElement.clear()
    }
  }

  def click(): Either[MissingElementException, Element] = {
    operationAsEither {
      webElement.click()
    }
  }

  def doubleClick(): Either[MissingElementException, Element] = {
    operationAsEither {
      val action = new Actions(driver).doubleClick(webElement).build()
      action.perform()
    }
  }

  /** submit the form that the element belongs to, if the element is a form element */
  def submit(): Either[MissingElementException, Element] = {
    operationAsEither {
      webElement.click()
    }
  }

  private def operationAsEither(b: => Any): Either[MissingElementException, Element] = {
    try {
      b
      Right(this)
    } catch {
      case e: NoSuchElementException => Left(new MissingElementException("Element matching expression '" + cssSelector + "' not found", e))
    }
  }

}