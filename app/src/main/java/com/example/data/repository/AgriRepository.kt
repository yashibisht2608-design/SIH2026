package com.example.data.repository

import com.example.R
import com.example.data.model.EquipmentItem
import com.example.data.model.LandItem
import com.example.data.model.PolicyItem
import com.example.data.model.ProduceItem
import com.example.data.model.RentalBookingRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AgriRepository {

    private val _equipmentList = MutableStateFlow(getInitialEquipment())
    val equipmentList: StateFlow<List<EquipmentItem>> = _equipmentList.asStateFlow()

    private val _produceList = MutableStateFlow(getInitialProduce())
    val produceList: StateFlow<List<ProduceItem>> = _produceList.asStateFlow()

    private val _landList = MutableStateFlow(getInitialLand())
    val landList: StateFlow<List<LandItem>> = _landList.asStateFlow()

    private val _policyList = MutableStateFlow(getInitialPolicies())
    val policyList: StateFlow<List<PolicyItem>> = _policyList.asStateFlow()

    private val _rentalBookings = MutableStateFlow<List<RentalBookingRequest>>(emptyList())
    val rentalBookings: StateFlow<List<RentalBookingRequest>> = _rentalBookings.asStateFlow()

    fun addEquipment(item: EquipmentItem) {
        _equipmentList.update { listOf(item) + it }
    }

    fun addProduce(item: ProduceItem) {
        _produceList.update { listOf(item) + it }
    }

    fun addLand(item: LandItem) {
        _landList.update { listOf(item) + it }
    }

    fun bookRental(request: RentalBookingRequest) {
        _rentalBookings.update { listOf(request) + it }
    }

    private fun getInitialEquipment(): List<EquipmentItem> {
        return listOf(
            EquipmentItem(
                id = "EQ-01",
                title = "Mahindra 575 DI Sarpanch",
                titleHi = "महिंद्रा 575 डीआई सरपंच",
                category = "Tractor",
                categoryHi = "ट्रैक्टर",
                horsepower = "45 HP",
                dailyRate = 1200,
                hourlyRate = 350,
                ownerName = "Gurpreet Singh",
                ownerPhone = "+91 98721 54320",
                location = "Moga, Punjab",
                isAvailable = true,
                specs = "Power steering, includes 7-tine cultivator & trolley hitch",
                specsHi = "पावर स्टीयरिंग, 7-टाइन कल्टीवेटर और ट्रॉली हुक सहित",
                imageRes = R.drawable.img_tractor_rental
            ),
            EquipmentItem(
                id = "EQ-02",
                title = "John Deere 5050 D (4WD)",
                titleHi = "जॉन डियर 5050 डी (4WD)",
                category = "Tractor",
                categoryHi = "ट्रैक्टर",
                horsepower = "50 HP",
                dailyRate = 1750,
                hourlyRate = 450,
                ownerName = "Rajeshwar Patil",
                ownerPhone = "+91 94230 87123",
                location = "Indore, MP",
                isAvailable = true,
                specs = "4-Wheel Drive, dual clutch, reverse PTO, heavy disc harrow",
                specsHi = "4-व्हील ड्राइव, डुअल क्लच, रिवर्स पीटीओ, भारी डिस्क हैरो",
                imageRes = R.drawable.img_tractor_rental
            ),
            EquipmentItem(
                id = "EQ-03",
                title = "Preet 987 Self-Propelled Harvester",
                titleHi = "प्रीत 987 कंबाइन हार्वेस्टर",
                category = "Harvester",
                categoryHi = "हार्वेस्टर",
                horsepower = "101 HP",
                dailyRate = 8500,
                hourlyRate = 2200,
                ownerName = "Kisan Machinery Co-op",
                ownerPhone = "+91 98140 33499",
                location = "Karnal, Haryana",
                isAvailable = true,
                specs = "14-ft cutter bar, AC cabin, ideal for Paddy & Wheat harvesting",
                specsHi = "14 फीट कटर बार, एसी केबिन, धान व गेहूं की कटाई के लिए उपयुक्त",
                imageRes = R.drawable.img_farm_hero
            ),
            EquipmentItem(
                id = "EQ-04",
                title = "Shaktiman Semi-Champion Rotavator",
                titleHi = "शक्तिमान सेमी-चैंपियन रोटावेटर",
                category = "Implement",
                categoryHi = "कृषि यंत्र",
                horsepower = "40-55 HP Req.",
                dailyRate = 700,
                hourlyRate = 200,
                ownerName = "Balwant Verma",
                ownerPhone = "+91 97551 22910",
                location = "Sehore, MP",
                isAvailable = true,
                specs = "7-feet width, 48 L-type boron steel blades, gear drive",
                specsHi = "7 फीट चौड़ाई, 48 बोरॉन स्टील ब्लेड, गियर ड्राइव",
                imageRes = null
            ),
            EquipmentItem(
                id = "EQ-05",
                title = "Garuda Kisan Agri Drone Sprayer",
                titleHi = "गरुड़ किसान एग्री ड्रोन स्प्रेयर",
                category = "Drone & Solar",
                categoryHi = "ड्रोन व सौर",
                horsepower = "10L Payload",
                dailyRate = 3000,
                hourlyRate = 600,
                ownerName = "AgriTech Services Ltd",
                ownerPhone = "+91 99001 88765",
                location = "Nashik, Maharashtra",
                isAvailable = true,
                specs = "Covers 1 acre in 7 minutes, certified DGCA pilot included",
                specsHi = "7 मिनट में 1 एकड़ छिड़काव, प्रमाणित पायलट शामिल",
                imageRes = null
            ),
            EquipmentItem(
                id = "EQ-06",
                title = "Shakti Solar Submersible Pump Set",
                titleHi = "शक्ति सोलर सबमर्सिबल पंप",
                category = "Drone & Solar",
                categoryHi = "ड्रोन व सौर",
                horsepower = "5 HP",
                dailyRate = 850,
                hourlyRate = 180,
                ownerName = "Dinesh Choudhary",
                ownerPhone = "+91 98290 77412",
                location = "Jaipur, Rajasthan",
                isAvailable = true,
                specs = "Trolley-mounted portable solar panels + pump, 25,000 LPH",
                specsHi = "ट्रॉली पर पोर्टेबल सोलर पैनल व पंप, 25,000 लीटर/घंटा",
                imageRes = null
            )
        )
    }

    private fun getInitialProduce(): List<ProduceItem> {
        return listOf(
            ProduceItem(
                id = "PR-01",
                cropName = "Sharbati Wheat (शरबती गेहूँ)",
                cropNameHi = "शरबती गेहूँ (प्रीमियम)",
                variety = "C-306 Golden Grain",
                varietyHi = "सी-306 स्वर्णिम दाना",
                category = "Grains",
                categoryHi = "अनाज",
                pricePerQuintal = 2850,
                mandiPricePerQuintal = 2420,
                quantityQuintals = 120.0,
                qualityGrade = "Grade A+ (Premium)",
                farmerName = "Radheshyam Sharma",
                farmerPhone = "+91 98260 44102",
                village = "Barkheda",
                district = "Sehore",
                state = "Madhya Pradesh",
                harvestDate = "March 2026",
                imageRes = R.drawable.img_fresh_harvest
            ),
            ProduceItem(
                id = "PR-02",
                cropName = "Pusa Basmati Paddy 1121",
                cropNameHi = "पूसा 1121 बासमती धान",
                variety = "Extra Long Grain",
                varietyHi = "अतिरिक्त लंबा खुशबूदार दाना",
                category = "Grains",
                categoryHi = "अनाज",
                pricePerQuintal = 4450,
                mandiPricePerQuintal = 3900,
                quantityQuintals = 85.0,
                qualityGrade = "Export Quality",
                farmerName = "Harinder Pal Singh",
                farmerPhone = "+91 98762 10984",
                village = "Taraori",
                district = "Karnal",
                state = "Haryana",
                harvestDate = "October 2025 (Cured)",
                imageRes = R.drawable.img_fresh_harvest
            ),
            ProduceItem(
                id = "PR-03",
                cropName = "Desi Chana (Bengal Gram)",
                cropNameHi = "देसी चना (उत्कृष्ट दलहन)",
                variety = "JG-11 Bold",
                varietyHi = "जेजी-11 मोटा दाना",
                category = "Pulses",
                categoryHi = "दलहन",
                pricePerQuintal = 5900,
                mandiPricePerQuintal = 5350,
                quantityQuintals = 65.0,
                qualityGrade = "Grade A",
                farmerName = "Manohar Lal Meena",
                farmerPhone = "+91 94140 66231",
                village = "Nokha",
                district = "Bikaner",
                state = "Rajasthan",
                harvestDate = "February 2026",
                imageRes = null
            ),
            ProduceItem(
                id = "PR-04",
                cropName = "Hybrid Red Tomatoes",
                cropNameHi = "ताज़े लाल टमाटर (हाइब्रिड)",
                variety = "Abhinav Seminis",
                varietyHi = "अभिनव गोल सख्त दाना",
                category = "Vegetables",
                categoryHi = "सब्जियां",
                pricePerQuintal = 1450,
                mandiPricePerQuintal = 1100,
                quantityQuintals = 45.0,
                qualityGrade = "Firm & Ripe",
                farmerName = "Santosh Kulkarni",
                farmerPhone = "+91 98500 12349",
                village = "Pimpalgaon",
                district = "Nashik",
                state = "Maharashtra",
                harvestDate = "Harvested Yesterday",
                imageRes = R.drawable.img_fresh_harvest
            ),
            ProduceItem(
                id = "PR-05",
                cropName = "Mustard Seeds (Sarson)",
                cropNameHi = "पीली सरसों (उच्च तेल मात्रा)",
                variety = "Giriraj DRMRIJ-31",
                varietyHi = "गिरीराज 42% तेल",
                category = "Oilseeds",
                categoryHi = "तिलहन",
                pricePerQuintal = 5650,
                mandiPricePerQuintal = 5050,
                quantityQuintals = 90.0,
                qualityGrade = "High Oil (42%)",
                farmerName = "Mukesh Yadav",
                farmerPhone = "+91 97850 88210",
                village = "Behror",
                district = "Kotputli-Alwar",
                state = "Rajasthan",
                harvestDate = "March 2026",
                imageRes = null
            ),
            ProduceItem(
                id = "PR-06",
                cropName = "Ooty Garlic (लहसुन)",
                cropNameHi = "देसी मोटा लहसुन (सफेद)",
                variety = "G-282 Yamuna Safed",
                varietyHi = "जी-282 यमुना सफेद",
                category = "Vegetables",
                categoryHi = "सब्जियां",
                pricePerQuintal = 12500,
                mandiPricePerQuintal = 10800,
                quantityQuintals = 30.0,
                qualityGrade = "Dry & Cured",
                farmerName = "Babulal Patidar",
                farmerPhone = "+91 94250 99011",
                village = "Daloda",
                district = "Mandsaur",
                state = "Madhya Pradesh",
                harvestDate = "January 2026",
                imageRes = null
            )
        )
    }

    private fun getInitialLand(): List<LandItem> {
        return listOf(
            LandItem(
                id = "LD-01",
                title = "5 Acres Canal-Fed Alluvial Farmland",
                titleHi = "5 एकड़ नहरी जलोढ़ कृषि भूमि",
                acreage = 5.0,
                soilType = "Alluvial Loam (जलोढ़ मिट्टी)",
                soilTypeHi = "जलोढ़ दोमट उपजाऊ मिट्टी",
                irrigationSource = "Canal Water + 10 HP Borewell",
                irrigationSourceHi = "नहर का पानी + 10 एचपी सबमर्सिबल ट्यूबवेल",
                leasePricePerYear = 32000,
                tenureYears = 2,
                ownerName = "Sukhvinder Dhillon",
                ownerPhone = "+91 98150 44321",
                village = "Gharaunda",
                district = "Karnal",
                state = "Haryana",
                suitableCrops = listOf("Paddy", "Wheat", "Mustard", "Sugarcane"),
                imageRes = R.drawable.img_fertile_land
            ),
            LandItem(
                id = "LD-02",
                title = "12 Acres Deep Black Cotton Soil",
                titleHi = "12 एकड़ गहरी काली मिट्टी (रेगुर)",
                acreage = 12.0,
                soilType = "Black Cotton (काली मिट्टी)",
                soilTypeHi = "काली कपासी मिट्टी (रेगुर)",
                irrigationSource = "Perennial Farm Pond + Drip Setup",
                irrigationSourceHi = "बारहमासी खेत तालाब + ड्रिप सिंचाई प्रणाली",
                leasePricePerYear = 26000,
                tenureYears = 3,
                ownerName = "Gopalrao Deshmukh",
                ownerPhone = "+91 94221 67890",
                village = "Akola Rural",
                district = "Akola",
                state = "Maharashtra",
                suitableCrops = listOf("Cotton", "Soybean", "Gram (Chana)", "Pigeon Pea (Tur)"),
                imageRes = R.drawable.img_fertile_land
            ),
            LandItem(
                id = "LD-03",
                title = "4 Acres Organic Horticulture Land",
                titleHi = "4 एकड़ प्रमाणित जैविक बागवानी भूमि",
                acreage = 4.0,
                soilType = "Red Loam (लाल दोमट)",
                soilTypeHi = "लाल दोमट मिट्टी (उत्तम जल निकास)",
                irrigationSource = "Dedicated Solar Pump Borewell",
                irrigationSourceHi = "समर्पित 5 एचपी सौर बोरवेल (24x7)",
                leasePricePerYear = 35000,
                tenureYears = 5,
                ownerName = "Anil Narang",
                ownerPhone = "+91 98220 55198",
                village = "Dindori",
                district = "Nashik",
                state = "Maharashtra",
                suitableCrops = listOf("Pomegranate", "Grapes", "Onion", "Tomatoes"),
                imageRes = R.drawable.img_fertile_land
            ),
            LandItem(
                id = "LD-04",
                title = "8 Acres Fenced Farm Parcel",
                titleHi = "8 एकड़ तारबंदी सहित समतल कृषि खेत",
                acreage = 8.0,
                soilType = "Sandy Loam (बलुई दोमट)",
                soilTypeHi = "बलुई दोमट (आलू व सरसों हेतु उत्तम)",
                irrigationSource = "Double Borewell with Electricity Connection",
                irrigationSourceHi = "दोहरे बोरवेल 3-फेज कृषि बिजली कनेक्शन सहित",
                leasePricePerYear = 22000,
                tenureYears = 1,
                ownerName = "Chaudhary Ramswaroop",
                ownerPhone = "+91 98280 12099",
                village = "Kotputli",
                district = "Jaipur Rural",
                state = "Rajasthan",
                suitableCrops = listOf("Mustard", "Wheat", "Bajra", "Green Fodder"),
                imageRes = null
            )
        )
    }

    private fun getInitialPolicies(): List<PolicyItem> {
        return listOf(
            PolicyItem(
                id = "POL-01",
                schemeCode = "PM-KISAN",
                title = "PM Kisan Samman Nidhi Yojana",
                titleHi = "प्रधानमंत्री किसान सम्मान निधि योजना",
                ministry = "Ministry of Agriculture & Farmers Welfare",
                ministryHi = "कृषि एवं किसान कल्याण मंत्रालय",
                subsidyBenefit = "₹6,000 / Year (3 DBT Installments of ₹2,000)",
                subsidyBenefitHi = "₹6,000 प्रति वर्ष (₹2,000 की 3 सीधी बैंक किस्तें)",
                category = "Income Support",
                categoryHi = "आय सहायता",
                simplifiedSummaryEn = "Direct cash benefit transferred directly into farmer bank accounts every 4 months to assist with seed, fertilizer, and agricultural inputs.",
                simplifiedSummaryHi = "बीज, खाद और कृषि आवश्यकताओं के लिए हर 4 महीने में सीधे किसान के बैंक खाते में ₹2,000 की नकद सहायता।",
                eligibilityEn = listOf(
                    "All landholding farmer families with cultivable land record",
                    "Aadhaar card linked with active bank account",
                    "Mandatory e-KYC completed on portal or CSC"
                ),
                eligibilityHi = listOf(
                    "खेती योग्य भूमि के सभी भू-स्वामी किसान परिवार",
                    "आधार कार्ड से लिंक सक्रिय बैंक खाता",
                    "पोर्टल या जन सेवा केंद्र पर ई-केवाईसी पूर्ण होना अनिवार्य"
                ),
                documentsEn = listOf("Aadhaar Card", "Land Ownership Record (Khatauni/Khasra)", "Bank Passbook with IFSC"),
                documentsHi = listOf("आधार कार्ड", "खसरा/खतौनी की नकल", "बैंक पासबुक (IFSC कोड सहित)"),
                officialPortalUrl = "https://pmkisan.gov.in",
                tollFreeNumber = "155261"
            ),
            PolicyItem(
                id = "POL-02",
                schemeCode = "PMFBY",
                title = "Pradhan Mantri Fasal Bima Yojana",
                titleHi = "प्रधानमंत्री फसल बीमा योजना (PMFBY)",
                ministry = "Ministry of Agriculture & Farmers Welfare",
                ministryHi = "कृषि एवं किसान कल्याण मंत्रालय",
                subsidyBenefit = "Comprehensive Crop Insurance: Premium only 1.5% - 2%",
                subsidyBenefitHi = "फसल नुकसान बीमा: किसान प्रीमियम केवल 1.5% से 2%",
                category = "Crop Insurance",
                categoryHi = "फसल बीमा",
                simplifiedSummaryEn = "Shields farmers against crop failure due to drought, unseasonal rain, pests, hail, and floods with fast digitized claim settlements.",
                simplifiedSummaryHi = "सूखा, ओलावृष्टि, कीट या बेमौसम बारिश से फसल बर्बाद होने पर पूरा वित्तीय मुआवजा। किसान को केवल 1.5-2% नाममात्र प्रीमियम देना होता है।",
                eligibilityEn = listOf(
                    "All farmers cultivating notified crops in notified areas",
                    "Both loanee (KCC holders) and non-loanee farmers eligible",
                    "Intimation within 72 hours of localized natural calamities"
                ),
                eligibilityHi = listOf(
                    "अधिसूचित क्षेत्र में अधिसूचित फसल बोने वाले सभी किसान",
                    "केसीसी ऋणी व गैर-ऋणी दोनों किसान पात्र",
                    "स्थानीय आपदा पर 72 घंटे के भीतर सूचना देना अनिवार्य"
                ),
                documentsEn = listOf("Sowing Certificate / Patwari Report", "Land Record", "Aadhaar Card", "Bank Account Details"),
                documentsHi = listOf("बुवाई प्रमाण पत्र / पटवारी रिपोर्ट", "भू-अभिलेख (जमाबंदी)", "आधार कार्ड", "बैंक पासबुक"),
                officialPortalUrl = "https://pmfby.gov.in",
                tollFreeNumber = "1800-180-1551"
            ),
            PolicyItem(
                id = "POL-03",
                schemeCode = "SMAM",
                title = "Sub-Mission on Agricultural Mechanization",
                titleHi = "कृषि यंत्रीकरण उप-मिशन (SMAM सब्सिडी)",
                ministry = "Ministry of Agriculture & Farmers Welfare",
                ministryHi = "कृषि एवं किसान कल्याण मंत्रालय",
                subsidyBenefit = "50% to 80% Subsidy on Tractors & Implements",
                subsidyBenefitHi = "ट्रैक्टर, रोटावेटर व कृषि यंत्रों पर 50% से 80% तक अनुदान",
                category = "Machinery Subsidy",
                categoryHi = "यंत्र सब्सिडी",
                simplifiedSummaryEn = "Provides massive government subsidies for purchasing modern agricultural machinery, rotavators, laser land levelers, and establishing Custom Hiring Centres (CHC).",
                simplifiedSummaryHi = "आधुनिक कृषि उपकरण जैसे ट्रैक्टर, रोटावेटर, रीपर, सुपर सीडर खरीदने या कस्टम हायरिंग सेंटर खोलने पर 50% से 80% तक की छूट।",
                eligibilityEn = listOf(
                    "Small and marginal farmers, SC/ST, and women farmers given high priority",
                    "Farmer must own agricultural land",
                    "Registration on state DBT Agriculture portal"
                ),
                eligibilityHi = listOf(
                    "लघु एवं सीमांत किसान, महिला किसान व एससी/एसटी को विशेष प्राथमिकता",
                    "किसान के पास कृषि भूमि का स्वामित्व होना चाहिए",
                    "राज्य कृषि यंत्र डीबीटी पोर्टल पर पूर्व पंजीकरण"
                ),
                documentsEn = listOf("Khasra-Khatauni Copy", "Aadhaar Card", "Caste Certificate (if applicable)", "Quotation from Authorized Dealer"),
                documentsHi = listOf("खसरा-खतौनी की नकल", "आधार कार्ड", "अधिकृत डीलर का कोटेशन", "बैंक खाता विवरण"),
                officialPortalUrl = "https://agrimachinery.nic.in",
                tollFreeNumber = "1800-180-1551"
            ),
            PolicyItem(
                id = "POL-04",
                schemeCode = "PMKSY",
                title = "PM Krishi Sinchayee Yojana (Per Drop More Crop)",
                titleHi = "प्रधानमंत्री कृषि सिंचाई योजना (प्रति बूंद अधिक फसल)",
                ministry = "Ministry of Jal Shakti & Agriculture",
                ministryHi = "जल शक्ति एवं कृषि मंत्रालय",
                subsidyBenefit = "Up to 55% Subsidy on Drip & Sprinkler Systems",
                subsidyBenefitHi = "ड्रिप व स्प्रिंकलर सिंचाई यंत्रों पर 55% तक अनुदान",
                category = "Irrigation",
                categoryHi = "सिंचाई योजना",
                simplifiedSummaryEn = "Promotes micro-irrigation (drip and sprinkler) to maximize water efficiency, reduce electricity cost, and boost crop productivity by 30-40%.",
                simplifiedSummaryHi = "पानी की बचत और 30-40% अधिक पैदावार के लिए ड्रिप (टपक) और स्प्रिंकलर (फव्वारा) सिंचाई तकनीक पर 55% तक सरकारी सब्सिडी।",
                eligibilityEn = listOf(
                    "Farmers with assured water source (borewell, pond, or canal)",
                    "Minimum land size as per state guidelines (usually 0.5 acre)",
                    "Subsidies directly credited or adjusted in equipment cost"
                ),
                eligibilityHi = listOf(
                    "सुनिश्चित जल स्रोत (बोरवेल, कुआं या तालाब) वाले किसान",
                    "न्यूनतम 0.5 एकड़ कृषि भूमि",
                    "राज्य बागवानी या कृषि विभाग के पोर्टल पर आवेदन"
                ),
                documentsEn = listOf("Water Source Proof / Electricity Bill", "Land Jamabandi", "Soil & Water Test Report", "Aadhaar Card"),
                documentsHi = listOf("जल स्रोत प्रमाण / बिजली बिल", "जमीन की जमाबंदी", "आधार कार्ड", "बैंक पासबुक"),
                officialPortalUrl = "https://pmksy.gov.in",
                tollFreeNumber = "1800-180-1551"
            ),
            PolicyItem(
                id = "POL-05",
                schemeCode = "KCC",
                title = "Kisan Credit Card (Low Interest Loan)",
                titleHi = "किसान क्रेडिट कार्ड (सस्ती ब्याज दर पर ऋण)",
                ministry = "Ministry of Finance & Agriculture",
                ministryHi = "वित्त एवं कृषि मंत्रालय",
                subsidyBenefit = "Effective 4% Interest Rate (3% Prompt Repayment Incentive)",
                subsidyBenefitHi = "प्रभावी ब्याज दर केवल 4% (समय पर भुगतान पर 3% छूट)",
                category = "Credit & Loan",
                categoryHi = "ऋण व साख",
                simplifiedSummaryEn = "Hassle-free institutional credit up to ₹3 Lakhs for crop cultivation, fertilizer, post-harvest expenses, and animal husbandry without high private interest rates.",
                simplifiedSummaryHi = "फसल लागत, खाद-बीज और पशुपालन के लिए ₹3 लाख तक का सस्ता संस्थागत ऋण। साहूकारों के भारी ब्याज से मुक्ति।",
                eligibilityEn = listOf(
                    "All individual farmers, joint borrowers, tenant farmers, and oral lessees",
                    "Age between 18 to 75 years",
                    "Valid land document or verified tenancy agreement"
                ),
                eligibilityHi = listOf(
                    "सभी व्यक्तिगत किसान, बटाईदार व पट्टेदार किसान",
                    "आयु 18 से 75 वर्ष",
                    "कृषि भूमि के वैध दस्तावेज"
                ),
                documentsEn = listOf("Filled Bank Application Form", "Land Record Copies", "Aadhaar Card & PAN Card", "Two Passport Photos"),
                documentsHi = listOf("बैंक आवेदन फॉर्म", "खतौनी/भू-अभिलेख", "आधार कार्ड एवं पैन कार्ड", "पासपोर्ट साइज फोटो"),
                officialPortalUrl = "https://myscheme.gov.in/schemes/kcc",
                tollFreeNumber = "1800-115-526"
            ),
            PolicyItem(
                id = "POL-06",
                schemeCode = "e-NAM",
                title = "National Agriculture Market (e-NAM)",
                titleHi = "राष्ट्रीय कृषि बाजार (ई-नाम पोर्टल)",
                ministry = "Ministry of Agriculture & Farmers Welfare",
                ministryHi = "कृषि एवं किसान कल्याण मंत्रालय",
                subsidyBenefit = "Zero Middleman Fees + Pan-India Online Bidding",
                subsidyBenefitHi = "शून्य दलाली + देशभर के व्यापारियों से सीधी ऑनलाइन बोली",
                category = "Direct Market",
                categoryHi = "बाज़ार सुधार",
                simplifiedSummaryEn = "An electronic trading platform that integrates 1,361+ wholesale APMC mandis across 23 states so farmers get competitive nation-wide prices for their harvest.",
                simplifiedSummaryHi = "देश की 1,361 से अधिक मंडियों को जोड़ने वाला ऑनलाइन मंच, जहां किसान अपनी उपज की गुणवत्ता जांच करवाकर देशभर के खरीदारों से उच्चतम भाव पा सकते हैं।",
                eligibilityEn = listOf(
                    "Any farmer registered at local e-NAM APMC mandi",
                    "Lot assaying (quality testing) available free of charge",
                    "Direct online payment into farmer bank account within 24 hours"
                ),
                eligibilityHi = listOf(
                    "स्थानीय ई-नाम मंडी में पंजीकृत कोई भी किसान",
                    "मंडी में फसल की मुफ्त गुणवत्ता जांच (Assaying)",
                    "24 घंटे के अंदर सीधे बैंक खाते में भुगतान"
                ),
                documentsEn = listOf("Farmer Mandi Registration / Gate Pass", "Aadhaar Card", "Bank Account Details"),
                documentsHi = listOf("मंडी गेट पास / किसान पंजीकरण", "आधार कार्ड", "बैंक खाता विवरण"),
                officialPortalUrl = "https://enam.gov.in",
                tollFreeNumber = "1800-270-0224"
            )
        )
    }
}
