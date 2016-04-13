package net.sf.historicalprices

import java.util.concurrent.TimeUnit

import dispatch._
import dispatch.{Future, Http, url, _}
import Defaults._
import org.joda.time.LocalDate
import org.joda.time.format.{DateTimeFormat, DateTimeFormatter}
import org.slf4j.LoggerFactory

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.io.Source
import scala.util.{Failure, Success, Try}


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
    *         to (businessDate), inclusively.
    */
  def dailyPrices(businessDate: LocalDate, ticker: String): Seq[DailyPrice]


}


object YahooDataSource extends DataSource {

  val logger = LoggerFactory.getLogger(this.getClass)

  val delimiter = ","
  val datePattern = "yyyy-MM-dd"
  val dateTimeFormatter = DateTimeFormat.forPattern(datePattern)

  //Todo : To make this configurable.
  val timeout = Duration(10, TimeUnit.SECONDS)

  def dailyPrices(businessDate: LocalDate, ticker: String): Seq[DailyPrice] = {

    logger.info(s"Processing $ticker,$businessDate")

    val urlStr = PricesURLProvider.pricesURL(businessDate, ticker)

    val responseF: Future[Res] = Http(url(urlStr))

    val resultF: Future[Seq[DailyPrice]] = responseF.map {
      response => {
        val inStream = response.getResponseBodyAsStream

        // Drop the first line, which is the column headers.
        val lines = Source.fromInputStream(inStream).getLines().drop(1)
        lines.map(lineToDailyPrice).toSeq
      }
    }


    Await.result(resultF, timeout)

  }


  def lineToDailyPrice(line: String): DailyPrice = {
    val tokens = line.split(delimiter)
    val date = dateTimeFormatter.parseDateTime(tokens(0)).toLocalDate
    val open = tokens(1).toDouble
    val high = tokens(2).toDouble
    val low = tokens(3).toDouble
    val close = tokens(4).toDouble
    val volume = tokens(5).toInt
    val adjClose = tokens(6).toDouble

    DailyPrice(date, open, high, low, close, volume, adjClose)
  }

}
