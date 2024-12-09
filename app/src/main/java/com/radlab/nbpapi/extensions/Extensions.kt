package com.radlab.nbpapi.extensions

import java.util.Locale

fun Double.isOutOfRange(referenceRate: Double, rangePercent: Double = 10.0): Boolean {
    val lowerBound = referenceRate * (1 - rangePercent / 100)
    val upperBound = referenceRate * (1 + rangePercent / 100)
    return this < lowerBound || this > upperBound
}

fun Double.formatToThreeDecimalPlaces(): String {
    return String.format(Locale.getDefault(), "%.3f", this)
}