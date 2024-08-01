package com.josdem.fruitypedia

import com.josdem.fruitypedia.model.Beverage
import com.josdem.fruitypedia.model.Category
import com.josdem.fruitypedia.service.FruityService
import com.josdem.fruitypedia.service.RetrofitHelper
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Test
import retrofit2.Response

const val LANGUAGE = "en"
const val CATEGORY = 5
const val BEVERAGE = 85

class FruityServiceTest {
    private val fruityService: FruityService =
        RetrofitHelper.getInstance().create(FruityService::class.java)

    private suspend fun getCategories(): Response<List<Category>> {
        return fruityService.getCategories(LANGUAGE)
    }

    private suspend fun getBeverages(): Response<List<Beverage>> {
        return fruityService.getBeverages(CATEGORY)
    }

    private suspend fun getBeverage(): Response<Beverage> {
        return fruityService.getBeverage(BEVERAGE)
    }

    @Test
    fun shouldGetCategories() =
        runTest {
            val response = getCategories()
            val categories: List<Category>? = response.body()
            assertTrue(response.isSuccessful)
            assertEquals(4, categories?.size)
        }

    @Test
    fun shouldGetBeverages() =
        runTest {
            val response = getBeverages()
            val beverages: List<Beverage>? = response.body()
            assertTrue(response.isSuccessful)
            assertEquals(14, beverages?.size)
        }

    @Test
    fun shouldGetBeverage() =
        runTest {
            val response = getBeverage()
            val beverage: Beverage? = response.body()
            assertTrue(response.isSuccessful)
            assertEquals("Anti-constipation Smoothie", beverage?.name)
            assertEquals("https://storage.googleapis.com/jugoterapia/85.jpg", beverage?.image)
            assertEquals("1 Apple,1 Pear", beverage?.ingredients)
            assertEquals(
                "Start your day with apple and pear without peel getting juice as much as you can utilizing juice extractor" +
                    " or using just a blender. Apple juice contains sorbitol, " +
                    "natural sugar which helps to our body to avoid persistent constipation",
                beverage?.recipe,
            )
        }
}
