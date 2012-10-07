name := "scalenium"

version := "1.0"

scalaVersion := "2.9.1"

libraryDependencies ++= Seq(
  "org.seleniumhq.selenium" % "selenium-java" % "2.20.0",
  // this should probably be an optional dependency somehow, extract specs2 integration to separate module?
  "org.specs2" %% "specs2" % "1.8.2" 
)

resolvers += "releases" at "http://oss.sonatype.org/content/repositories/releases"

initialCommands := """
import com.markatta.scalenium._
val browser = new Browser(new org.openqa.selenium.htmlunit.HtmlUnitDriver)
"""
