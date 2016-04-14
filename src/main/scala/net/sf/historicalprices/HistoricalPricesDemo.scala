package net.sf.historicalprices

import javax.xml.ws.Dispatch

import dispatch.Http


object HistoricalPricesDemo {

  def main(args: Array[String]) {

    if (args.size != 1) {
      println("""usage : (ticker)
           | Eg GOOG
        """.stripMargin)

      System.exit(1)
    }

    val ticker = args(0)

    val dataSrc = YahooDataSource

    val pricesService = HistoricalPricesService(dataSrc)


    val dailyPrices = pricesService.dailyPrices(ticker)
    val dailyReturn = pricesService.returns(ticker)
    val meanReturn = pricesService.meanReturn(ticker)


    println(
      s"""One year data for $ticker:
         |  no of daily prices = ${dailyPrices.size}
         |  Last 3 latest prices = ${dailyPrices.take(3)}
         |  no of daily returns = ${dailyReturn.size}
         |  Last 2 latest returns = ${dailyReturn.take(2)}
         |  meanReturn = ${meanReturn}
       """.stripMargin


    )


    //Todo : Find a better way to shutdown dispatch.Http
    Http.shutdown()

  }

}
