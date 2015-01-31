package com.markatta.scalenium

import org.openqa.selenium.Keys
import org.openqa.selenium.interactions.Actions

trait ElementInteractions { this: Element =>


  // keyboard interactions

  /** clears the given text/input element and writes the given text in it */
  def write(text: String)(implicit failureHandler: MissingElementFailureHandler): Element = {
    operationAsEither {
      clear()
      webElement.sendKeys(text)
    }
  }

  /** clear the text content of the element */
  def clear()(implicit failureHandler: MissingElementFailureHandler): Element = {
    operationAsEither(webElement.clear())
  }

  /** trigger a key down for the given key on the element */
  def keyDown(key: Keys)(implicit failureHandler: MissingElementFailureHandler): Element =
    doAction(_.keyDown(webElement, key))

  /** trigger a key up for the given key on the element */
  def keyUp(key: Keys)(implicit failureHandler: MissingElementFailureHandler): Element =
    doAction(_.keyUp(webElement, key))



  // mouse interactions


  def click()(implicit failureHandler: MissingElementFailureHandler): Element =
    operationAsEither(webElement.click())

  def clickAndHold()(implicit failureHandler: MissingElementFailureHandler): Element =
    doAction(_.clickAndHold(webElement))

  def contextClick()(implicit failureHandler: MissingElementFailureHandler): Element =
    doAction(_.contextClick(webElement))

  def doubleClick()(implicit failureHandler: MissingElementFailureHandler): Element =
    doAction(_.doubleClick(webElement))

  def dragAndDropTo(other: Element)(implicit failureHandler: MissingElementFailureHandler): Element =
    doAction(_.dragAndDrop(webElement, other.webElement))

  /** move the mouse cursor to above the center of the element */
  def hover()(implicit failureHandler: MissingElementFailureHandler): Element =
    doAction(_.moveToElement(webElement))


  /** submit the form that the element belongs to, if the element is a form element */
  @deprecated("This only does .click(), just use that instead")
  def submit()(implicit failureHandler: MissingElementFailureHandler): Element = {
    operationAsEither {
      webElement.click()
    }(failureHandler)
  }


  private def doAction(builder: Actions => Actions)(implicit failureHandler: MissingElementFailureHandler): Element = {
    operationAsEither {
      builder(new Actions(driver)).perform()
    }
    this
  }

  private def operationAsEither(b: => Any)(implicit failureHandler: MissingElementFailureHandler): Element = {
    try {
      b
      this
    } catch {
      case e: NoSuchElementException =>
        failureHandler.noSuchElement(cssSelector)
        // we will never get here because all failure handlers will throw exceptions!
        this
    }
  }

}
