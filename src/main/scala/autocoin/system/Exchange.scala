package autocoin.system

import autocoin.system.impl.{Exchange => ImplExchange}
import org.knowm.xchange.ExchangeFactory
import org.knowm.xchange.bitstamp.{BitstampExchange => JBitstampExchange}
import org.knowm.xchange.livecoin.{LivecoinExchange => JLivecoinExchange}

trait Exchange {
  def name: String
  def marketDataService: MarketDataService
}

object Exchange {
  def apply(name: String): Exchange = name match {
    case "Bitstamp" => new ImplExchange(ExchangeFactory.INSTANCE.createExchange(classOf[JBitstampExchange].getName))
    case "Livecoin" => new ImplExchange(ExchangeFactory.INSTANCE.createExchange(classOf[JLivecoinExchange].getName))
  }
}