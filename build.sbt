name := "sapienapps"

organization := "com.sapiendevices"

version := "1.0.0"

scalaVersion := "2.11.1"

//resolvers += "Typesafe Maven Repository" at "http://repo.typesafe.com/typesafe/maven-releases/"

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play" % "2.3.0",
  "com.typesafe.play.plugins" %% "play-plugins-mailer" % "2.3.0",
  "org.mindrot" % "jbcrypt" % "0.3m"
  //"org.webjars" %% "webjars-play" % "2.2.2",
  //"com.typesafe.play" %% "play-slick" % "0.7.0-M1"
)

publishTo := Some(Resolver.file("sapienapps",  new File(Path.userHome + "/Dropbox/public/libs" )) )