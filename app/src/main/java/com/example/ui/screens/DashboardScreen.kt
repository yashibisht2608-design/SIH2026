package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Agriculture
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Landscape
import androidx.compose.material.icons.filled.LocalAtm
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.AppLanguage
import com.example.data.model.NavigationTab
import com.example.ui.theme.AlertOrange
import com.example.ui.theme.FreshLeaf
import com.example.ui.theme.HarvestGold

@Composable
fun DashboardScreen(
    language: AppLanguage,
    onNavigateTab: (NavigationTab) -> Unit,
    onOpenSchema: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isHindi = language == AppLanguage.HINDI

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("dashboard_screen")
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(4.dp))
            // Hero Banner with farm image
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                shape = RoundedCornerShape(18.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(id = R.drawable.img_farm_hero),
                        contentDescription = "Indian Farmland Hero",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    // Gradient overlay for readability
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, Color(0xCC0E2512), Color(0xFA081C0C)),
                                    startY = 60f
                                )
                            )
                    )

                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(16.dp)
                    ) {
                        Surface(
                            color = HarvestGold,
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = if (isHindi) "आत्मनिर्भर किसान • डिजिटल कृषि" else "Self-Reliant Farmer • AgriTech India",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (isHindi) "किसान सेतु: भारत का अपना कृषि मंच" else "KisanSetu: Direct Agri Platform",
                            color = Color.White,
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = if (isHindi) "उपकरण किराया • सीधा बाज़ार (दलाल मुक्त) • भूमि पट्टा • सरकारी योजनाएं" else "Equipment Rental • Direct Market • Land Lease • Subsidies",
                            color = Color(0xFFE2F0E4),
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

        // Live Weather & Mandi Alert
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
                            .background(HarvestGold),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.WbSunny,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = if (isHindi) "आज की मंडी व मौसम सूचना" else "Live Mandi & Weather Update",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = if (isHindi)
                                "गेहूं व चना आवक तेज। सीधा बाज़ार में किसानों को 15% अधिक मूल्य मिल रहा है।"
                            else
                                "High harvest arrivals. Farmers saving 15%+ broker fee via Direct Market.",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f)
                        )
                    }
                }
            }
        }

        // Section Title: 4 Core Modules
        item {
            Text(
                text = if (isHindi) "प्रमुख सेवाएं (मुख्य सुविधाएं)" else "Core Agricultural Services",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        // 1. Equipment Rental Service
        item {
            ModuleFeatureCard(
                title = if (isHindi) "1. कृषि उपकरण किराया सेवा" else "1. Equipment Rental Service",
                subtitle = if (isHindi)
                    "ट्रैक्टर, कंबाइन हार्वेस्टर, ड्रोन व रोटावेटर उचित दैनिक व प्रति घंटा किराये पर प्राप्त करें।"
                else
                    "Rent tractors, combine harvesters, seed drills, and spray drones at affordable daily/hourly rates.",
                badge = if (isHindi) "किराये पर उपलब्ध" else "Ready to Rent",
                icon = Icons.Default.Agriculture,
                containerColor = MaterialTheme.colorScheme.surface,
                iconColor = MaterialTheme.colorScheme.primary,
                onClick = { onNavigateTab(NavigationTab.EQUIPMENT) }
            )
        }

        // 2. Direct Market (Broker Hatao)
        item {
            ModuleFeatureCard(
                title = if (isHindi) "2. सीधा बाज़ार (बिचौलिया हटाओ)" else "2. Direct Market (Broker Hatao)",
                subtitle = if (isHindi)
                    "दलालों को हटाकर अपनी फसल (गेहूँ, धान, चना, सरसों) सीधे थोक खरीदारों को बेचें और पूरा मुनाफा पाएं।"
                else
                    "Peer-to-peer produce marketplace eliminating middlemen. Sell crops per quintal directly to buyers.",
                badge = if (isHindi) "0% दलाली • 100% मुनाफा" else "Zero Broker Commission",
                icon = Icons.Default.ShoppingBag,
                containerColor = MaterialTheme.colorScheme.surface,
                iconColor = FreshLeaf,
                onClick = { onNavigateTab(NavigationTab.DIRECT_MARKET) }
            )
        }

        // 3. Landlease System
        item {
            ModuleFeatureCard(
                title = if (isHindi) "3. कृषि भूमि पट्टा प्रणाली" else "3. Landlease System",
                subtitle = if (isHindi)
                    "उपजाऊ नहरी व ट्यूबवेल वाली कृषि भूमि देखें। मिट्टी का प्रकार, रकबा (एकड़) और वार्षिक पट्टा शर्तें।"
                else
                    "Explore verified agricultural land parcels by acreage, soil type, irrigation source, and lease terms.",
                badge = if (isHindi) "सत्यापित भू-पट्टा" else "Verified Farmland",
                icon = Icons.Default.Landscape,
                containerColor = MaterialTheme.colorScheme.surface,
                iconColor = MaterialTheme.colorScheme.secondary,
                onClick = { onNavigateTab(NavigationTab.LAND_LEASE) }
            )
        }

        // 4. Government Policy Hub
        item {
            ModuleFeatureCard(
                title = if (isHindi) "4. सरकारी योजनाएं व सब्सिडी" else "4. Government Policy Hub",
                subtitle = if (isHindi)
                    "पीएम किसान ₹6000, फसल बीमा, ट्रैक्टर पर 50-80% सब्सिडी और आसान केसीसी ऋण की संपूर्ण जानकारी।"
                else
                    "Simplified bulletin for PM-KISAN, PMFBY crop insurance, SMAM machinery subsidies, and KCC loans.",
                badge = if (isHindi) "सरकारी सहायता" else "Subsidies & Schemes",
                icon = Icons.Default.AccountBalance,
                containerColor = MaterialTheme.colorScheme.surface,
                iconColor = HarvestGold,
                onClick = { onNavigateTab(NavigationTab.GOVERNMENT_POLICIES) }
            )
        }

        // 5. Technical Requirement: JSON Database Schema Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onOpenSchema() }
                    .testTag("dashboard_schema_card"),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                shape = RoundedCornerShape(14.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(MaterialTheme.colorScheme.secondary),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Code,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = if (isHindi) "डेटाबेस स्कीमा (JSON Schema)" else "Sample JSON Database Schema",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                            Text(
                                text = if (isHindi) "Users, Equipment, Produce, Land, Policies स्कीमा देखें" else "Manage Users, Equipment, Produce, Land & Policies",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.8f)
                            )
                        }
                    }
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "View Schema",
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun ModuleFeatureCard(
    title: String,
    subtitle: String,
    badge: String,
    icon: ImageVector,
    containerColor: Color,
    iconColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(iconColor.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = iconColor,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = title,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Surface(
                    color = iconColor.copy(alpha = 0.12f),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = badge,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = iconColor,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = subtitle,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 17.sp
            )

            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "आगे बढ़ें • Explore",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = iconColor
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(14.dp)
                )
            }
        }
    }
}
