package com.dmabram15.lenghtofservice.model.utils.matchers

import com.dmabram15.lenghtofservice.model.Period
import com.dmabram15.lenghtofservice.model.listeners.OnMatcherEventListener
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Test

class PeriodMatcherTest : TestCase() {

    private lateinit var listener: OnMatcherEventListener
    private var resultOfWork : Boolean? = null
    private val periods = listOf(
        Period(0, 2000, 6000, 1f)
    )

    @Before
    override fun setUp() {
        super.setUp()
        listener = object : OnMatcherEventListener {
            override fun hasBeginCross(crossedPeriod: Period, date: Long) {
                resultOfWork = false
            }

            override fun hasEndCross(crossedPeriod: Period, date: Long) {
                resultOfWork = false
            }

            override fun hasIncludingCross(crossedPeriod: Period) {
                resultOfWork = false
            }

            override fun success() {
                resultOfWork = true
            }
        }
    }

    @Test
    fun testMatchPeriod_hasBeginCross_False() {
        //Arrange
        val period = Period(1, 3000, 8000, 1f)
        //Act
        PeriodMatcher(listener).matchPeriod(period, periods)

        //Assert
        assertFalse(resultOfWork!!)
    }

    @Test
    fun testMatchPeriod_hasEndCross_False() {
        //Arrange
        val period = Period(1, 1000, 3000, 1f)
        //Act
        PeriodMatcher(listener).matchPeriod(period, periods)

        //Assert
        assertFalse(resultOfWork!!)
    }

    @Test
    fun testMatchPeriod_hasIncludingCross_False() {
        //Arrange
        val period = Period(1, 3000, 5000, 1f)
        //Act
        PeriodMatcher(listener).matchPeriod(period, periods)

        //Assert
        assertFalse(resultOfWork!!)
    }

    @Test
    fun testMatchPeriod_Success_True() {
        //Arrange
        val period = Period(1, 1000, 1500, 1f)
        //Act
        PeriodMatcher(listener).matchPeriod(period, periods)

        //Assert
        assertTrue(resultOfWork!!)
    }

    @After
    override fun tearDown(){
        super.tearDown()
        resultOfWork = null
    }
}