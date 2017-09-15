package autocoin.system

import java.util.Date
import scala.collection.immutable._

trait OrderSeq extends Iterable[Order] {
  def orders: Seq[Order]

  override def iterator: Iterator[Order] = orders.iterator

  override def size: Int = orders.length

  def orderType: OrderType.OrderType
}

case class AskOrderSeq private(override val orders: Seq[AskOrder]) extends OrderSeq {
  val orderType: OrderType.OrderType = OrderType.ASK
}

object AskOrderSeq {
  def apply(orders: Seq[AskOrder], sort: Boolean = true): AskOrderSeq =
    if (sort) AskOrderSeq(orders.sortBy(_.price)) else AskOrderSeq(orders)
}

case class BidOrderSeq private(override val orders: Seq[BidOrder]) extends OrderSeq {
  val orderType: OrderType.OrderType = OrderType.BID
}

object BidOrderSeq {
  def apply(orders: Seq[BidOrder], sort: Boolean = true): BidOrderSeq =
    if (sort) BidOrderSeq(orders.sortBy(-_.price)) else BidOrderSeq(orders)
}

trait OrderBook extends CurrencyPairFeature {
  def askOrders: AskOrderSeq

  def bidOrders: BidOrderSeq

  def timeStamp: Date

  def filterRange(range: Double): OrderBook
}

case class LimitOrderBook(askOrders: AskOrderSeq, bidOrders: BidOrderSeq,
                          timeStamp: Date, currencyPair: CurrencyPair)
  extends OrderBook {
  def filterRange(range: Double): LimitOrderBook = LimitOrderBook(
    AskOrderSeq(askOrders.orders.filter(_.price < askOrders.head.price * (1 + range)), sort = false),
    BidOrderSeq(bidOrders.orders.filter(_.price > bidOrders.head.price * (1 - range)), sort = false),
    timeStamp, currencyPair)
}


trait Order {
  def tradableAmount: Double

  def price: Double
}

trait AskOrder extends Order

trait BidOrder extends Order

trait LimitOrder extends Order

case class LimitAskOrder(tradableAmount: Double,
                         limitPrice: Double) extends LimitOrder with AskOrder {
  def price: Double = limitPrice
}

case class LimitBidOrder(tradableAmount: Double,
                         limitPrice: Double) extends LimitOrder with BidOrder {
  def price: Double = limitPrice
}

trait CurrencyPairFeature {
  def currencyPair: CurrencyPair
}

object OrderType extends Enumeration {
  type OrderType = Value
  val BID, ASK = Value
}