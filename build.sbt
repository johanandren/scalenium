name := "scalenium"

version := "1.0"

scalaVersion := "2.11.4"

scalacOptions ++= Seq("-feature", "-deprecation")

libraryDependencies ++= Seq(
  "org.seleniumhq.selenium" % "selenium-java" % "2.44.0",
  // this should probably be an optional dependency somehow, extract specs2 integration to separate module?
  "org.specs2" %% "specs2" % "2.4.14" 
)

resolvers += "releases" at "http://oss.sonatype.org/content/repositories/releases"

initialCommands := """
import com.markatta.scalenium._
val browser = new Browser(new org.openqa.selenium.htmlunit.HtmlUnitDriver)
"""
