package com.example.angkorchatproto.utils

import com.example.angkorchatproto.R

object CountryUtils {
    fun getCountries(): ArrayList<Country> {
        val countries = ArrayList<Country>()
        countries.add(Country("Cambodia", "KH", "855", R.drawable.img_flag_cambodia))
        countries.add(Country("Vietnam", "VN", "84", R.drawable.img_flag_vietnam))
        countries.add(Country("Thailand", "TH", "66", R.drawable.img_flag_thailand))
        countries.add(Country("Laos", "LA", "856", R.drawable.img_flag_laos))
        countries.add(Country("Malaysia", "MY", "60", R.drawable.img_flag_malaysia))
        countries.add(Country("Sigapore", "SG", "65", R.drawable.img_flag_singapore))
        countries.add(Country("Indonesia", "ID", "855", R.drawable.img_flag_indonesia))
        countries.add(Country("Philippines", "PH", "63", R.drawable.img_flag_philippines))
        countries.add(Country("China", "CN", "86", R.drawable.img_flag_china))
        countries.add(Country("South Korea", "KR", "82", R.drawable.img_flag_south_korea))
        countries.add(Country("Japan", "JP", "81", R.drawable.img_flag_japan))
        countries.add(Country("India", "IN", "91", R.drawable.img_flag_india))
        countries.add(Country("United States", "US", "1", R.drawable.img_flag_united_states))
        countries.add(Country("Canada", "CA", "1", R.drawable.img_flag_canada))
        countries.add(Country("United Kingdom", "GB", "44", R.drawable.img_flag_united_kingdom))
        countries.add(Country("France", "FR", "33", R.drawable.img_flag_france))
        countries.add(Country("Germany", "DE", "49", R.drawable.img_flag_germany))
        countries.add(Country("Australia", "AU", "61", R.drawable.img_flag_australia))
        countries.add(Country("New Zealand", "NZ", "64", R.drawable.img_flag_new_zealand))
//        return ArrayList(
//            countries.sortedBy { item -> App.instance.getString(item.name) }.stream().collect(
//                Collectors.toList()
//            )
//        )
        return countries
    }
}