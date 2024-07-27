package com.mobdeve.s11.group5.shopfreemobileapp

class DataHelper {
    companion object {
        fun initializeMarketData(): ArrayList<Market> {
            val data = ArrayList<Market>()
            data.add(
                Market(
                    "SM City Manila",
                    "Natividad Almeda-Lopez corner A. Villegas and, San Marcelino St, Ermita, Manila, Metro Manila",
                    null,
                    R.drawable.sm_manila
                )
            )
            data.add(
                Market(
                    "Robinsons Supermarket Otis",
                    "Level 1, Robinsons Otis, Brgy. 831 Zone 90 Dr. Paz Mendoza-Guanzon St, Paco, Manila, Metro Manila",
                    null,
                    R.drawable.robinsons_otis
                )
            )
            return data
        }
    }
}