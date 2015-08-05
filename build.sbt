name := """easy_blog"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file("."))
  .enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.11.6"

// add resolver for deadbolt and easymail snapshots
resolvers += Resolver.sonatypeRepo("snapshots")

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs
)

libraryDependencies += "com.feth" %% "play-authenticate" % "0.7.0-SNAPSHOT"

libraryDependencies += "org.xerial" % "sqlite-jdbc" % "3.8.11"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator
