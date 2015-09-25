name := """play_blog"""

version := "0.1-SNAPSHOT"

lazy val root = (project in file("."))
  .enablePlugins(PlayJava)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  cache
)

resolvers += "amateras" at "http://amateras.sourceforge.jp/mvn/"

libraryDependencies += "io.github.gitbucket" % "markedj" % "1.0.2"

libraryDependencies += "com.orientechnologies" % "orientdb-core" % "2.1.1" withSources() withJavadoc()

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
//routesGenerator := InjectedRoutesGenerator
