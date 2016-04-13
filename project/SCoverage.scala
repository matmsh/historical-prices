
import scoverage.ScoverageKeys._
 
object SCoverage {

  val settings = Seq(
    coverageMinimum       := 70,
    coverageFailOnMinimum := true,
    coverageHighlighting  := true
  )
}
