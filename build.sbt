name := "stat"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache
)     

libraryDependencies += "org.mockito" % "mockito-all" % "1.8.4"

play.Project.playJavaSettings
