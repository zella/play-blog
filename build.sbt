name := """easy_blog"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file("."))
  .enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.11.6"

// add resolver for deadbolt and easymail snapshots
resolvers += Resolver.sonatypeRepo("snapshots")

libraryDependencies ++= Seq(
  "com.feth" %% "play-authenticate" % "0.7.0-SNAPSHOT",
  javaJdbc,
  cache,
  javaWs
)

libraryDependencies += "org.pegdown" % "pegdown" % "1.5.0"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
//routesGenerator := InjectedRoutesGenerator
