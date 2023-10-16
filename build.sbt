scalaVersion := "2.12.18"
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-cluster" % "2.6.21",
  "com.github.romix.akka" %% "akka-kryo-serialization" % "0.5.1",
)
enablePlugins(PackPlugin)
