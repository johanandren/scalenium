package com.markatta.scalenium

import org.openqa.selenium.JavascriptExecutor

trait Scripts { this: HasDriver =>
  def executeScript(script: String, args: String*): this.type = {
    driver match {
      case j: JavascriptExecutor => j.executeScript(script, args: _*)
      case x => throw new IllegalStateException("Driver " + x + " is not a javascript executor")
    }
    this
  }

  def executeAsyncScript(script: String, args: String*): this.type = {
    driver match {
      case j: JavascriptExecutor => j.executeAsyncScript(script, args: _*)
      case x => throw new IllegalStateException("Driver " + x + " is not a javascript executor")
    }
    this
  }
}
