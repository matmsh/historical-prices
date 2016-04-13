import sbt._
import Keys._
import sbtassembly.AssemblyPlugin.autoImport._




object Assembly {
  val settings = Seq(
    test in assembly := {},
    assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false)

  )

}
