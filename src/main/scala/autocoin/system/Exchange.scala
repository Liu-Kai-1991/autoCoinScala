package autocoin.system

import autocoin.system.impl.{Exchange => ImplExchange}
import org.knowm.xchange.ExchangeFactory
import org.knowm.xchange.bitstamp.BitstampExchange
import org.knowm.xchange.livecoin.LivecoinExchange
import org.knowm.xchange.bittrex.BittrexExchange
import org.knowm.xchange.bitfinex.v1.BitfinexExchange
import org.knowm.xchange.hitbtc.v2.HitbtcExchange

import scala.collection.immutable._

trait Exchange {
  def name: String
  def marketDataService: MarketDataService
  def getExchangeSymbols: Seq[CurrencyPair]
}

object Exchange {
  lazy val Bitstamp = new ImplExchange(ExchangeFactory.INSTANCE.createExchange(classOf[BitstampExchange].getName))
  lazy val Livecoin = new ImplExchange(ExchangeFactory.INSTANCE.createExchange(classOf[LivecoinExchange].getName))
  lazy val Bittrex = new ImplExchange(ExchangeFactory.INSTANCE.createExchange(classOf[BittrexExchange].getName))
  lazy val Bitfinex = new ImplExchange(ExchangeFactory.INSTANCE.createExchange(classOf[BitfinexExchange].getName))
  lazy val Hitbtc = new ImplExchange(ExchangeFactory.INSTANCE.createExchange(classOf[HitbtcExchange].getName))
}