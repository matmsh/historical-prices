package net.sf.historicalprices

import org.joda.time.LocalDate
import org.scalatest.{Inspectors, Matchers, WordSpec}
import HistoricalPricesServiceTest._

class HistoricalPricesServiceTest extends WordSpec with Matchers with Inspectors {
  val tol = 0.00000001

  val mockDataSrc = new DataSource {
    override def dailyPrices(businessDate: LocalDate,
                             ticker: String): Seq[DailyPrice] = pricesMap(ticker)

  }

  val pricesService = HistoricalPricesService(mockDataSrc)

  "HistoricalPricesService" should {
    "return the correct daily prices" in {

      val actual = pricesService.dailyPrices("AMD")
      actual should be(List(2.76, 2.74, 2.64, 2.8))

    }

    "return the only 1 daily prices " in {
      val actual = pricesService.dailyPrices("BP.L")
      actual should be(List(315.76))
    }


    "return the emptylist of  daily prices " in {
      val actual = pricesService.dailyPrices("LLOY.L")
      actual should be(List.empty[Double])
    }


    "compute the correct returns" in {
      val actual = pricesService.returns("AMD")

      val expected = List(0.00729927, 0.0378787879, -0.0571428571)

      forAll(actual zip expected) {
        case (act, exp) => act should be(exp +- tol)
      }

    }

    "throws an exception when computing returns with 1 daily price" in {
      val errMsg = "The no of daily prices of BP.L must be at least 2 to compute the returns. Found 1."

      val ex = intercept[RuntimeException] {
        pricesService.returns("BP.L")
      }
      ex.getMessage should be(errMsg)
    }

    "throws an exception when computing returns with no daily price" in {
      val errMsg = "The no of daily prices of LLOY.L must be at least 2 to compute the returns. Found 0."

      val ex = intercept[RuntimeException] {
        pricesService.returns("LLOY.L")
      }
      ex.getMessage should be(errMsg)
    }

    "compute the correct mean return" in {
      val actual = pricesService.meanReturn("AMD")
      val expected = -0.0039882664

      actual should be(expected +- tol)
    }

  }


}


object HistoricalPricesServiceTest {

  object AMD {
    val date20160411 = LocalDate.parse("2016-04-11")
    val date20160408 = LocalDate.parse("2016-04-08")
    val date20160407 = LocalDate.parse("2016-04-07")
    val date20160406 = LocalDate.parse("2016-04-06")


    val dp20160411 = DailyPrice(date20160411, 2.76, 2.82, 2.74, 2.76, 9031800, 2.76)
    val dp20160408 = DailyPrice(date20160408, 2.70, 2.76, 2.68, 2.74, 8465500, 2.74)
    val dp20160407 = DailyPrice(date20160407, 2.76, 2.83, 2.61, 2.64, 13360400, 2.64)
    val dp20160406 = DailyPrice(date20160406, 2.75, 2.80, 2.72, 2.80, 12473400, 2.80)


    val dailyPrices = List(dp20160411, dp20160408, dp20160407, dp20160406)

  }


  object BP {
    val date20160411 = LocalDate.parse("2016-04-11")
    val dp20160411 = DailyPrice(date20160411, 12.76, 12.82, 12.74, 315.76, 9031800, 12.76)
    val dailyPrices = List(dp20160411)
  }


  object LLOY {

    val dailyPrice = List.empty[DailyPrice]
  }


  val pricesMap = Map("AMD" -> AMD.dailyPrices,
    "BP.L" -> BP.dailyPrices, "LLOY.L" -> LLOY.dailyPrice)

}