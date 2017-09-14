import autocoin.system.{Currency, CurrencyPair, Exchange}

object TestRun extends App {
  val bitstamp = Exchange.Bittrex
  val marketDataService = bitstamp.marketDataService
  bitstamp.getExchangeSymbols.foreach{
    pair =>
      println(pair)
      val orderBook = marketDataService.getOrderBook(pair, Some(0.05))
      orderBook.askOrders foreach println
  }
}
