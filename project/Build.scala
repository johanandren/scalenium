import java.io.File
import sbt.Keys._
import sbt._
import bintray.Opts
import bintray.Plugin._
import bintray.Keys._

/**
 * this files is intended to build the main project
 * it contains links to all dependencies that are needed
 * */
object Build extends sbt.Build
{

  protected val bintrayPublishIvyStyle = settingKey[Boolean]("=== !publishMavenStyle") //workaround for sbt-bintray bug

  lazy val publishSettings = bintraySettings ++ Seq(
    repository in bintray := "denigma-releases",

    bintrayOrganization in bintray := Some("denigma"),

    licenses += ("MPL-2.0", url("http://opensource.org/licenses/MPL-2.0")),

    bintrayPublishIvyStyle := true
  )


  lazy val scalenium = (project in file(".")).settings( publishSettings:_* )

}