name := """play-blog"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  javaJpa, //TODO rem?
  "com.orientechnologies" % "orient-commons" % "1.7.10",
  "com.orientechnologies" % "orientdb-core" % "1.7.10",
  "com.orientechnologies" % "orientdb-object" % "1.7.10",
  "com.orientechnologies" % "orientdb-client" % "1.7.10",
  "com.google.guava" % "guava" % "18.0",
  "com.feth" %% "play-authenticate" % "0.6.8"
)

//templatesImport += "model.Post"
