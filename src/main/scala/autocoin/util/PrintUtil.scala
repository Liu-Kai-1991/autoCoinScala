package autocoin.util

import math.max
import scala.Console._

object PrintUtil {
  private val headlineLength = 100
  def printHeadline(headline: String, charactor: Char = '=', color: String = RED): Unit = {
    val lengthOfSymbol = max(headlineLength - headline.length - 2, 0)
    val left: Int = lengthOfSymbol / 2
    val right = lengthOfSymbol - left
    println(s"$RESET$color${String.valueOf(Array.fill[Char](left)(charactor))} $headline " +
      s"${String.valueOf(Array.fill[Char](right)(charactor))}$RESET")
  }
}

