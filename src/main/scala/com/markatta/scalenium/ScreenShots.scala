/*
 * Copyright 2015 Johan Andrén
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
