# historical-prices
A respository of functions to retrieve historical data from remote sources


Sample output:
```
> runMain net.sf.historicalprices.HistoricalPricesDemo GOOG
[info] Compiling 1 Scala source to /home/matmsh/workspaces/luna/scala/historical-prices/target/scala-2.10/classes...
[info] Running net.sf.historicalprices.HistoricalPricesDemo GOOG
21:14:01.549 [run-main-1] INFO  n.s.h.YahooDataSource$ dailyPrices - Processing GOOG,2016-04-14
21:14:01.551 [run-main-1] INFO  n.s.h.PricesURLProvider$ pricesURL - url=http://real-chart.finance.yahoo.com/table.csv?s=GOOG&a=03&b=14&c=2015&d=03&e=14&f=2016&g=d&ignore=.csv
21:14:01.835 [run-main-1] INFO  n.s.h.HistoricalPricesService$$anon$1 dailyPrices - Lastest price:Some(DailyPrice(2016-04-13,749.159973,754.380005,744.260986,751.719971,1707100,751.719971))
Earliest price:Some(DailyPrice(2015-04-14,536.252409,537.572435,528.094416,530.392405,2604100,530.392405))
"
21:14:01.837 [run-main-1] INFO  n.s.h.YahooDataSource$ dailyPrices - Processing GOOG,2016-04-14
21:14:01.838 [run-main-1] INFO  n.s.h.PricesURLProvider$ pricesURL - url=http://real-chart.finance.yahoo.com/table.csv?s=GOOG&a=03&b=14&c=2015&d=03&e=14&f=2016&g=d&ignore=.csv
21:14:02.015 [run-main-1] INFO  n.s.h.HistoricalPricesService$$anon$1 dailyPrices - Lastest price:Some(DailyPrice(2016-04-13,749.159973,754.380005,744.260986,751.719971,1707100,751.719971))
Earliest price:Some(DailyPrice(2015-04-14,536.252409,537.572435,528.094416,530.392405,2604100,530.392405))
"
21:14:02.018 [run-main-1] INFO  n.s.h.YahooDataSource$ dailyPrices - Processing GOOG,2016-04-14
21:14:02.018 [run-main-1] INFO  n.s.h.PricesURLProvider$ pricesURL - url=http://real-chart.finance.yahoo.com/table.csv?s=GOOG&a=03&b=14&c=2015&d=03&e=14&f=2016&g=d&ignore=.csv
21:14:02.204 [run-main-1] INFO  n.s.h.HistoricalPricesService$$anon$1 dailyPrices - Lastest price:Some(DailyPrice(2016-04-13,749.159973,754.380005,744.260986,751.719971,1707100,751.719971))
Earliest price:Some(DailyPrice(2015-04-14,536.252409,537.572435,528.094416,530.392405,2604100,530.392405))
"
One year data for GOOG:
  no of daily prices = 253
  Last 3 latest prices = List(751.719971, 743.090027, 736.099976)
  no of daily returns = 252
  Last 3 latest returns = List(0.01161359147133329, 0.009496061986014784)
  meanReturn = 0.001561435109716154

[success] Total time: 4 s, completed 14-Apr-2016 21:14:02
>
```
