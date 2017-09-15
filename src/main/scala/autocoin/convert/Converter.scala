package autocoin.convert

import scala.collection.JavaConverters._
import autocoin.system._
import org.knowm.xchange.dto.trade.{LimitOrder => JOrder}
import org.knowm.xchange.dto.marketdata.{OrderBook => JOrderBook}
import org.knowm.xchange.dto.Order.{OrderType => JOrderType}
import org.knowm.xchange.currency.{CurrencyPair => JCurrencyPair, Currency => JCurrency}


object Converter {

  implicit class JOrderBookImplicit(val jObj: JOrderBook) extends AnyVal {
    def toOrderBook: LimitOrderBook = {
      val askOrders = jObj.getAsks.asScala.map(x => LimitAskOrder(x.getTradableAmount.doubleValue,
        x.getLimitPrice.doubleValue)).toVector
      val bidOrders = jObj.getBids.asScala.map(x => LimitBidOrder(x.getTradableAmount.doubleValue,
        x.getLimitPrice.doubleValue)).toVector
      LimitOrderBook(AskOrderSeq(askOrders), BidOrderSeq(bidOrders),
        jObj.getTimeStamp, jObj.getAsks.get(0).getCurrencyPair.toCurrencyPair)
    }
  }

  implicit class JCurrencyImplicit(val jObj: JCurrency) extends AnyVal {
    def toCurrency: Currency = Currency(jObj.getCurrencyCode)
    def toCurrencyOption: Option[Currency] = Currency.currencyMap.get(jObj.getCurrencyCode)
  }

  implicit class JCurrencyPairImplicit(val jObj: JCurrencyPair) extends AnyVal {
    def toCurrencyPair: CurrencyPair = CurrencyPair(
      Currency(jObj.base.getCurrencyCode), Currency(jObj.counter.getCurrencyCode))

    def toCurrencyPairOption: Option[CurrencyPair] = {
      val base = jObj.base.toCurrencyOption
      val counter = jObj.counter.toCurrencyOption
      if (base.isDefined && counter.isDefined) Some(CurrencyPair(base.get, counter.get)) else None
    }
  }

  implicit class JOrderImplicit(val jObj: JOrder) extends AnyVal{
    def toOrder: LimitOrder = jObj.getType match {
      case JOrderType.ASK => LimitAskOrder(jObj.getTradableAmount.doubleValue, jObj.getLimitPrice.doubleValue)
      case JOrderType.BID => LimitBidOrder(jObj.getTradableAmount.doubleValue, jObj.getLimitPrice.doubleValue)
      case jOrderType => throw new MatchError(s"$jOrderType not supported yet")
    }
  }
}
