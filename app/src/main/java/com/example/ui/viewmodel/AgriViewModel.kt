package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.AppLanguage
import com.example.data.model.EquipmentItem
import com.example.data.model.LandItem
import com.example.data.model.NavigationTab
import com.example.data.model.PolicyItem
import com.example.data.model.ProduceItem
import com.example.data.model.RentalBookingRequest
import com.example.data.repository.AgriRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AgriUiState(
    val selectedTab: NavigationTab = NavigationTab.DASHBOARD,
    val language: AppLanguage = AppLanguage.HINDI, // Default to Hindi for rural accessibility, easy toggle to English
    val showSchemaDialog: Boolean = false,
    val selectedEquipmentForRent: EquipmentItem? = null,
    val showAddEquipmentDialog: Boolean = false,
    val showAddProduceDialog: Boolean = false,
    val showAddLandDialog: Boolean = false,
    val contactFarmerProduce: ProduceItem? = null,
    val contactLandowner: LandItem? = null,
    val snackbarMessage: String? = null,
    val equipmentFilter: String = "All",
    val produceFilter: String = "All",
    val searchQuery: String = ""
)

class AgriViewModel(
    private val repository: AgriRepository = AgriRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(AgriUiState())
    val uiState: StateFlow<AgriUiState> = _uiState.asStateFlow()

    val equipmentList: StateFlow<List<EquipmentItem>> = repository.equipmentList
    val produceList: StateFlow<List<ProduceItem>> = repository.produceList
    val landList: StateFlow<List<LandItem>> = repository.landList
    val policyList: StateFlow<List<PolicyItem>> = repository.policyList
    val rentalBookings: StateFlow<List<RentalBookingRequest>> = repository.rentalBookings

    fun selectTab(tab: NavigationTab) {
        _uiState.update { it.copy(selectedTab = tab, searchQuery = "") }
    }

    fun toggleLanguage() {
        _uiState.update {
            val newLang = if (it.language == AppLanguage.HINDI) AppLanguage.ENGLISH else AppLanguage.HINDI
            it.copy(language = newLang)
        }
    }

    fun setLanguage(language: AppLanguage) {
        _uiState.update { it.copy(language = language) }
    }

    fun openSchemaDialog() {
        _uiState.update { it.copy(showSchemaDialog = true) }
    }

    fun closeSchemaDialog() {
        _uiState.update { it.copy(showSchemaDialog = false) }
    }

    fun setEquipmentFilter(filter: String) {
        _uiState.update { it.copy(equipmentFilter = filter) }
    }

    fun setProduceFilter(filter: String) {
        _uiState.update { it.copy(produceFilter = filter) }
    }

    fun setSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    // Equipment Rental flow
    fun openRentDialog(item: EquipmentItem) {
        _uiState.update { it.copy(selectedEquipmentForRent = item) }
    }

    fun closeRentDialog() {
        _uiState.update { it.copy(selectedEquipmentForRent = null) }
    }

    fun submitRentRequest(
        equipment: EquipmentItem,
        farmerName: String,
        phone: String,
        startDate: String,
        days: Int,
        delivery: Boolean,
        village: String
    ) {
        val booking = RentalBookingRequest(
            id = "REQ-${System.currentTimeMillis() % 10000}",
            equipmentId = equipment.id,
            equipmentTitle = equipment.title,
            farmerName = farmerName,
            contactPhone = phone,
            startDate = startDate,
            durationDays = days,
            totalEstimatedCost = equipment.dailyRate * days + (if (delivery) 350 else 0),
            deliveryToFarm = delivery,
            farmVillage = village
        )
        repository.bookRental(booking)
        _uiState.update {
            it.copy(
                selectedEquipmentForRent = null,
                snackbarMessage = if (it.language == AppLanguage.HINDI)
                    "किराया अनुरोध दर्ज किया गया! मालिक जल्द संपर्क करेंगे।"
                else
                    "Rental request submitted! Owner will contact you shortly."
            )
        }
    }

    fun openAddEquipmentDialog() {
        _uiState.update { it.copy(showAddEquipmentDialog = true) }
    }

    fun closeAddEquipmentDialog() {
        _uiState.update { it.copy(showAddEquipmentDialog = false) }
    }

    fun addEquipmentListing(
        title: String,
        category: String,
        horsepower: String,
        dailyRate: Int,
        hourlyRate: Int,
        ownerName: String,
        ownerPhone: String,
        location: String,
        specs: String
    ) {
        val newEquipment = EquipmentItem(
            id = "EQ-${System.currentTimeMillis() % 10000}",
            title = title,
            titleHi = title,
            category = category,
            categoryHi = category,
            horsepower = horsepower,
            dailyRate = dailyRate,
            hourlyRate = hourlyRate,
            ownerName = ownerName,
            ownerPhone = ownerPhone,
            location = location,
            isAvailable = true,
            specs = specs,
            specsHi = specs
        )
        repository.addEquipment(newEquipment)
        _uiState.update {
            it.copy(
                showAddEquipmentDialog = false,
                snackbarMessage = if (it.language == AppLanguage.HINDI)
                    "उपकरण सफलतापूर्वक सूचीबद्ध हो गया!"
                else
                    "Equipment listed successfully!"
            )
        }
    }

    // Direct Market (Broker Hatao) flow
    fun openContactFarmer(produce: ProduceItem) {
        _uiState.update { it.copy(contactFarmerProduce = produce) }
    }

    fun closeContactFarmer() {
        _uiState.update { it.copy(contactFarmerProduce = null) }
    }

    fun openAddProduceDialog() {
        _uiState.update { it.copy(showAddProduceDialog = true) }
    }

    fun closeAddProduceDialog() {
        _uiState.update { it.copy(showAddProduceDialog = false) }
    }

    fun addProduceListing(
        cropName: String,
        variety: String,
        category: String,
        pricePerQuintal: Int,
        quantity: Double,
        qualityGrade: String,
        farmerName: String,
        phone: String,
        village: String,
        district: String,
        state: String
    ) {
        val newProduce = ProduceItem(
            id = "PR-${System.currentTimeMillis() % 10000}",
            cropName = cropName,
            cropNameHi = cropName,
            variety = variety,
            varietyHi = variety,
            category = category,
            categoryHi = category,
            pricePerQuintal = pricePerQuintal,
            mandiPricePerQuintal = (pricePerQuintal * 0.88).toInt(),
            quantityQuintals = quantity,
            qualityGrade = qualityGrade,
            farmerName = farmerName,
            farmerPhone = phone,
            village = village,
            district = district,
            state = state,
            harvestDate = "Current Harvest"
        )
        repository.addProduce(newProduce)
        _uiState.update {
            it.copy(
                showAddProduceDialog = false,
                snackbarMessage = if (it.language == AppLanguage.HINDI)
                    "फसल सीधी बिक्री हेतु सूचीबद्ध हो गई! (दलाल मुक्त)"
                else
                    "Crop listed for direct sale! (Zero Middleman)"
            )
        }
    }

    // Land Lease flow
    fun openContactLandowner(land: LandItem) {
        _uiState.update { it.copy(contactLandowner = land) }
    }

    fun closeContactLandowner() {
        _uiState.update { it.copy(contactLandowner = null) }
    }

    fun openAddLandDialog() {
        _uiState.update { it.copy(showAddLandDialog = true) }
    }

    fun closeAddLandDialog() {
        _uiState.update { it.copy(showAddLandDialog = false) }
    }

    fun addLandListing(
        title: String,
        acreage: Double,
        soilType: String,
        irrigation: String,
        pricePerYear: Int,
        tenureYears: Int,
        ownerName: String,
        phone: String,
        village: String,
        district: String,
        state: String,
        crops: List<String>
    ) {
        val newLand = LandItem(
            id = "LD-${System.currentTimeMillis() % 10000}",
            title = title,
            titleHi = title,
            acreage = acreage,
            soilType = soilType,
            soilTypeHi = soilType,
            irrigationSource = irrigation,
            irrigationSourceHi = irrigation,
            leasePricePerYear = pricePerYear,
            tenureYears = tenureYears,
            ownerName = ownerName,
            ownerPhone = phone,
            village = village,
            district = district,
            state = state,
            suitableCrops = crops
        )
        repository.addLand(newLand)
        _uiState.update {
            it.copy(
                showAddLandDialog = false,
                snackbarMessage = if (it.language == AppLanguage.HINDI)
                    "कृषि भूमि पट्टे हेतु सूचीबद्ध हो गई!"
                else
                    "Agricultural land listed for lease!"
            )
        }
    }

    fun clearSnackbar() {
        _uiState.update { it.copy(snackbarMessage = null) }
    }
}
