import sbt._
import sbt.Keys._


/**
  * To run sCoverage : sbt clean coverage test coverageReport
  */
object HistoricalPricesScalaBuild  extends Build {
 
  val _organization="net.sf"
  val _scalaVersions=Seq("2.10.6","2.11.8")
  val _version="1.0-SNAPSHOT"


 lazy val historicalPrices  = Project(
    id = "historicalprices",
    base = file("."),
    settings = Defaults.coreDefaultSettings ++
      SCoverage.settings ++
      net.virtualvoid.sbt.graph.DependencyGraphSettings.graphSettings ++ Seq(
      name := "historicalprices",
      organization := _organization,
      version := _version,
      isSnapshot := true,
      crossScalaVersions := _scalaVersions,

      ivyScala := ivyScala.value map { _.copy(overrideScalaVersion = true) },

      parallelExecution in Test := false,
      scalacOptions ++= Seq("-deprecation", "-feature"),


      libraryDependencies ++=  Seq(         
         "joda-time"                 % "joda-time"            % "2.8",
         "org.joda"                  % "joda-convert"         % "1.8.1",

         "net.databinder.dispatch"   %% "dispatch-core"       % "0.11.3",

         "ch.qos.logback"            % "logback-classic"      % "1.1.1",
         "org.scalatest"            %% "scalatest"            % "2.2.5"   % "test"

       )

    )  
  ) // end of Project    
   .settings(Assembly.settings:_*)

}  


