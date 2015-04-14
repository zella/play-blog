name := """play-blog"""

version := "0.1.0"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  javaJpa,
  "com.google.guava" % "guava" % "18.0",
  "com.feth" %% "play-authenticate" % "0.6.8"
)

libraryDependencies += "com.orientechnologies" % "orientdb-core" % "2.0.6" withSources() withJavadoc()

libraryDependencies += "com.orientechnologies" % "orientdb-client" % "2.0.6" withSources() withJavadoc()

libraryDependencies += "com.orientechnologies" % "orientdb-enterprise" % "2.0.6" withSources() withJavadoc()

libraryDependencies += "com.orientechnologies" % "orientdb-object" % "2.0.6" withSources() withSources() withJavadoc()

libraryDependencies += "com.orientechnologies" % "orientdb-lucene" % "2.0.6" withSources() withJavadoc()
