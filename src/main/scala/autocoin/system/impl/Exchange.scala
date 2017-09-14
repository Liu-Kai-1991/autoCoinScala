package autocoin.system.impl

import autocoin.system.{Exchange => ApiExchange, _}
import org.knowm.xchange.{Exchange => JExchange}

class Exchange(jObj: JExchange) extends ApiExchange {
  lazy val name = jObj.getDefaultExchangeSpecification.getExchangeName
  override def marketDataService: MarketDataService = new MarketDataService(jObj.getMarketDataService)
}
