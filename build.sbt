
name := "scalacheck-demo"

scalaVersion := "2.11.6"

libraryDependencies := Seq(
  "org.scalacheck" %% "scalacheck" % "1.12.2",
  "org.scalatest" %% "scalatest" % "2.2.1",
  "org.scalaz" %% "scalaz-core" % "7.1.1",
  "org.typelevel" %% "shapeless-scalacheck" % "0.3"
)

