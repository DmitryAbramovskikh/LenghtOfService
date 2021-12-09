package com.dmabram15.lenghtofservice.model.usecases

import com.dmabram15.lenghtofservice.model.Period
import junit.framework.TestCase
import org.junit.Test

class ComputeLengthPeriodTest : TestCase() {

    private val millisPerDay = 86400000L

    @Test
    fun testMultipleLengthExecute() {
        //Arrange
        val periods = listOf(
            Period(1, millisPerDay * 1, millisPerDay * 2, 2f),
            Period(2, millisPerDay * 3, millisPerDay * 4, 2f),
            Period(3, millisPerDay * 5, millisPerDay * 6, 2f),
            Period(4, millisPerDay * 7, millisPerDay * 8, 2f),
            Period(5, millisPerDay * 9, millisPerDay * 10, 2f)
        )
        val sut = ComputeFullLengthWithMultiplierUseCase()
        //Act
        val length = sut.execute(periods)
        //Assert
        assertEquals(millisPerDay * 10, length)
    }

    @Test
    fun testLengthExecute() {
        //Arrange
        val periods = listOf(
            Period(1, millisPerDay * 1, millisPerDay * 2, 2f),
            Period(2, millisPerDay * 3, millisPerDay * 4, 2f),
            Period(3, millisPerDay * 5, millisPerDay * 6, 2f),
            Period(4, millisPerDay * 7, millisPerDay * 8, 2f),
            Period(5, millisPerDay * 9, millisPerDay * 10, 2f)
        )
        val sut = ComputeFullLengthWithoutMultiplierUseCase()
        //Act
        val length = sut.execute(periods)
        //Assert
        assertEquals(millisPerDay * 5, length)
    }
}