name := "scalenium"

version := "1.0"

scalaVersion := "2.9.1"

libraryDependencies ++= Seq(
  "org.seleniumhq.selenium" % "selenium-java" % "2.20.0",
  "org.specs2" %% "specs2" % "1.12.1" % "test"
)

resolvers += "releases" at "http://oss.sonatype.org/content/repositories/releases"