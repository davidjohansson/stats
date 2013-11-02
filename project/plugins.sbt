import sbt._
// Comment to get more information during initialization
logLevel := Level.Warn

// The Typesafe repository
resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

// Use the Play sbt plugin for Play projects
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.2.0")

addSbtPlugin("play" % "sbt-plugin" % "2.0.2")

// This plugin is used to load the sbt-jasmine plugin into our project. 
// This allows us to import the SbtJasminePlugin file
// in Build.scala, and then set the settings and configuration for Sbt-Jasmine
object Plugins extends Build {
  lazy val plugins = Project("plugins", file("."))
    .dependsOn(uri("git://github.com/guardian/sbt-jasmine-plugin.git#0.7"))
}