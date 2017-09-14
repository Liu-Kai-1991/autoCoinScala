package autocoin.system

import java.util.Date
import autocoin.system.OrderType.OrderType

trait OrderBook extends CurrencyPairFeature {
  def askOrders: Seq[Order]

  def bidOrders: Seq[Order]

  def timeStamp: Date

  def filterRange(range: Double): OrderBook
}

case class LimitOrderBook(askOrders: Vector[LimitOrder], bidOrders: Vector[LimitOrder],
                          timeStamp: Date, currencyPair: CurrencyPair)
  extends OrderBook {
  def filterRange(range: Double): LimitOrderBook = LimitOrderBook(
    askOrders.filter(_.limitPrice < askOrders.minBy(_.price).limitPrice * (1 + range)),
    bidOrders.filter(_.limitPrice > bidOrders.maxBy(_.price).limitPrice * (1 - range)),
    timeStamp, currencyPair)
}

trait Order {
  def orderType: OrderType

  def tradableAmount: Double

  def price: Double
}

case class LimitOrder(orderType: OrderType,
                      tradableAmount: Double,
                      limitPrice: Double) extends Order {
  def price: Double = limitPrice
}

object OrderType extends Enumeration {
  type OrderType = Value
  val BID, ASK, EXIT_ASK, EXIT_BID = Value
}

trait CurrencyPairFeature {
  def currencyPair: CurrencyPair
}
