package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Agriculture
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Landscape
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.outlined.AccountBalance
import androidx.compose.material.icons.outlined.Agriculture
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Landscape
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.model.AppLanguage
import com.example.data.model.NavigationTab
import com.example.ui.components.LanguageToggleButton
import com.example.ui.screens.AddEquipmentDialog
import com.example.ui.screens.AddLandDialog
import com.example.ui.screens.AddProduceDialog
import com.example.ui.screens.DashboardScreen
import com.example.ui.screens.DirectMarketScreen
import com.example.ui.screens.EquipmentScreen
import com.example.ui.screens.LandLeaseScreen
import com.example.ui.screens.PolicyHubScreen
import com.example.ui.screens.RequestToRentDialog
import com.example.ui.screens.SchemaViewerDialog
import com.example.ui.theme.HarvestGold
import com.example.ui.theme.KisanTheme
import com.example.ui.viewmodel.AgriViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KisanTheme {
                KisanApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KisanApp(viewModel: AgriViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val equipmentList by viewModel.equipmentList.collectAsStateWithLifecycle()
    val produceList by viewModel.produceList.collectAsStateWithLifecycle()
    val landList by viewModel.landList.collectAsStateWithLifecycle()
    val policyList by viewModel.policyList.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }
    val isHindi = uiState.language == AppLanguage.HINDI

    LaunchedEffect(uiState.snackbarMessage) {
        uiState.snackbarMessage?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            viewModel.clearSnackbar()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .clip(CircleShape)
                                .background(HarvestGold),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Agriculture,
                                contentDescription = null,
                                tint = Color.Black,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (isHindi) "किसान सेतु" else "KisanSetu",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                actions = {
                    // Language Switcher Toggle
                    LanguageToggleButton(
                        currentLanguage = uiState.language,
                        onToggle = { viewModel.toggleLanguage() }
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    // JSON Database Schema Action
                    IconButton(
                        onClick = { viewModel.openSchemaDialog() },
                        modifier = Modifier.testTag("open_schema_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Code,
                            contentDescription = "JSON Database Schema",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        bottomBar = {
            KisanBottomNavigationBar(
                selectedTab = uiState.selectedTab,
                onTabSelected = { viewModel.selectTab(it) },
                isHindi = isHindi
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            AnimatedContent(
                targetState = uiState.selectedTab,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "TabContent"
            ) { targetTab ->
                when (targetTab) {
                    NavigationTab.DASHBOARD -> DashboardScreen(
                        language = uiState.language,
                        onNavigateTab = { viewModel.selectTab(it) },
                        onOpenSchema = { viewModel.openSchemaDialog() }
                    )

                    NavigationTab.EQUIPMENT -> EquipmentScreen(
                        equipmentList = equipmentList,
                        language = uiState.language,
                        filter = uiState.equipmentFilter,
                        onFilterChange = { viewModel.setEquipmentFilter(it) },
                        searchQuery = uiState.searchQuery,
                        onSearchChange = { viewModel.setSearchQuery(it) },
                        onRequestRent = { viewModel.openRentDialog(it) },
                        onOpenAddEquipment = { viewModel.openAddEquipmentDialog() }
                    )

                    NavigationTab.DIRECT_MARKET -> DirectMarketScreen(
                        produceList = produceList,
                        language = uiState.language,
                        filter = uiState.produceFilter,
                        onFilterChange = { viewModel.setProduceFilter(it) },
                        searchQuery = uiState.searchQuery,
                        onSearchChange = { viewModel.setSearchQuery(it) },
                        onOpenAddProduce = { viewModel.openAddProduceDialog() }
                    )

                    NavigationTab.LAND_LEASE -> LandLeaseScreen(
                        landList = landList,
                        language = uiState.language,
                        searchQuery = uiState.searchQuery,
                        onSearchChange = { viewModel.setSearchQuery(it) },
                        onOpenAddLand = { viewModel.openAddLandDialog() }
                    )

                    NavigationTab.GOVERNMENT_POLICIES -> PolicyHubScreen(
                        policyList = policyList,
                        language = uiState.language,
                        filter = "All",
                        onFilterChange = {},
                        searchQuery = uiState.searchQuery,
                        onSearchChange = { viewModel.setSearchQuery(it) }
                    )
                }
            }
        }
    }

    // Dialogs
    if (uiState.showSchemaDialog) {
        SchemaViewerDialog(
            onDismiss = { viewModel.closeSchemaDialog() },
            isHindi = isHindi
        )
    }

    uiState.selectedEquipmentForRent?.let { equipment ->
        RequestToRentDialog(
            equipment = equipment,
            isHindi = isHindi,
            onDismiss = { viewModel.closeRentDialog() },
            onSubmit = { farmerName, phone, startDate, days, delivery, village ->
                viewModel.submitRentRequest(equipment, farmerName, phone, startDate, days, delivery, village)
            }
        )
    }

    if (uiState.showAddEquipmentDialog) {
        AddEquipmentDialog(
            isHindi = isHindi,
            onDismiss = { viewModel.closeAddEquipmentDialog() },
            onAdd = { title, category, horsepower, dailyRate, hourlyRate, ownerName, ownerPhone, location, specs ->
                viewModel.addEquipmentListing(title, category, horsepower, dailyRate, hourlyRate, ownerName, ownerPhone, location, specs)
            }
        )
    }

    if (uiState.showAddProduceDialog) {
        AddProduceDialog(
            isHindi = isHindi,
            onDismiss = { viewModel.closeAddProduceDialog() },
            onAdd = { cropName, variety, category, pricePerQuintal, quantity, qualityGrade, farmerName, phone, village, district, state ->
                viewModel.addProduceListing(cropName, variety, category, pricePerQuintal, quantity, qualityGrade, farmerName, phone, village, district, state)
            }
        )
    }

    if (uiState.showAddLandDialog) {
        AddLandDialog(
            isHindi = isHindi,
            onDismiss = { viewModel.closeAddLandDialog() },
            onAdd = { title, acreage, soilType, irrigation, pricePerYear, tenureYears, ownerName, phone, village, district, state, crops ->
                viewModel.addLandListing(title, acreage, soilType, irrigation, pricePerYear, tenureYears, ownerName, phone, village, district, state, crops)
            }
        )
    }
}

@Composable
fun KisanBottomNavigationBar(
    selectedTab: NavigationTab,
    onTabSelected: (NavigationTab) -> Unit,
    isHindi: Boolean
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp
    ) {
        // 1. Dashboard
        NavigationBarItem(
            selected = selectedTab == NavigationTab.DASHBOARD,
            onClick = { onTabSelected(NavigationTab.DASHBOARD) },
            icon = {
                Icon(
                    imageVector = if (selectedTab == NavigationTab.DASHBOARD) Icons.Default.Home else Icons.Outlined.Home,
                    contentDescription = "Dashboard"
                )
            },
            label = { Text(if (isHindi) "होम" else "Home", fontSize = 10.sp, fontWeight = FontWeight.Medium) },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                selectedIconColor = MaterialTheme.colorScheme.primary,
                selectedTextColor = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.testTag("nav_tab_dashboard")
        )

        // 2. Equipment Rental
        NavigationBarItem(
            selected = selectedTab == NavigationTab.EQUIPMENT,
            onClick = { onTabSelected(NavigationTab.EQUIPMENT) },
            icon = {
                Icon(
                    imageVector = if (selectedTab == NavigationTab.EQUIPMENT) Icons.Default.Agriculture else Icons.Outlined.Agriculture,
                    contentDescription = "Equipment"
                )
            },
            label = { Text(if (isHindi) "उपकरण" else "Rentals", fontSize = 10.sp, fontWeight = FontWeight.Medium) },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                selectedIconColor = MaterialTheme.colorScheme.primary,
                selectedTextColor = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.testTag("nav_tab_equipment")
        )

        // 3. Direct Market
        NavigationBarItem(
            selected = selectedTab == NavigationTab.DIRECT_MARKET,
            onClick = { onTabSelected(NavigationTab.DIRECT_MARKET) },
            icon = {
                Icon(
                    imageVector = if (selectedTab == NavigationTab.DIRECT_MARKET) Icons.Default.ShoppingBag else Icons.Outlined.ShoppingBag,
                    contentDescription = "Market"
                )
            },
            label = { Text(if (isHindi) "सीधा बाज़ार" else "Market", fontSize = 10.sp, fontWeight = FontWeight.Medium) },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                selectedIconColor = MaterialTheme.colorScheme.primary,
                selectedTextColor = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.testTag("nav_tab_market")
        )

        // 4. Land Lease
        NavigationBarItem(
            selected = selectedTab == NavigationTab.LAND_LEASE,
            onClick = { onTabSelected(NavigationTab.LAND_LEASE) },
            icon = {
                Icon(
                    imageVector = if (selectedTab == NavigationTab.LAND_LEASE) Icons.Default.Landscape else Icons.Outlined.Landscape,
                    contentDescription = "Land Lease"
                )
            },
            label = { Text(if (isHindi) "भू-पट्टा" else "Land", fontSize = 10.sp, fontWeight = FontWeight.Medium) },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                selectedIconColor = MaterialTheme.colorScheme.primary,
                selectedTextColor = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.testTag("nav_tab_land")
        )

        // 5. Policies
        NavigationBarItem(
            selected = selectedTab == NavigationTab.GOVERNMENT_POLICIES,
            onClick = { onTabSelected(NavigationTab.GOVERNMENT_POLICIES) },
            icon = {
                Icon(
                    imageVector = if (selectedTab == NavigationTab.GOVERNMENT_POLICIES) Icons.Default.AccountBalance else Icons.Outlined.AccountBalance,
                    contentDescription = "Policies"
                )
            },
            label = { Text(if (isHindi) "योजनाएं" else "Policies", fontSize = 10.sp, fontWeight = FontWeight.Medium) },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                selectedIconColor = MaterialTheme.colorScheme.primary,
                selectedTextColor = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.testTag("nav_tab_policies")
        )
    }
}
