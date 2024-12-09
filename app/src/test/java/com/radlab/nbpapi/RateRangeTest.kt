package com.radlab.nbpapi

import com.radlab.nbpapi.extensions.isOutOfRange
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class RateRangeTest {

    @Test
    fun `value within range`() {
        val referenceRate = 100.0
        val value = 105.0
        val rangePercent = 10.0
        assertFalse(value.isOutOfRange(referenceRate, rangePercent))
    }

    @Test
    fun `value below lower bound`() {
        val referenceRate = 100.0
        val value = 89.0
        val rangePercent = 10.0
        assertTrue(value.isOutOfRange(referenceRate, rangePercent))
    }

    @Test
    fun `value above upper bound`() {
        val referenceRate = 100.0
        val value = 111.0
        val rangePercent = 10.0
        assertTrue(value.isOutOfRange(referenceRate, rangePercent))
    }

    @Test
    fun `value on lower bound`() {
        val referenceRate = 100.0
        val value = 90.0
        val rangePercent = 10.0
        assertFalse(value.isOutOfRange(referenceRate, rangePercent))
    }

    @Test
    fun `value on upper bound`() {
        val referenceRate = 100.0
        val value = 110.0
        val rangePercent = 10.0
        assertFalse(value.isOutOfRange(referenceRate, rangePercent))
    }

    @Test
    fun `default range percent`() {
        val referenceRate = 100.0
        val value = 115.0
        assertTrue(value.isOutOfRange(referenceRate))
    }

    @Test
    fun `value very close to upper bound`() {
        val referenceRate = 100.0
        val value = 110.0001
        val rangePercent = 10.0
        assertTrue(value.isOutOfRange(referenceRate, rangePercent))
    }

    @Test
    fun `reference rate is zero`() {
        val referenceRate = 0.0
        val value = 0.0
        assertFalse(value.isOutOfRange(referenceRate))
    }
}