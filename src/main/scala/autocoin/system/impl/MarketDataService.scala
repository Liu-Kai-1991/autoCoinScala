package autocoin.system.impl

import org.knowm.xchange.service.marketdata.{MarketDataService => JMarketDataService}
import org.knowm.xchange.dto.marketdata.{Ticker, Trades}
import autocoin.convert.Converter._
import autocoin.system.{MarketDataService => ApiMarketDataService, CurrencyPair, OrderBook}

class MarketDataService(val jObj: JMarketDataService) extends ApiMarketDataService {
  override def getOrderBook(currencyPair: CurrencyPair, range: Option[Double] = None): OrderBook = range match {
    case None => jObj.getOrderBook(currencyPair.toJCurrencyPair).toOrderBook
    case Some(r) => jObj.getOrderBook(currencyPair.toJCurrencyPair).toOrderBook.filterRange(r)
  }

  override def getTicker(currencyPair: CurrencyPair): Ticker =
    jObj.getTicker(currencyPair.toJCurrencyPair)

  override def getTrades(currencyPair: CurrencyPair): Trades =
    jObj.getTrades(currencyPair.toJCurrencyPair)
}

