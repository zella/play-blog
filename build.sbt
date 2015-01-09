name := """play-blog"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  javaJdbc,
  javaJpa,
  "org.hibernate" % "hibernate-entitymanager" % "4.3.8.Final" // replace by your jpa implementation
)
