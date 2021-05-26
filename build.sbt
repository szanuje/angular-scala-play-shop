name := """scala-play-angular-shop"""
organization := "com.maksple"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.5"

resolvers += Resolver.jcenterRepo
resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.maksple.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.maksple.binders._"

val playSilhouetteVersion = "6.1.1"
libraryDependencies ++= Seq(
  "com.mohiva" %% "play-silhouette" % playSilhouetteVersion,
  "com.mohiva" %% "play-silhouette-password-bcrypt" % playSilhouetteVersion,
  "com.mohiva" %% "play-silhouette-persistence" % playSilhouetteVersion,
  "com.mohiva" %% "play-silhouette-crypto-jca" % playSilhouetteVersion,
  "net.codingwell" %% "scala-guice" % "4.2.6",
  guice,
  filters,
  "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test,
  "org.mongodb.scala" %% "mongo-scala-driver" % "2.8.0",
  "org.reactivemongo" %% "play2-reactivemongo" % "1.0.0-play27",
  "org.reactivemongo" % "reactivemongo-play-json_2.13" % "0.20.13-play27"
)