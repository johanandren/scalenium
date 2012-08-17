package com.markatta.scalenium

import org.openqa.selenium.{OutputType, TakesScreenshot}
import java.io.File
import org.apache.commons.io.FileUtils

trait ScreenShots { this: HasDriver =>

  private def defaultScreenShotName = System.currentTimeMillis + ".png"

  def takeScreenShot(fileName: String = defaultScreenShotName): this.type = {
    require(fileName != "", "Filename of screenshot cannot be empty")

    // TODO default directory?
    val scrFile = driver.asInstanceOf[TakesScreenshot].getScreenshotAs(OutputType.FILE)
    try {
      val destFile = new File(fileName)
      FileUtils.copyFile(scrFile, destFile)
    } catch {
      case e: RuntimeException => throw new RuntimeException("error when taking the snapshot", e)
    }

    this
  }

}
