import autocoin.system.{Currency, CurrencyPair, Exchange}
import autocoin.util.PrintUtil
import autocoin.stat.Summary.OrderSeqImplicit

object Example extends App {
  val exchange = Exchange.Bittrex
  val marketDataService = exchange.marketDataService
  exchange.getExchangeSymbols.foreach{
    pair =>
      PrintUtil.printHeadline(s"$pair from ${exchange.name}")
      val orderBook = marketDataService.getOrderBook(pair, Some(0.05))
      PrintUtil.printHeadline("askOrders")
      orderBook.askOrders foreach println
      PrintUtil.printHeadline("askOrder summary")
      println(orderBook.askOrders.summary().toFormattedString)
  }
}
