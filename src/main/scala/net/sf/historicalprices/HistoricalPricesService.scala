package net.sf.historicalprices

import org.joda.time.LocalDate
import org.slf4j.LoggerFactory


trait HistoricalPricesService {


  /**
    *
    * @param ticker
    * @return  1 year (from (today - year) to today inclusively)
    *          historical (closing) prices of a given ticker.
    *          The prices are in
    *         Chronological descending order. The first one is the latest.
    */
  def dailyPrices(ticker: String) : List[Double]




  /**
    * Daily returns, where return = ( Price_Today – Price_Yesterday)/Price_Yesterday
    * @param ticker
    * @return The daily returns in the last year for given ticker.
    */
  def returns(ticker:String) : Seq[Double] ={
     val dPrices = dailyPrices(ticker)

    if (dPrices.size > 1) {

      // Drop last
      val todayPrices = dPrices.dropRight(1)

      // Drop 1st
      val yesterdayPrices = dPrices.drop(1)

      val dailyReturn = (todayYesterday: (Double, Double)) => {
        val (today, yesterday) = todayYesterday
        (today - yesterday) / yesterday
      }

      todayPrices.zip(yesterdayPrices).map(dailyReturn)

    } else {
      val errMsg = s"The no of daily prices of ${ticker} must be at least 2 to compute the returns. Found ${dPrices.size}."
      throw new RuntimeException(errMsg)
    }

  }



  /**
    *
    * @param ticker
    * @return The mean returns of ticker in the last year.
    */
  def meanReturn(ticker:String): Double ={
      val dReturns = returns(ticker)
      dReturns.sum/dReturns.size

  }

}



trait BasePricesService extends HistoricalPricesService {
  val logger = LoggerFactory.getLogger(this.getClass)

  val dataSource:DataSource

  /**
    *
    * @param ticker
    * @return  1 year (from (today - year) to today inclusively)
    *          historical (closing) prices of a given ticker.
    *          The prices are in
    *         Chronological descending order. The first one is the latest.
    */
  def dailyPrices(ticker: String) : List[Double] ={

    val today = LocalDate.now()

    val dailyPrices = dataSource.dailyPrices(today,ticker)

    logger.info(
      s"""Lastest price:${dailyPrices.headOption}
         |Earliers price:${dailyPrices.lastOption}
         |"""".stripMargin)

    dailyPrices.map(_.close).toList
  }


}


object HistoricalPricesService {


  def apply(dataSrc: DataSource):HistoricalPricesService={

    new BasePricesService {
      override val dataSource: DataSource = dataSrc
    }

  }




}