package autocoin.convert

import scala.collection.JavaConverters._
import autocoin.system._
import autocoin.system.OrderType._
import org.knowm.xchange.dto.trade.{LimitOrder => JOrder}
import org.knowm.xchange.dto.marketdata.{OrderBook => JOrderBook}
import org.knowm.xchange.dto.Order.{OrderType => JOrderType}
import org.knowm.xchange.currency.{CurrencyPair => JCurrencyPair}

object Converter {

  implicit class OrderTypeimplicit(jObj: JOrderType) {
    def toOrderType: OrderType = jObj match {
      case JOrderType.BID => BID
      case JOrderType.ASK => ASK
      case JOrderType.EXIT_ASK => EXIT_ASK
      case JOrderType.EXIT_BID => EXIT_BID
    }
  }

  implicit class JOrderBookImplicit(jObj: JOrderBook) {
    def toOrderBook: LimitOrderBook = {
      val askOrders = jObj.getAsks.asScala.map(x => LimitOrder(OrderType.ASK, x.getTradableAmount.doubleValue,
        x.getLimitPrice.doubleValue)).toVector
      val bidOrders = jObj.getBids.asScala.map(x => LimitOrder(OrderType.BID, x.getTradableAmount.doubleValue,
        x.getLimitPrice.doubleValue)).toVector
      LimitOrderBook(askOrders, bidOrders, jObj.getTimeStamp, jObj.getAsks.get(0).getCurrencyPair.toCurrencyPair)
    }
  }

  implicit class JCurrencyPairImplicit(jObj: JCurrencyPair) {
    def toCurrencyPair: CurrencyPair = CurrencyPair(
      Currency(jObj.base.getCurrencyCode), Currency(jObj.counter.getCurrencyCode))
  }

  implicit class JOrderImplicit(jObj: JOrder) {
    def toOrder: LimitOrder = LimitOrder(
      jObj.getType.toOrderType,
      jObj.getTradableAmount.doubleValue,
      jObj.getLimitPrice.doubleValue)
  }

}
