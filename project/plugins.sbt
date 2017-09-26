
// The Play plugin
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.6.5")

//Scala.js
addSbtPlugin("org.scala-js" % "sbt-scalajs" % "0.6.20")
addSbtPlugin("com.vmunier" % "sbt-web-scalajs" % "1.0.6")


libraryDependencies += "org.jsoup" % "jsoup" % "1.9.2" % Compile
