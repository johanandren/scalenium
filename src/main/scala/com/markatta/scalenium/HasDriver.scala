package com.markatta.scalenium

import org.openqa.selenium.WebDriver

trait HasDriver {
  protected def driver: WebDriver
}
