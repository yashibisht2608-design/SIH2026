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
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.MoneyOff
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Verified
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
import com.example.data.model.ProduceItem
import com.example.ui.components.BrokerFreeSavingsBadge
import com.example.ui.components.DirectContactDialog
import com.example.ui.components.FilterChipsRow
import com.example.ui.components.RuralSearchBar
import com.example.ui.theme.AlertOrange
import com.example.ui.theme.FreshLeaf
import com.example.ui.theme.HarvestGold

@Composable
fun DirectMarketScreen(
    produceList: List<ProduceItem>,
    language: AppLanguage,
    filter: String,
    onFilterChange: (String) -> Unit,
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    onOpenAddProduce: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isHindi = language == AppLanguage.HINDI
    var contactProduceItem by remember { mutableStateOf<ProduceItem?>(null) }

    val categories = if (isHindi) {
        listOf("सभी", "Grains", "Pulses", "Oilseeds", "Vegetables")
    } else {
        listOf("All", "Grains", "Pulses", "Oilseeds", "Vegetables")
    }

    val filteredList = produceList.filter { item ->
        val matchesCategory = if (filter == "All" || filter == "सभी") true
        else item.category.equals(filter, ignoreCase = true)

        val matchesSearch = if (searchQuery.isBlank()) true
        else item.cropName.contains(searchQuery, ignoreCase = true) ||
                item.cropNameHi.contains(searchQuery, ignoreCase = true) ||
                item.variety.contains(searchQuery, ignoreCase = true) ||
                item.district.contains(searchQuery, ignoreCase = true) ||
                item.state.contains(searchQuery, ignoreCase = true) ||
                item.farmerName.contains(searchQuery, ignoreCase = true)

        matchesCategory && matchesSearch
    }

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .testTag("direct_market_screen")
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(4.dp))
                // Broker Hatao Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = if (isHindi) "सीधा बाज़ार" else "Direct Market",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                color = AlertOrange,
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    text = if (isHindi) "बिचौलिया हटाओ" else "Broker Hatao",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                        Text(
                            text = if (isHindi) "सीधी खरीद-बिक्री • शून्य प्रतिशत दलाली" else "Peer-to-peer crops: 100% farmer earnings",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }

                    OutlinedButton(
                        onClick = onOpenAddProduce,
                        modifier = Modifier.testTag("list_produce_button"),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = if (isHindi) "फसल बेचें" else "Sell Crop", fontSize = 12.sp)
                    }
                }
            }

            // Broker Hatao Value Proposition Banner
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(FreshLeaf),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.MoneyOff,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = if (isHindi) "दलालों को दें अलविदा! सीधा भाव पाएं" else "Zero Middlemen • Maximum Profit",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Text(
                                text = if (isHindi)
                                    "मंडी आढ़त व दलाली के 8-15% शुल्क की बचत। सीधे खरीदार से कॉल पर सौदा करें।"
                                else
                                    "Bypass mandi commissions (8-15%). Call and trade directly with bulk buyers.",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f)
                            )
                        }
                    }
                }
            }

            // Search Bar
            item {
                RuralSearchBar(
                    query = searchQuery,
                    onQueryChange = onSearchChange,
                    placeholderText = if (isHindi) "फसल (गेहूँ, धान, चना) या जिला खोजें..." else "Search crops (wheat, basmati, pulses)..."
                )
            }

            // Category Filter
            item {
                FilterChipsRow(
                    filters = categories,
                    selectedFilter = filter,
                    onSelectFilter = onFilterChange
                )
            }

            // Counter
            item {
                Text(
                    text = if (isHindi) "${filteredList.size} फसलें सीधी बिक्री के लिए तैयार" else "${filteredList.size} Direct Crop Lots Available",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Crop Cards
            items(filteredList, key = { it.id }) { item ->
                ProduceListingCard(
                    item = item,
                    isHindi = isHindi,
                    onContactFarmer = { contactProduceItem = item }
                )
            }

            item {
                Spacer(modifier = Modifier.height(30.dp))
            }
        }

        // Direct Contact Dialog
        contactProduceItem?.let { item ->
            DirectContactDialog(
                contactName = item.farmerName,
                contactRole = if (isHindi) "किसान (कृषक)" else "Farmer / Producer",
                phoneNumber = item.farmerPhone,
                locationText = "${item.village}, ${item.district}, ${item.state}",
                itemDescription = "${if (isHindi) item.cropNameHi else item.cropName} (${item.quantityQuintals} क्विंटल @ ₹${item.pricePerQuintal})",
                isHindi = isHindi,
                onDismiss = { contactProduceItem = null }
            )
        }
    }
}

@Composable
fun ProduceListingCard(
    item: ProduceItem,
    isHindi: Boolean,
    onContactFarmer: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("produce_card_${item.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Optional image
            if (item.imageRes != null) {
                Image(
                    painter = painterResource(id = item.imageRes),
                    contentDescription = item.cropName,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(10.dp))
            }

            // Header Row: Crop Title & Quality Grade
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = if (isHindi) item.cropNameHi else item.cropName,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "किस्म / Variety: ${if (isHindi) item.varietyHi else item.variety}",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.outline
                    )
                }

                Surface(
                    color = FreshLeaf.copy(alpha = 0.12f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = item.qualityGrade,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = FreshLeaf,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Location & Farmer Info
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier.size(14.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${item.farmerName} • 📍 ${item.village}, ${item.district} (${item.state})",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Price per quintal box with Mandi comparison
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = if (isHindi) "सीधा भाव (अपेक्षित)" else "Direct Farmer Price",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.outline
                            )
                            Text(
                                text = "₹${item.pricePerQuintal} / क्विंटल",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = if (isHindi) "उपलब्ध मात्रा" else "Available Quantity",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.outline
                            )
                            Text(
                                text = "${item.quantityQuintals} क्विंटल",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (isHindi) "मंडी दर: ₹${item.mandiPricePerQuintal}/क्विंटल" else "Mandi Benchmark: ₹${item.mandiPricePerQuintal}/Qtl",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.outline
                        )

                        BrokerFreeSavingsBadge(savingsInr = item.brokerSavingsPct, isHindi = isHindi)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Action Button: Direct Contact
            Button(
                onClick = onContactFarmer,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("contact_farmer_button_${item.id}"),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(imageVector = Icons.Default.Call, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = if (isHindi) "सीधे किसान से संपर्क करें (कॉल/व्हाट्सएप)" else "Contact Farmer Directly",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            }
        }
    }
}

@Composable
fun AddProduceDialog(
    isHindi: Boolean,
    onDismiss: () -> Unit,
    onAdd: (
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
    ) -> Unit
) {
    var cropName by remember { mutableStateOf("") }
    var variety by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Grains") }
    var priceStr by remember { mutableStateOf("2900") }
    var quantityStr by remember { mutableStateOf("50") }
    var qualityGrade by remember { mutableStateOf("Grade A+ (Premium)") }
    var farmerName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var village by remember { mutableStateOf("") }
    var district by remember { mutableStateOf("") }
    var state by remember { mutableStateOf("Madhya Pradesh") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = if (isHindi) "अपनी फसल सूचीबद्ध करें (बिचौलिया मुक्त)" else "List Your Produce (Direct Market)",
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
                    value = cropName,
                    onValueChange = { cropName = it },
                    label = { Text(if (isHindi) "फसल का नाम (उदा. शरबती गेहूँ, बासमती धान)" else "Crop Name (e.g. Wheat, Basmati)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("add_crop_name_input")
                )

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = variety,
                        onValueChange = { variety = it },
                        label = { Text(if (isHindi) "किस्म (उदा. C-306)" else "Variety") },
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = category,
                        onValueChange = { category = it },
                        label = { Text("Category") },
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = priceStr,
                        onValueChange = { priceStr = it },
                        label = { Text(if (isHindi) "भाव (₹/क्विंटल)" else "Price (₹/Qtl)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f).testTag("add_crop_price_input")
                    )
                    OutlinedTextField(
                        value = quantityStr,
                        onValueChange = { quantityStr = it },
                        label = { Text(if (isHindi) "मात्रा (क्विंटल)" else "Quantity (Qtl)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f)
                    )
                }

                OutlinedTextField(
                    value = farmerName,
                    onValueChange = { farmerName = it },
                    label = { Text(if (isHindi) "किसान का नाम" else "Farmer Full Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text(if (isHindi) "मोबाइल नंबर" else "Mobile Number") },
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
                    val cr = if (cropName.isBlank()) "Sharbati Wheat" else cropName
                    val vr = if (variety.isBlank()) "Desi Golden" else variety
                    val p = priceStr.toIntOrNull() ?: 2900
                    val q = quantityStr.toDoubleOrNull() ?: 50.0
                    val fn = if (farmerName.isBlank()) "Ramesh Kisan" else farmerName
                    val ph = if (phone.isBlank()) "+91 98765 12345" else phone
                    val vil = if (village.isBlank()) "Sujalpur" else village
                    val dis = if (district.isBlank()) "Sehore" else district
                    onAdd(cr, vr, category, p, q, qualityGrade, fn, ph, vil, dis, state)
                },
                modifier = Modifier.testTag("submit_add_produce_button"),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(if (isHindi) "फसल प्रकाशित करें" else "Publish Listing")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(if (isHindi) "रद्द करें" else "Cancel")
            }
        }
    )
}
