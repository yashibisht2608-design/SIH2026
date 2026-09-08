package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.model.AppLanguage
import com.example.data.model.NavigationTab
import com.example.data.schema.JsonSchemaProvider
import com.example.ui.viewmodel.AgriViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("KisanSetu", appName)
  }

  @Test
  fun `viewModel language toggle switches between Hindi and English`() {
    val viewModel = AgriViewModel()
    assertEquals(AppLanguage.HINDI, viewModel.uiState.value.language)
    viewModel.toggleLanguage()
    assertEquals(AppLanguage.ENGLISH, viewModel.uiState.value.language)
    viewModel.toggleLanguage()
    assertEquals(AppLanguage.HINDI, viewModel.uiState.value.language)
  }

  @Test
  fun `viewModel tab navigation changes selected tab`() {
    val viewModel = AgriViewModel()
    assertEquals(NavigationTab.DASHBOARD, viewModel.uiState.value.selectedTab)
    viewModel.selectTab(NavigationTab.EQUIPMENT)
    assertEquals(NavigationTab.EQUIPMENT, viewModel.uiState.value.selectedTab)
    viewModel.selectTab(NavigationTab.DIRECT_MARKET)
    assertEquals(NavigationTab.DIRECT_MARKET, viewModel.uiState.value.selectedTab)
    viewModel.selectTab(NavigationTab.LAND_LEASE)
    assertEquals(NavigationTab.LAND_LEASE, viewModel.uiState.value.selectedTab)
    viewModel.selectTab(NavigationTab.GOVERNMENT_POLICIES)
    assertEquals(NavigationTab.GOVERNMENT_POLICIES, viewModel.uiState.value.selectedTab)
  }

  @Test
  fun `equipment rental booking adds request`() {
    val viewModel = AgriViewModel()
    val initialBookingsCount = viewModel.rentalBookings.value.size
    val firstEquipment = viewModel.equipmentList.value.first()
    viewModel.submitRentRequest(
      equipment = firstEquipment,
      farmerName = "Suresh Patel",
      phone = "9876543210",
      startDate = "2026-04-01",
      days = 3,
      delivery = true,
      village = "Bhopal Rural"
    )
    assertEquals(initialBookingsCount + 1, viewModel.rentalBookings.value.size)
    assertNotNull(viewModel.uiState.value.snackbarMessage)
  }

  @Test
  fun `json database schema contains all five core entities`() {
    val schema = JsonSchemaProvider.FULL_DATABASE_SCHEMA_JSON
    assertTrue(schema.contains("Users"))
    assertTrue(schema.contains("EquipmentListings"))
    assertTrue(schema.contains("ProduceListings"))
    assertTrue(schema.contains("LandListings"))
    assertTrue(schema.contains("GovernmentPolicies"))
  }
}
