package net.sf.historicalprices

import org.joda.time.LocalDate
import org.scalatest.{Matchers, WordSpec}



class YahooDataSourceTest extends WordSpec with Matchers{



  val lineParser = YahooDataSource.lineToDailyPrice _

  "lineParser" should {
    "parse daily prices line correctly" in {

      val line = "2015-12-09,2.37,2.40,2.32,2.35,8436700,2.35"

      val actual = lineParser(line)


      val date: LocalDate = LocalDate.parse("2015-12-09")
      val expected = DailyPrice(date,2.37,2.40,2.32,2.35,6436700,2.35)

      actual should be (expected)
    }

  }

}
