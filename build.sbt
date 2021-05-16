name := """scala-play-angular-shop"""
organization := "com.maksple"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.5"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-slick" % "5.0.0",
  "com.typesafe.play" %% "play-slick-evolutions" % "5.0.0"
)
libraryDependencies += "org.postgresql" % "postgresql" % "42.2.6"
// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.maksple.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.maksple.binders._"
libraryDependencies += "org.mongodb.scala" %% "mongo-scala-driver" % "2.8.0"
