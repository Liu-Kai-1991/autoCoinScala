package autocoin.system

import org.knowm.xchange.dto.marketdata.{Ticker, Trades}

trait MarketDataService {
  def getTicker(currencyPair: CurrencyPair): Ticker

  def getOrderBook(currencyPair: CurrencyPair, range: Option[Double] = None): OrderBook

  def getTrades(currencyPair: CurrencyPair): Trades
}


