import de.heikoseeberger.sbtheader.license.Apache2_0

name := "scalenium"

version := "1.0.1"

organization := "com.markatta"

scalaVersion := "2.11.4"

scalacOptions ++= Seq("-feature", "-deprecation")

libraryDependencies ++= Seq(
  "org.seleniumhq.selenium" % "selenium-java" % "2.44.0",
  // this should probably be an optional dependency somehow, extract specs2 integration to separate module?
  "org.specs2" %% "specs2" % "2.4.15"
)

resolvers ++= Seq(
   // required for the scalaz-streams dependency from specs2 :(
  "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases"
)

initialCommands := """
import com.markatta.scalenium._
val browser = new Browser(new org.openqa.selenium.htmlunit.HtmlUnitDriver)
"""

// releasing
sonatypeSettings
licenses := Seq("Apache License, Version 2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0"))
homepage := Some(url("https://github.com/johanandren/scalenium"))
publishMavenStyle := true
publishArtifact in Test := false
pomIncludeRepository := { _ => false }
publishTo := Some {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    "snapshots" at nexus + "content/repositories/snapshots"
  else
    "releases" at nexus + "service/local/staging/deploy/maven2"
}

pomExtra :=
  <scm>
    <url>git@github.com:johanandren/scalenium.git</url>
    <connection>scm:git:git@github.com:johanandren/scalenium.git</connection>
  </scm>
    <developers>
      <developer>
        <id>johanandren</id>
        <name>Johan Andrén</name>
        <email>johan@markatta.com</email>
        <url>https://markatta.com/johan/codemonkey</url>
      </developer>
    </developers>