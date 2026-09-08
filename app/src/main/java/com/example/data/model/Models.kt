package com.example.data.model

import androidx.annotation.DrawableRes

enum class AppLanguage {
    ENGLISH,
    HINDI
}

enum class NavigationTab {
    DASHBOARD,
    EQUIPMENT,
    DIRECT_MARKET,
    LAND_LEASE,
    GOVERNMENT_POLICIES
}

data class EquipmentItem(
    val id: String,
    val title: String,
    val titleHi: String,
    val category: String,
    val categoryHi: String,
    val horsepower: String,
    val dailyRate: Int,
    val hourlyRate: Int,
    val ownerName: String,
    val ownerPhone: String,
    val location: String,
    val isAvailable: Boolean = true,
    val specs: String,
    val specsHi: String,
    @DrawableRes val imageRes: Int? = null
)

data class ProduceItem(
    val id: String,
    val cropName: String,
    val cropNameHi: String,
    val variety: String,
    val varietyHi: String,
    val category: String,
    val categoryHi: String,
    val pricePerQuintal: Int,
    val mandiPricePerQuintal: Int,
    val quantityQuintals: Double,
    val qualityGrade: String, // "Grade A+", "Export Quality", "Standard"
    val farmerName: String,
    val farmerPhone: String,
    val village: String,
    val district: String,
    val state: String,
    val harvestDate: String,
    @DrawableRes val imageRes: Int? = null
) {
    val brokerSavingsPct: Int
        get() {
            return if (mandiPricePerQuintal > 0) {
                val diff = pricePerQuintal - mandiPricePerQuintal
                val saving = ((pricePerQuintal * 0.12).toInt()) // approximate broker cut bypassed
                saving
            } else 250
        }
}

data class LandItem(
    val id: String,
    val title: String,
    val titleHi: String,
    val acreage: Double,
    val soilType: String,
    val soilTypeHi: String,
    val irrigationSource: String,
    val irrigationSourceHi: String,
    val leasePricePerYear: Int,
    val tenureYears: Int,
    val ownerName: String,
    val ownerPhone: String,
    val village: String,
    val district: String,
    val state: String,
    val suitableCrops: List<String>,
    @DrawableRes val imageRes: Int? = null
)

data class PolicyItem(
    val id: String,
    val schemeCode: String,
    val title: String,
    val titleHi: String,
    val ministry: String,
    val ministryHi: String,
    val subsidyBenefit: String,
    val subsidyBenefitHi: String,
    val category: String,
    val categoryHi: String,
    val simplifiedSummaryEn: String,
    val simplifiedSummaryHi: String,
    val eligibilityEn: List<String>,
    val eligibilityHi: List<String>,
    val documentsEn: List<String>,
    val documentsHi: List<String>,
    val officialPortalUrl: String,
    val tollFreeNumber: String = "1800-180-1551"
)

data class RentalBookingRequest(
    val id: String,
    val equipmentId: String,
    val equipmentTitle: String,
    val farmerName: String,
    val contactPhone: String,
    val startDate: String,
    val durationDays: Int,
    val totalEstimatedCost: Int,
    val deliveryToFarm: Boolean,
    val farmVillage: String,
    val status: String = "Submitted"
)
