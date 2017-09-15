package autocoin.stat

import autocoin.system.{AskOrderSeq, Order, OrderSeq, OrderType}
import autocoin.util.ToFormattedStringFeature

import scala.collection.immutable.Seq

case class Summary(bestPrice: Double, bestVol: Double,
                   bestThousandth: Seq[(Double /* from */, Double /* to */, Double /* vol */)],
                   orderType: OrderType.OrderType) extends ToFormattedStringFeature {
  def toFormattedString: String =
    (Seq(s"orderType = $orderType", s"bestPrice = $bestPrice, bestVol = $bestVol") ++
      bestThousandth.zipWithIndex.map{
        case ((from, to, vol), ith) =>
          s"best ${ith + 1} thousandth: from = $from, to = $to, volume = $vol"
      }).mkString("\n")
}

object Summary{
  implicit class OrderSeqImplicit(val x: OrderSeq) extends AnyVal {
    def summary(maxThousandth: Int = 5): Summary = {
      val (bestPrice, bestVol) = (x.orders.head.price, x.orders.head.tradableAmount)
      def loop(thousandth: Int, orders: Seq[Order], acc: Vector[(Double, Double, Double)])
      : Seq[(Double, Double, Double)] =
        if (thousandth > maxThousandth) acc else {
          val batch = x.orderType match {
            case OrderType.ASK =>
              val threshold = bestPrice * (1 + thousandth * 0.001)
              orders.takeWhile(order => order.price < threshold)
            case OrderType.BID =>
              val threshold = bestPrice * (1 - thousandth * 0.001)
              orders.takeWhile(order => order.price > threshold)
          }
          if (batch.isEmpty) acc else
            loop(thousandth + 1, orders.drop(batch.size),
              acc :+ (batch.head.price, batch.last.price, batch.map(_.tradableAmount).sum))
      }
      val bestThousandth = loop(1, x.orders, Vector())
      Summary(bestPrice, bestVol, bestThousandth, x.orderType)
    }
  }
}