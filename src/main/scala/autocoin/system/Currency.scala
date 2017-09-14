package autocoin.system

import org.knowm.xchange.currency.{Currency => JCurrency, CurrencyPair => JCurrencyPair}

case class Currency(commonCode: String) {
  lazy val toJCurrency: JCurrency = commonCode match {
    case "USD" => JCurrency.USD
    case "BTC" => JCurrency.BTC
    case "ETH" => JCurrency.ETH
  }
}

object Currency {
  val USD = Currency("USD")
  val BTC = Currency("BTC")
}

case class CurrencyPair(base: Currency, counter: Currency) {
  lazy val toJCurrencyPair: JCurrencyPair =
    new JCurrencyPair(base.toJCurrency, counter.toJCurrency)
}

object CurrencyPair {
  val BTC_USD = CurrencyPair(Currency.BTC, Currency.USD)
}