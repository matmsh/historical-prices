package net.sf.historicalprices

import org.joda.time.LocalDate
import org.slf4j.LoggerFactory


object PricesURLProvider {

  val logger = LoggerFactory.getLogger(this.getClass)

  /**
    * http://informedretailtrader.blogspot.co.uk/2014/07/getting-historical-data-in-csv-file.html
    *
    * @param businessDate
    * @param ticker
    * @return A url to download daily prices,from Yahoo, for ticker from (businessDate - yr)
    *         to (businessDate), inclusively.
    */
  def pricesURL(businessDate: LocalDate, ticker: String): String = {

    val lastYear = businessDate.minusYears(1)

    // Month is 0-based !
    val startDate = f"a=${lastYear.getMonthOfYear-1}%02d&b=${lastYear.getDayOfMonth}%02d&c=${lastYear.getYear}"
    val endDate = f"d=${businessDate.getMonthOfYear-1}%02d&e=${businessDate.getDayOfMonth}%02d&f=${businessDate.getYear}"
    val url = s"http://real-chart.finance.yahoo.com/table.csv?s=$ticker&${startDate}&${endDate}&g=d&ignore=.csv"

    logger.info(s"url=$url")

    url
  }
}
