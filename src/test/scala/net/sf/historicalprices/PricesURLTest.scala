package net.sf.historicalprices

import org.scalatest.{Matchers, WordSpec}
import PricesURLProvider.pricesURL
import org.joda.time.LocalDate

class PricesURLTest extends WordSpec with Matchers{



  "pricesURL" should {
    "return a correct url for single digit day" in {

      val date20151201 = LocalDate.parse("2015-12-01")

      val urlActual = pricesURL(date20151201,"AMD")

      println(urlActual)

      val urlExpected = """http://real-chart.finance.yahoo.com/table.csv?s=AMD&a=11&b=01&c=2014&d=11&e=01&f=2015&g=d&ignore=.csv"""
      urlActual should be (urlExpected)

    }

    "return a correct url for single digit month" in {

      val date20160412 = LocalDate.parse("2016-04-12")

      val urlActual = pricesURL(date20160412,"AMD")

      val urlExpected = """http://real-chart.finance.yahoo.com/table.csv?s=AMD&a=03&b=12&c=2015&d=03&e=12&f=2016&g=d&ignore=.csv"""
      urlActual should be (urlExpected)

    }

    "return a correct url for double digit day and month" in {

      val date20151210 = LocalDate.parse("2015-12-10")

      val urlActual = pricesURL(date20151210,"AMD")

      val urlExpected = """http://real-chart.finance.yahoo.com/table.csv?s=AMD&a=11&b=10&c=2014&d=11&e=10&f=2015&g=d&ignore=.csv"""
      urlActual should be (urlExpected)

    }

  }



}
