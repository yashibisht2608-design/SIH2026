package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Landscape
import androidx.compose.material.icons.filled.Opacity
import androidx.compose.material.icons.filled.SquareFoot
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.AppLanguage
import com.example.data.model.LandItem
import com.example.ui.components.DirectContactDialog
import com.example.ui.components.RuralSearchBar
import com.example.ui.theme.ClaySoil
import com.example.ui.theme.FreshLeaf
import com.example.ui.theme.HarvestGold

@Composable
fun LandLeaseScreen(
    landList: List<LandItem>,
    language: AppLanguage,
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    onOpenAddLand: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isHindi = language == AppLanguage.HINDI
    var contactLandItem by remember { mutableStateOf<LandItem?>(null) }

    val filteredList = landList.filter { item ->
        if (searchQuery.isBlank()) true
        else item.title.contains(searchQuery, ignoreCase = true) ||
                item.titleHi.contains(searchQuery, ignoreCase = true) ||
                item.soilType.contains(searchQuery, ignoreCase = true) ||
                item.district.contains(searchQuery, ignoreCase = true) ||
                item.state.contains(searchQuery, ignoreCase = true) ||
                item.village.contains(searchQuery, ignoreCase = true)
    }

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .testTag("land_lease_screen")
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = if (isHindi) "कृषि भूमि पट्टा प्रणाली" else "Landlease System",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = if (isHindi) "उपजाऊ खेती योग्य जमीन लीज पर लें" else "Verified agricultural land for seasonal/long-term lease",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }

                    OutlinedButton(
                        onClick = onOpenAddLand,
                        modifier = Modifier.testTag("list_land_button"),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = if (isHindi) "जमीन जोड़ें" else "List Land", fontSize = 12.sp)
                    }
                }
            }

            // Search Bar
            item {
                RuralSearchBar(
                    query = searchQuery,
                    onQueryChange = onSearchChange,
                    placeholderText = if (isHindi) "जिला, राज्य या मिट्टी का प्रकार खोजें (उदा. करनाल, काली मिट्टी)..." else "Search district, state, soil type (Alluvial, Black)..."
                )
            }

            // Trust highlights
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.secondary),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Landscape,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = if (isHindi) "पारदर्शी पट्टा अनुबंध व भू-अभिलेख" else "Transparent Tenancy Terms & Soil Health",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                            Text(
                                text = if (isHindi)
                                    "पानी की व्यवस्था (नहर/ट्यूबवेल) एवं मिट्टी की किस्म का पूर्ण विवरण।"
                                else
                                    "Clear water rights (canal/borewell), soil suitability and legal lease duration.",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.85f)
                            )
                        }
                    }
                }
            }

            // List count
            item {
                Text(
                    text = if (isHindi) "${filteredList.size} कृषि भूखंड पट्टे हेतु उपलब्ध" else "${filteredList.size} Agricultural Land Parcels Available",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Land Listings
            items(filteredList, key = { it.id }) { item ->
                LandListingCard(
                    item = item,
                    isHindi = isHindi,
                    onContactOwner = { contactLandItem = item }
                )
            }

            item {
                Spacer(modifier = Modifier.height(30.dp))
            }
        }

        // Contact Landowner Dialog
        contactLandItem?.let { item ->
            DirectContactDialog(
                contactName = item.ownerName,
                contactRole = if (isHindi) "भूमि स्वामी (Landowner)" else "Agricultural Landowner",
                phoneNumber = item.ownerPhone,
                locationText = "${item.village}, ${item.district}, ${item.state}",
                itemDescription = "${if (isHindi) item.titleHi else item.title} (${item.acreage} एकड़ @ ₹${item.leasePricePerYear}/वर्ष)",
                isHindi = isHindi,
                onDismiss = { contactLandItem = null }
            )
        }
    }
}

@Composable
fun LandListingCard(
    item: LandItem,
    isHindi: Boolean,
    onContactOwner: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("land_card_${item.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Optional image
            if (item.imageRes != null) {
                Image(
                    painter = painterResource(id = item.imageRes),
                    contentDescription = item.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(10.dp))
            }

            // Header Row: Title & Acreage
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = if (isHindi) item.titleHi else item.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "📍 ${item.village}, ${item.district} (${item.state})",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.outline
                    )
                }

                Surface(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.SquareFoot,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp),
                            tint = MaterialTheme.colorScheme.secondary
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${item.acreage} एकड़",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Badges: Soil Type & Irrigation
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Surface(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text(
                            text = if (isHindi) "मिट्टी का प्रकार" else "Soil Type",
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.outline
                        )
                        Text(
                            text = if (isHindi) item.soilTypeHi else item.soilType,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f),
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text(
                            text = if (isHindi) "सिंचाई सुविधा" else "Irrigation Source",
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.outline
                        )
                        Text(
                            text = if (isHindi) item.irrigationSourceHi else item.irrigationSource,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Suitable crops
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = if (isHindi) "अनुकूल फसलें:" else "Suitable Crops:",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                item.suitableCrops.forEach { crop ->
                    Surface(
                        color = FreshLeaf.copy(alpha = 0.12f),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = crop,
                            fontSize = 11.sp,
                            color = FreshLeaf,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Pricing & Terms Card
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                shape = RoundedCornerShape(10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = if (isHindi) "वार्षिक लीज दर" else "Annual Lease Price",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.outline
                        )
                        Text(
                            text = "₹${item.leasePricePerYear} / एकड़ / वर्ष",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = if (isHindi) "पट्टा अवधि" else "Tenure Duration",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.outline
                        )
                        Text(
                            text = "${item.tenureYears} वर्ष (Years)",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Action: Contact Owner
            Button(
                onClick = onContactOwner,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("contact_landowner_button_${item.id}"),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(imageVector = Icons.Default.Call, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = if (isHindi) "भूमि मालिक से बात करें / पट्टा पूछताछ" else "Contact Landowner / Lease Inquiry",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            }
        }
    }
}

@Composable
fun AddLandDialog(
    isHindi: Boolean,
    onDismiss: () -> Unit,
    onAdd: (
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
    ) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var acreageStr by remember { mutableStateOf("5.0") }
    var soilType by remember { mutableStateOf("Alluvial (जलोढ़)") }
    var irrigation by remember { mutableStateOf("Canal + Borewell (नहर व बोरवेल)") }
    var priceStr by remember { mutableStateOf("28000") }
    var tenureStr by remember { mutableStateOf("2") }
    var ownerName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var village by remember { mutableStateOf("") }
    var district by remember { mutableStateOf("") }
    var state by remember { mutableStateOf("Madhya Pradesh") }
    var cropsStr by remember { mutableStateOf("Wheat, Paddy, Mustard") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = if (isHindi) "कृषि भूमि पट्टे हेतु जोड़ें" else "List Agricultural Land for Lease",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text(if (isHindi) "शीर्षक (उदा. 5 एकड़ नहरी उपजाऊ भूमि)" else "Title (e.g. 5 Acres Canal Farmland)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("add_land_title_input")
                )

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = acreageStr,
                        onValueChange = { acreageStr = it },
                        label = { Text(if (isHindi) "रकबा (एकड़)" else "Acreage (Acres)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = soilType,
                        onValueChange = { soilType = it },
                        label = { Text(if (isHindi) "मिट्टी" else "Soil Type") },
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = priceStr,
                        onValueChange = { priceStr = it },
                        label = { Text(if (isHindi) "लीज दर (₹/एकड़/वर्ष)" else "Price (₹/Acre/Year)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = tenureStr,
                        onValueChange = { tenureStr = it },
                        label = { Text(if (isHindi) "अवधि (वर्ष)" else "Tenure (Years)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f)
                    )
                }

                OutlinedTextField(
                    value = irrigation,
                    onValueChange = { irrigation = it },
                    label = { Text(if (isHindi) "सिंचाई साधन (उदा. नहर, बोरवेल, सोलर)" else "Irrigation Source") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = ownerName,
                    onValueChange = { ownerName = it },
                    label = { Text(if (isHindi) "मालिक का नाम" else "Owner Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text(if (isHindi) "फ़ोन नंबर" else "Phone Number") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = village,
                        onValueChange = { village = it },
                        label = { Text(if (isHindi) "गांव" else "Village") },
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = district,
                        onValueChange = { district = it },
                        label = { Text(if (isHindi) "जिला" else "District") },
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val t = if (title.isBlank()) "5 Acres Fertile Farmland" else title
                    val ac = acreageStr.toDoubleOrNull() ?: 5.0
                    val p = priceStr.toIntOrNull() ?: 28000
                    val ten = tenureStr.toIntOrNull() ?: 2
                    val on = if (ownerName.isBlank()) "Harishchandra" else ownerName
                    val ph = if (phone.isBlank()) "+91 98260 77112" else phone
                    val v = if (village.isBlank()) "Baroda" else village
                    val d = if (district.isBlank()) "Karnal" else district
                    val crList = cropsStr.split(",").map { it.trim() }.filter { it.isNotEmpty() }
                    onAdd(t, ac, soilType, irrigation, p, ten, on, ph, v, d, state, crList)
                },
                modifier = Modifier.testTag("submit_add_land_button"),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Text(if (isHindi) "भूमि प्रकाशित करें" else "Publish Listing")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(if (isHindi) "रद्द करें" else "Cancel")
            }
        }
    )
}
