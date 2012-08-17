package com.markatta.scalenium

import org.openqa.selenium.JavascriptExecutor

trait Scripts { this: HasDriver =>
  def executeScript(script: String, args: String*): this.type = {
    driver.asInstanceOf[JavascriptExecutor].executeScript(script, args: _*)
    this
  }

  def executeAsyncScript(script: String, args: String*): this.type = {
    driver.asInstanceOf[JavascriptExecutor].executeAsyncScript(script, args: _*)
    this
  }
}
