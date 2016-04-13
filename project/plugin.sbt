resolvers += "Scala Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"
 
addSbtPlugin("com.typesafe.sbteclipse"  % "sbteclipse-plugin"    % "4.0.0")

addSbtPlugin("net.virtual-void"         % "sbt-dependency-graph" % "0.8.2")

addSbtPlugin("org.scoverage"            % "sbt-scoverage"        % "1.3.5")

addSbtPlugin("com.eed3si9n"             % "sbt-assembly"         % "0.14.2")
