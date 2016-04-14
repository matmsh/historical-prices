package net.sf.historicalprices

import java.util.concurrent.TimeUnit

import dispatch.Defaults._
import dispatch.{Future, Http, url, _}
import org.joda.time.LocalDate
import org.slf4j.LoggerFactory

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.io.Source


case class DailyPrice(date: LocalDate,
                      open: Double,
                      high: Double,
                      low: Double,
                      close: Double,
                      volume: Int,
                      adjClose: Double)

trait DataSource {

  /**
    * @param businessDate
    * @param ticker
    * @return Daily prices for ticker from (businessDate - yr)
    *         to (businessDate), inclusively. The DailyPrices are in
    *         Chronological descending order. The first one is the latest.
    */
  def dailyPrices(businessDate: LocalDate, ticker: String): Seq[DailyPrice]


}




object YahooDataSource extends DataSource {

  val logger = LoggerFactory.getLogger(this.getClass)

  val delimiter = ","

  //Todo : To make this configurable.
  val timeout = Duration(10, TimeUnit.SECONDS)


  /**
    *
    * @param businessDate
    * @param ticker
    * @return Daily prices for ticker from (businessDate - yr)
    *         to (businessDate), inclusively.
    */
  def dailyPrices(businessDate: LocalDate, ticker: String): Seq[DailyPrice] = {

    logger.info(s"Processing $ticker,$businessDate")

    val urlStr = PricesURLProvider.pricesURL(businessDate, ticker)

    val responseF: Future[Res] = Http(url(urlStr))

    val resultF: Future[Seq[DailyPrice]] = responseF.map {
      response => {
        val inStream = response.getResponseBodyAsStream

        try {
          // Drop the first line, which is the column headers.
          val lines = Source.fromInputStream(inStream).getLines().drop(1)
          lines.map(lineToDailyPrice).toSeq
        } finally{
           inStream.close()
        }
      }
    }


    Await.result(resultF, timeout)

  }


  def lineToDailyPrice(line: String): DailyPrice = {
    val tokens = line.split(delimiter)
    val date = LocalDate.parse(tokens(0))
    val open = tokens(1).toDouble
    val high = tokens(2).toDouble
    val low = tokens(3).toDouble
    val close = tokens(4).toDouble
    val volume = tokens(5).toInt
    val adjClose = tokens(6).toDouble

    DailyPrice(date, open, high, low, close, volume, adjClose)
  }

}
