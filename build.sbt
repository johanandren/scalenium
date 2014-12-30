name := "scalenium"

version := "1.0"

scalaVersion := "2.11.4"

scalacOptions ++= Seq("-feature", "-deprecation")

libraryDependencies ++= Seq(
  "org.seleniumhq.selenium" % "selenium-java" % "2.44.0",
  // this should probably be an optional dependency somehow, extract specs2 integration to separate module?
  "org.specs2" %% "specs2" % "2.4.15"
)



resolvers ++= Seq(
  "releases" at "http://oss.sonatype.org/content/repositories/releases",
   // required for the scalaz-streams dependency from specs2 :(
  "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases"
)


initialCommands := """
import com.markatta.scalenium._
val browser = new Browser(new org.openqa.selenium.htmlunit.HtmlUnitDriver)
"""
