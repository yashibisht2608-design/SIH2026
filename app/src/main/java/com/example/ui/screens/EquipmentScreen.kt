package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Agriculture
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.data.model.EquipmentItem
import com.example.ui.components.DirectContactDialog
import com.example.ui.components.FilterChipsRow
import com.example.ui.components.RuralSearchBar
import com.example.ui.theme.FreshLeaf
import com.example.ui.theme.HarvestGold

@Composable
fun EquipmentScreen(
    equipmentList: List<EquipmentItem>,
    language: AppLanguage,
    filter: String,
    onFilterChange: (String) -> Unit,
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    onRequestRent: (EquipmentItem) -> Unit,
    onOpenAddEquipment: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isHindi = language == AppLanguage.HINDI
    var directContactItem by remember { mutableStateOf<EquipmentItem?>(null) }

    val categories = if (isHindi) {
        listOf("सभी", "Tractor", "Harvester", "Implement", "Drone & Solar")
    } else {
        listOf("All", "Tractor", "Harvester", "Implement", "Drone & Solar")
    }

    val filteredList = equipmentList.filter { item ->
        val matchesCategory = if (filter == "All" || filter == "सभी") true
        else item.category.contains(filter, ignoreCase = true)

        val matchesSearch = if (searchQuery.isBlank()) true
        else item.title.contains(searchQuery, ignoreCase = true) ||
                item.titleHi.contains(searchQuery, ignoreCase = true) ||
                item.location.contains(searchQuery, ignoreCase = true) ||
                item.ownerName.contains(searchQuery, ignoreCase = true)

        matchesCategory && matchesSearch
    }

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .testTag("equipment_screen")
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(4.dp))
                // Top Header with Post Button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = if (isHindi) "कृषि उपकरण किराया बाज़ार" else "Equipment Rental Service",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = if (isHindi) "सत्यापित ट्रैक्टर, हार्वेस्टर व यंत्र" else "Affordable rentals directly from local owners",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }

                    OutlinedButton(
                        onClick = onOpenAddEquipment,
                        modifier = Modifier.testTag("list_equipment_button"),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = if (isHindi) "उपकरण जोड़ें" else "List Equipment", fontSize = 12.sp)
                    }
                }
            }

            // Search Bar
            item {
                RuralSearchBar(
                    query = searchQuery,
                    onQueryChange = onSearchChange,
                    placeholderText = if (isHindi) "ट्रैक्टर या स्थान खोजें (उदा. महिंद्रा, करनाल)..." else "Search tractor, harvester, location..."
                )
            }

            // Category Chips
            item {
                FilterChipsRow(
                    filters = categories,
                    selectedFilter = filter,
                    onSelectFilter = onFilterChange
                )
            }

            // Item count & status banner
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (isHindi) "${filteredList.size} उपकरण उपलब्ध" else "${filteredList.size} Machines Available",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Surface(
                        color = FreshLeaf.copy(alpha = 0.12f),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = if (isHindi) "✓ आज ही बुकिंग उपलब्ध" else "✓ Instant Booking Available",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = FreshLeaf,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }

            // Listings
            items(filteredList, key = { it.id }) { item ->
                EquipmentListingCard(
                    item = item,
                    isHindi = isHindi,
                    onRequestRent = { onRequestRent(item) },
                    onDirectCall = { directContactItem = item }
                )
            }

            item {
                Spacer(modifier = Modifier.height(30.dp))
            }
        }

        // Direct Contact Dialog
        directContactItem?.let { item ->
            DirectContactDialog(
                contactName = item.ownerName,
                contactRole = if (isHindi) "उपकरण मालिक (Farmer/Owner)" else "Equipment Owner",
                phoneNumber = item.ownerPhone,
                locationText = item.location,
                itemDescription = "${if (isHindi) item.titleHi else item.title} (${item.horsepower})",
                isHindi = isHindi,
                onDismiss = { directContactItem = null }
            )
        }
    }
}

@Composable
fun EquipmentListingCard(
    item: EquipmentItem,
    isHindi: Boolean,
    onRequestRent: () -> Unit,
    onDirectCall: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("equipment_card_${item.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Optional image banner
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

            // Title & HP Badge
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
                        text = "📍 ${item.location} • ${item.ownerName}",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.outline
                    )
                }

                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Speed,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = item.horsepower,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Specs / attachments
            Text(
                text = if (isHindi) item.specsHi else item.specs,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 16.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Pricing Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = if (isHindi) "दैनिक किराया" else "Daily Rental",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.outline
                    )
                    Text(
                        text = "₹${item.dailyRate} / दिन",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = if (isHindi) "प्रति घंटा दर" else "Hourly Rate",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.outline
                    )
                    Text(
                        text = "₹${item.hourlyRate} / घंटा",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Actions: "Request to Rent" & Direct Call
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = onDirectCall,
                    modifier = Modifier
                        .height(48.dp)
                        .testTag("call_owner_button_${item.id}"),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(imageVector = Icons.Default.Call, contentDescription = "Call", modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = if (isHindi) "संपर्क" else "Contact", fontSize = 13.sp)
                }

                Button(
                    onClick = onRequestRent,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .testTag("request_rent_button_${item.id}"),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(imageVector = Icons.Default.Agriculture, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (isHindi) "किराये के लिए अनुरोध" else "Request to Rent",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}

@Composable
fun RequestToRentDialog(
    equipment: EquipmentItem,
    isHindi: Boolean,
    onDismiss: () -> Unit,
    onSubmit: (farmerName: String, phone: String, startDate: String, days: Int, delivery: Boolean, village: String) -> Unit
) {
    var farmerName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("कल सुबह (Tomorrow)") }
    var durationDays by remember { mutableIntStateOf(1) }
    var needDelivery by remember { mutableStateOf(true) }
    var village by remember { mutableStateOf(equipment.location) }

    val totalEstimate = (equipment.dailyRate * durationDays) + (if (needDelivery) 350 else 0)

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Column {
                Text(
                    text = if (isHindi) "उपकरण किराया अनुरोध" else "Equipment Rental Request",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = if (isHindi) equipment.titleHi else equipment.title,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = farmerName,
                    onValueChange = { farmerName = it },
                    label = { Text(if (isHindi) "आपका नाम (किसान भाई)" else "Your Full Name") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("rent_farmer_name_input"),
                    shape = RoundedCornerShape(10.dp)
                )

                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text(if (isHindi) "मोबाइल नंबर (10 अंक)" else "Contact Mobile Number") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("rent_phone_input"),
                    shape = RoundedCornerShape(10.dp)
                )

                OutlinedTextField(
                    value = village,
                    onValueChange = { village = it },
                    label = { Text(if (isHindi) "खेत / गांव का पता" else "Farm / Village Location") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                )

                // Days selection
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (isHindi) "अवधि (दिन):" else "Duration (Days):",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        OutlinedButton(
                            onClick = { if (durationDays > 1) durationDays-- },
                            modifier = Modifier.size(38.dp),
                            shape = CircleShape
                        ) {
                            Text("-", fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "$durationDays",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        OutlinedButton(
                            onClick = { durationDays++ },
                            modifier = Modifier.size(38.dp),
                            shape = CircleShape
                        ) {
                            Text("+", fontWeight = FontWeight.Bold)
                        }
                    }
                }

                // Delivery checkbox
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Checkbox(
                        checked = needDelivery,
                        onCheckedChange = { needDelivery = it }
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = if (isHindi) "खेत तक डिलीवरी चाहिए (+₹350)" else "Include Farm Delivery (+₹350)",
                        fontSize = 13.sp
                    )
                }

                // Total Estimate Box
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (isHindi) "अनुमानित कुल किराया:" else "Estimated Total:",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = "₹$totalEstimate",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val name = if (farmerName.isBlank()) "Kisan Mitra" else farmerName
                    val ph = if (phone.isBlank()) "9876543210" else phone
                    onSubmit(name, ph, startDate, durationDays, needDelivery, village)
                },
                modifier = Modifier.testTag("submit_rent_request_button"),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(if (isHindi) "अनुरोध भेजें" else "Confirm Request")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(if (isHindi) "रद्द करें" else "Cancel")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEquipmentDialog(
    isHindi: Boolean,
    onDismiss: () -> Unit,
    onAdd: (
        title: String,
        category: String,
        horsepower: String,
        dailyRate: Int,
        hourlyRate: Int,
        ownerName: String,
        ownerPhone: String,
        location: String,
        specs: String
    ) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Tractor") }
    var horsepower by remember { mutableStateOf("50 HP") }
    var dailyRateStr by remember { mutableStateOf("1500") }
    var hourlyRateStr by remember { mutableStateOf("350") }
    var ownerName by remember { mutableStateOf("") }
    var ownerPhone by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var specs by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = if (isHindi) "अपना कृषि उपकरण किराये पर जोड़ें" else "List Your Equipment For Rent",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
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
                    label = { Text(if (isHindi) "उपकरण का नाम व मॉडल (उदा. स्वराज 744 FE)" else "Equipment Name & Model") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("add_eq_title_input")
                )

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = category,
                        onValueChange = { category = it },
                        label = { Text("Category") },
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = horsepower,
                        onValueChange = { horsepower = it },
                        label = { Text("Horsepower") },
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = dailyRateStr,
                        onValueChange = { dailyRateStr = it },
                        label = { Text(if (isHindi) "दैनिक दर (₹)" else "Daily Rate (₹)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = hourlyRateStr,
                        onValueChange = { hourlyRateStr = it },
                        label = { Text(if (isHindi) "प्रति घंटा दर (₹)" else "Hourly Rate (₹)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f)
                    )
                }

                OutlinedTextField(
                    value = ownerName,
                    onValueChange = { ownerName = it },
                    label = { Text(if (isHindi) "मालिक का नाम" else "Owner Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = ownerPhone,
                    onValueChange = { ownerPhone = it },
                    label = { Text(if (isHindi) "फ़ोन नंबर" else "Phone Number") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = location,
                    onValueChange = { location = it },
                    label = { Text(if (isHindi) "स्थान / जिला (उदा. करनाल, हरियाणा)" else "Location / District") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = specs,
                    onValueChange = { specs = it },
                    label = { Text(if (isHindi) "विशेष विवरण व अटैचमेंट" else "Specifications & Attachments") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val t = if (title.isBlank()) "Swaraj 744 FE Tractor" else title
                    val oName = if (ownerName.isBlank()) "Surender Gill" else ownerName
                    val oPhone = if (ownerPhone.isBlank()) "+91 98765 43210" else ownerPhone
                    val loc = if (location.isBlank()) "Karnal, Haryana" else location
                    val sp = if (specs.isBlank()) "Includes Cultivator and Trolley hitch" else specs
                    val dRate = dailyRateStr.toIntOrNull() ?: 1500
                    val hRate = hourlyRateStr.toIntOrNull() ?: 350
                    onAdd(t, category, horsepower, dRate, hRate, oName, oPhone, loc, sp)
                },
                modifier = Modifier.testTag("submit_add_equipment_button"),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(if (isHindi) "लिस्ट करें" else "Publish Listing")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(if (isHindi) "रद्द करें" else "Cancel")
            }
        }
    )
}
