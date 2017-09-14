package autocoin.system

import org.knowm.xchange.currency.{Currency => JCurrency, CurrencyPair => JCurrencyPair}

import scala.collection.immutable._

sealed trait Currency {
  def commonCode: String

  def toJCurrency: JCurrency

  def name: String = toJCurrency.getDisplayName
}

case class DigitalCurrency private(commonCode: String) extends Currency {
  lazy val toJCurrency: JCurrency = commonCode match {
    case "BTC" => JCurrency.BTC
    case "ETH" => JCurrency.ETH
    case "MSC" => JCurrency.MSC
    case "DOGE" => JCurrency.DOGE
  }
}

case class RealCurrency private(commonCode: String) extends Currency {
  lazy val toJCurrency: JCurrency = commonCode match {
    case "USD" => JCurrency.USD
  }
}

object Currency {
  val USD = RealCurrency("USD")
  val BTC = DigitalCurrency("BTC")
  val ETH = DigitalCurrency("ETH")
  val MSC = DigitalCurrency("MSC")
  val DOGE = DigitalCurrency("DOGE")

  val currencies = Vector(USD, BTC, ETH, MSC, DOGE)
  val realCurrencies: Vector[RealCurrency] = currencies.collect { case c: RealCurrency => c }
  val digitalCurrencies: Vector[DigitalCurrency] = currencies.collect { case c: DigitalCurrency => c }
  val currencyMap: Map[String, Currency] = currencies.map(c => c.commonCode -> c).toMap
  val realCurrencyMap: Map[String, RealCurrency] = realCurrencies.map(c => c.commonCode -> c).toMap
  val digitalCurrencyMap: Map[String, DigitalCurrency] = digitalCurrencies.map(c => c.commonCode -> c).toMap

  def apply(commonCode: String): Currency = currencyMap(commonCode)

  def digital(commonCode: String): DigitalCurrency = digitalCurrencyMap(commonCode)

  def real(commonCode: String): RealCurrency = realCurrencyMap(commonCode)
}

case class CurrencyPair private(base: Currency, counter: Currency) {
  lazy val toJCurrencyPair: JCurrencyPair =
    new JCurrencyPair(base.toJCurrency, counter.toJCurrency)
}

object CurrencyPair {
  val currencyPairMap: Map[(Currency, Currency), CurrencyPair] = {
    val cross = for (x <- Currency.currencies; y <- Currency.currencies) yield (x, y) -> new CurrencyPair(x, y)
    cross.toMap
  }
  val digitalCurrencyPairMap: Map[(DigitalCurrency, DigitalCurrency), CurrencyPair] = currencyPairMap.collect {
    case ((base: DigitalCurrency, counter: DigitalCurrency), ccyPair) => ((base, counter), ccyPair)
  }
  val realCurrencyPairMap: Map[(RealCurrency, RealCurrency), CurrencyPair] = currencyPairMap.collect {
    case ((base: RealCurrency, counter: RealCurrency), ccyPair) => ((base, counter), ccyPair)
  }

  def apply(base: Currency, counter: Currency): CurrencyPair = currencyPairMap(base, counter)
}