name := """play_blog"""

version := "0.2-SNAPSHOT"

lazy val root = (project in file("."))
  .enablePlugins(PlayJava)

scalaVersion := "2.11.6"

resolvers += "amateras" at "http://amateras.sourceforge.jp/mvn/"

libraryDependencies += "io.github.gitbucket" % "markedj" % "1.0.8" withSources() withJavadoc()

libraryDependencies += "com.orientechnologies" % "orientdb-core" % "2.1.20" withJavadoc()

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
//routesGenerator := InjectedRoutesGenerator
