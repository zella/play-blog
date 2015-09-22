name := """play_blog"""

version := "0.1-SNAPSHOT"

lazy val root = (project in file("."))
  .enablePlugins(PlayJava)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  cache
)

libraryDependencies += "org.pegdown" % "pegdown" % "1.5.0"

libraryDependencies += "com.orientechnologies" % "orientdb-core" % "2.1.1" withSources() withJavadoc()

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
//routesGenerator := InjectedRoutesGenerator
