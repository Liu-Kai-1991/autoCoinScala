import autocoin.system.{CurrencyPair, Exchange}

object TestRun extends App {
  println("Hello World")
  val bitstamp = Exchange("Livecoin")
  val marketDataService = bitstamp.marketDataService
  val orderBook = marketDataService.getOrderBook(CurrencyPair.BTC_USD).filterRange(0.1)
  orderBook.askOrders foreach println
}
