package com.example.data.schema

object JsonSchemaProvider {

    val FULL_DATABASE_SCHEMA_JSON: String = """
{
  "${'$'}schema": "https://json-schema.org/draft/2020-12/schema",
  "title": "KisanSetuAgriTechDatabaseSchema",
  "description": "Unified database schema to manage Users, Equipment Listings, Produce (Broker Hatao), Land Lease, and Government Policies for Indian Rural Agriculture",
  "version": "1.0.0",
  "entities": {
    "Users": {
      "type": "object",
      "description": "Registered farmers, equipment owners, and institutional buyers",
      "properties": {
        "user_id": { "type": "string", "example": "usr_kisan_8912" },
        "full_name": { "type": "string", "example": "Ramesh Kumar Patel" },
        "phone_number": { "type": "string", "pattern": "^[6-9][0-9]{9}$", "example": "9876543210" },
        "role": { "type": "string", "enum": ["FARMER", "EQUIPMENT_OWNER", "LANDOWNER", "BULK_BUYER", "ADMIN"] },
        "language_preference": { "type": "string", "enum": ["hi", "en"], "default": "hi" },
        "kyc_verification": {
          "type": "object",
          "properties": {
            "aadhaar_verified": { "type": "boolean", "default": true },
            "kisan_credit_card_no": { "type": "string", "nullable": true },
            "verified_at": { "type": "string", "format": "date-time" }
          }
        },
        "location": {
          "type": "object",
          "properties": {
            "village": { "type": "string", "example": "Sujalpur" },
            "district": { "type": "string", "example": "Sehore" },
            "state": { "type": "string", "example": "Madhya Pradesh" },
            "pincode": { "type": "string", "example": "466116" },
            "coordinates": { "type": "array", "items": { "type": "number" }, "example": [23.18, 76.85] }
          }
        },
        "created_at": { "type": "string", "format": "date-time" }
      },
      "required": ["user_id", "full_name", "phone_number", "role", "location"]
    },
    "EquipmentListings": {
      "type": "object",
      "description": "Agricultural machinery, tractors, implements available for rental",
      "properties": {
        "equipment_id": { "type": "string", "example": "eq_tr_575" },
        "owner_id": { "type": "string", "description": "Foreign key -> Users.user_id" },
        "equipment_type": { "type": "string", "enum": ["TRACTOR", "HARVESTER", "ROTAVATOR", "SEED_DRILL", "SOLAR_PUMP", "DRONE_SPRAYER"] },
        "model_name": { "type": "string", "example": "Mahindra 575 DI Sarpanch" },
        "horsepower": { "type": "string", "example": "45 HP" },
        "pricing": {
          "type": "object",
          "properties": {
            "daily_rate_inr": { "type": "integer", "example": 1200 },
            "hourly_rate_inr": { "type": "integer", "example": 350 },
            "security_deposit_inr": { "type": "integer", "example": 2000 }
          },
          "required": ["daily_rate_inr"]
        },
        "specifications": {
          "type": "object",
          "properties": {
            "fuel_included": { "type": "boolean", "default": false },
            "driver_provided": { "type": "boolean", "default": true },
            "attachment_included": { "type": "string", "example": "Rotary Tiller & Trolley" }
          }
        },
        "is_available": { "type": "boolean", "default": true },
        "images": { "type": "array", "items": { "type": "string", "format": "uri" } }
      },
      "required": ["equipment_id", "owner_id", "equipment_type", "model_name", "pricing"]
    },
    "ProduceListings": {
      "type": "object",
      "description": "Direct Market (Broker Hatao) peer-to-peer crops listed by farmers",
      "properties": {
        "produce_id": { "type": "string", "example": "prod_wh_091" },
        "farmer_id": { "type": "string", "description": "Foreign key -> Users.user_id" },
        "crop_name": { "type": "string", "example": "Wheat" },
        "crop_name_hindi": { "type": "string", "example": "गेहूँ (शरबती)" },
        "variety": { "type": "string", "example": "Sharbati C-306" },
        "category": { "type": "string", "enum": ["GRAINS", "PULSES", "OILSEEDS", "VEGETABLES", "FRUITS", "SPICES"] },
        "price_per_quintal_inr": { "type": "integer", "example": 2850 },
        "benchmark_mandi_msp_inr": { "type": "integer", "example": 2275 },
        "quantity_quintals": { "type": "number", "example": 120.0 },
        "minimum_order_quintals": { "type": "number", "example": 5.0 },
        "quality_grade": { "type": "string", "enum": ["GRADE_A_PLUS", "GRADE_A", "STANDARD"] },
        "moisture_content_pct": { "type": "number", "example": 11.5 },
        "harvest_date": { "type": "string", "format": "date", "example": "2026-03-25" },
        "contact_preference": { "type": "string", "enum": ["DIRECT_CALL", "WHATSAPP", "IN_APP_CHAT"] }
      },
      "required": ["produce_id", "farmer_id", "crop_name", "price_per_quintal_inr", "quantity_quintals"]
    },
    "LandListings": {
      "type": "object",
      "description": "Agricultural land parcels available for seasonal or long-term lease",
      "properties": {
        "land_id": { "type": "string", "example": "land_mp_772" },
        "landowner_id": { "type": "string", "description": "Foreign key -> Users.user_id" },
        "title": { "type": "string", "example": "Fertile Canal-Fed Wheat Land" },
        "acreage": { "type": "number", "example": 5.0 },
        "soil_type": { "type": "string", "enum": ["ALLUVIAL", "BLACK_COTTON", "RED_LOAM", "SANDY_LOAM", "CLAY"] },
        "irrigation_facilities": {
          "type": "array",
          "items": { "type": "string" },
          "example": ["Canal Water 15-Day Turn", "Submersible Borewell with Solar Power"]
        },
        "lease_terms": {
          "type": "object",
          "properties": {
            "price_per_acre_year_inr": { "type": "integer", "example": 28000 },
            "tenure_years": { "type": "integer", "example": 2 },
            "payment_frequency": { "type": "string", "enum": ["ANNUAL_ADVANCE", "BI_ANNUAL", "CROP_SHARE_50_50"] },
            "security_advance_inr": { "type": "integer", "example": 15000 }
          },
          "required": ["price_per_acre_year_inr", "tenure_years"]
        },
        "fencing": { "type": "boolean", "default": true },
        "suitable_crops": { "type": "array", "items": { "type": "string" }, "example": ["Wheat", "Soybean", "Garlic", "Vegetables"] }
      },
      "required": ["land_id", "landowner_id", "acreage", "soil_type", "lease_terms"]
    },
    "GovernmentPolicies": {
      "type": "object",
      "description": "Government schemes, DBT income support, crop insurance & equipment subsidies",
      "properties": {
        "policy_id": { "type": "string", "example": "pol_pm_kisan_01" },
        "scheme_code": { "type": "string", "example": "PM-KISAN" },
        "official_title": { "type": "string", "example": "Pradhan Mantri Kisan Samman Nidhi" },
        "title_hindi": { "type": "string", "example": "प्रधानमंत्री किसान सम्मान निधि" },
        "ministry": { "type": "string", "example": "Ministry of Agriculture & Farmers Welfare" },
        "benefit_summary": { "type": "string", "example": "₹6,000 per year in 3 direct DBT installments of ₹2,000" },
        "subsidy_category": { "type": "string", "enum": ["INCOME_SUPPORT", "CROP_INSURANCE", "MACHINERY_SUBSIDY", "IRRIGATION", "CREDIT_LOAN"] },
        "eligibility_criteria": { "type": "array", "items": { "type": "string" } },
        "required_documents": { "type": "array", "items": { "type": "string" } },
        "portal_url": { "type": "string", "format": "uri", "example": "https://pmkisan.gov.in" },
        "helpline_toll_free": { "type": "string", "example": "155261 / 1800-115-526" }
      },
      "required": ["policy_id", "scheme_code", "official_title", "benefit_summary", "subsidy_category"]
    }
  }
}
    """.trimIndent()
}
