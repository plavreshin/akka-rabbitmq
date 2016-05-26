organization := "com.thenewmotion.akka"
name := "akka-rabbitmq"

enablePlugins(OssLibPlugin)

licenses := Seq(("Apache License, Version 2.0", url("http://www.apache.org/licenses/LICENSE-2.0")))
homepage := Some(new URL("https://github.com/thenewmotion/akka-rabbitmq"))

def akka(scalaVersion: String) = {
  val version = scalaVersion match {
    case x if x.startsWith("2.10") => "2.3.14"
    case x => "2.4.6"
  }

  def libs(xs: String*) = xs.map(x => "com.typesafe.akka" %% s"akka-$x" % version)

  libs("actor") ++ libs("testkit").map(_ % "test")
}

libraryDependencies ++= {
  Seq(
    "com.rabbitmq" % "amqp-client" % "3.6.2",
    "com.typesafe" % "config" % "1.3.0" % "test",
    "org.specs2" %% "specs2-mock" % "2.4.17" % "test"
  )
}
libraryDependencies <++= scalaVersion { v: String => akka(v) }

unmanagedSourceDirectories in Compile <+= (sourceDirectory in Compile, scalaBinaryVersion){
  (s, v) => s / ("scala_"+v)
}

Format.settings

