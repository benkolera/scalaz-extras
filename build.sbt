import ReleaseKeys._
import sbtrelease.{Version,versionFormatError}

organization := "com.benkolera"

name := "scalaz-extra"

scalaVersion := "2.10.3"

scalacOptions ++= Seq("-feature","-deprecation","-Xfatal-warnings")

libraryDependencies ++= Seq(
  "org.scalaz" %% "scalaz-core" % "7.0.3",
  "org.specs2" %% "specs2"      % "2.2.2" % "test"
)

resolvers ++= Seq()

releaseSettings

nextVersion := { ver =>
  Version(ver).map(_.bumpBugfix.asSnapshot.string).getOrElse(versionFormatError)
}

//Make this publish to oss.sonatype.com later
publishTo <<= version { (v: String) =>
  val nexus = "http://nexus.benkolera.com/nexus"
  if (v.trim.endsWith("SNAPSHOT"))
    Some("snapshots" at nexus + "content/repositories/snapshots/")
  else
    Some("releases"  at nexus + "content/repositories/releases/")
}
