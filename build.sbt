name := """scala-play-angular-shop"""
organization := "com.maksple"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.10"
routesGenerator := InjectedRoutesGenerator

resolvers += Resolver.jcenterRepo
resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

libraryDependencies ++= Seq(ehcache, ws, specs2 % Test, guice, filters)

val playSilhouetteVersion = "6.0.0"
libraryDependencies ++= Seq(
  "com.mohiva" %% "play-silhouette" % playSilhouetteVersion,
  "com.mohiva" %% "play-silhouette-password-bcrypt" % playSilhouetteVersion,
  "com.mohiva" %% "play-silhouette-persistence" % playSilhouetteVersion,
  "com.mohiva" %% "play-silhouette-crypto-jca" % playSilhouetteVersion,
  "com.mohiva" %% "play-silhouette-persistence-reactivemongo" % "5.0.6",
  )

libraryDependencies ++= Seq(
  "net.codingwell" %% "scala-guice" % "4.2.6",
  "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test,
  "org.reactivemongo" %% "play2-reactivemongo" % "0.20.13-play28",
  "org.reactivemongo" %% "reactivemongo-play-json-compat" % "0.20.13-play28",
  "com.iheart" %% "ficus" % "1.5.0",
  "org.apache.commons" % "commons-lang3" % "3.12.0"
)