package autocoin.system.impl

import autocoin.convert.Converter.JCurrencyPairImplicit
import autocoin.system.{Exchange => ApiExchange, _}
import org.knowm.xchange.{Exchange => JExchange}

import scala.collection.JavaConverters._
import scala.collection.immutable._

class Exchange(jObj: JExchange) extends ApiExchange {
  lazy val name: String = jObj.getDefaultExchangeSpecification.getExchangeName
  override def marketDataService: MarketDataService = new MarketDataService(jObj.getMarketDataService)
  override def getExchangeSymbols: Seq[CurrencyPair] = jObj.getExchangeSymbols.asScala.flatMap{
    jCurrencyPair => jCurrencyPair.toCurrencyPairOption
  }.toVector
}
