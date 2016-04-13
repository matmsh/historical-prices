package net.sf.historicalprices

import org.joda.time.LocalDate


object HistoricalPricesDemo {

  def main(args: Array[String]) {

    if (args.size != 1) {
      println("""usage : (ticker)
           | Eg GOOG
        """.stripMargin)

      sys.exit(1)
    }

    val ticker = args(0)
    val today = LocalDate.now()

    val dataSrc = YahooDataSource

    val pricesService = HistoricalPricesService(dataSrc)


    val dailyPrices = pricesService.dailyPrices(ticker)
    val dailyReturn = pricesService.returns(ticker)
    val meanReturn = pricesService.meanReturn(ticker)



    println(
      s"""One year data for $ticker:
         |  no of daily prices = ${dailyPrices.size}
         |  no of daily returns = ${dailyReturn.size}
         |  meanReturn = ${meanReturn}
       """.stripMargin


    )

    sys.exit(0)

  }

}
