
import scoverage.ScoverageKeys._
 
object SCoverage {

  val settings = Seq(
    coverageMinimum       := 50,
    coverageFailOnMinimum := true,
    coverageHighlighting  := true
  )
}
