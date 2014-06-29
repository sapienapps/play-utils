name := "sapienapps"

organization := "com.sapiendevices"

version := "1.0"

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  "org.mindrot" % "jbcrypt" % "0.3m"
  //"org.webjars" %% "webjars-play" % "2.2.2",
  //"com.typesafe.play" %% "play-slick" % "0.7.0-M1"
)

publishTo := Some(Resolver.file("sapienapps",  new File(Path.userHome + "/Dropbox/public/libs" )) )