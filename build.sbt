name := """scala-play-angular-shop"""
organization := "com.maksple"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.5"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.maksple.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.maksple.binders._"
libraryDependencies += "org.mongodb.scala" %% "mongo-scala-driver" % "2.8.0"

// https://mvnrepository.com/artifact/org.reactivemongo/play2-reactivemongo
libraryDependencies += "org.reactivemongo" %% "play2-reactivemongo" % "1.0.0-play27"
libraryDependencies += "org.reactivemongo" % "reactivemongo-play-json_2.13" % "0.20.13-play27"