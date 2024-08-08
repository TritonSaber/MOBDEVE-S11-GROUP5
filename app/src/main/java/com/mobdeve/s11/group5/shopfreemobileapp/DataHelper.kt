package com.mobdeve.s11.group5.shopfreemobileapp

import android.net.Uri
import com.mobdeve.s11.group5.shopfreemobileapp.MainActivity.Companion.PACKAGE_NAME

class DataHelper {
    companion object {
        fun initializeMarketData(): ArrayList<Market> {
            val data = ArrayList<Market>()
            data.add(
                Market(
                    "SM City Manila",
                    "Natividad Almeda-Lopez corner A. Villegas and, San Marcelino St, Ermita, Manila, Metro Manila",
                    null,
                    parseFirestoreImageURL(R.drawable.sm_manila),
                    "images/${R.drawable.sm_manila}"
                )
            )
            data.add(
                Market(
                    "Robinsons Supermarket Otis",
                    "Level 1, Robinsons Otis, Brgy. 831 Zone 90 Dr. Paz Mendoza-Guanzon St, Paco, Manila, Metro Manila",
                    null,
                    parseFirestoreImageURL(R.drawable.robinsons_otis),
                    "images/${R.drawable.robinsons_otis}"
                )
            )
            return data
        }

        fun inititalizeProductData(): ArrayList<Product> {
            val data = ArrayList<Product>()

            data.add(
                Product(
                     "Honeycrisp Apple",
                    "Robinsons Supermarket Otis",
                    200.00,
                    "images/${R.drawable.honeycrisp}",
                    parseFirestoreImageURL(R.drawable.honeycrisp),
                    0,
                    null,
                    "Fruits",
                    "kg"
                )
            )
            data.add(
                Product(
                    "Honeycrisp Apple",
                    "SM City Manila",
                    150.00,
                    "images/${R.drawable.honeycrisp}",
                    parseFirestoreImageURL(R.drawable.honeycrisp),
                    0,
                    null,
                    "Fruits",
                    "kg"
                )
            )
            data.add(
                Product (
                    "Pork Lomo",
                    "SM City Manila",
                    325.00,
                    "images/${R.drawable.porklomo}",
                    parseFirestoreImageURL(R.drawable.porklomo),
                    0,
                    null,
                    "Meats",
                    "kg"
                )
            )
            data.add(
                Product (
                    "Pork Lomo",
                    "Robinsons Supermarket Otis",
                    325.00,
                    "images/${R.drawable.porklomo}",
                    parseFirestoreImageURL(R.drawable.porklomo),
                    0,
                    null,
                    "Meats",
                    "kg"
                )
            )
            data.add(
                Product (
                    "Broccoli",
                    "Robinsons Supermarket Otis",
                    325.00,
                    "images/${R.drawable.broccoli}",
                    parseFirestoreImageURL(R.drawable.broccoli),
                    0,
                    null,
                    "Vegetables",
                    "kg"
                )
            )
            data.add(
                Product (
                    "Broccoli",
                    "SM City Manila",
                    325.00,
                    "images/${R.drawable.broccoli}",
                    parseFirestoreImageURL(R.drawable.broccoli),
                    0,
                    null,
                    "Vegetables",
                    "kg"
                )
            )
            return data
        }

        fun initializeCategoryData() : ArrayList<Category> {
            var data = ArrayList<Category>()
            data.add(
                Category(
                    "Fruits",
                    parseFirestoreImageURL(R.drawable.category_fruits),
                    "images/${R.drawable.category_fruits}"
                )
            )
            data.add(
                Category(
                    "Meats",
                    parseFirestoreImageURL(R.drawable.category_meats),
                    "images/${R.drawable.category_meats}"
                )
            )
            data.add(
                Category(
                    "Vegetables",
                    parseFirestoreImageURL(R.drawable.category_vegetables),
                    "images/${R.drawable.category_vegetables}"
                )
            )
            return data
        }

        fun parseFirestoreImageURL(dr: Int) : Uri {
            var fileUri: Uri = Uri.parse("android.resource://$PACKAGE_NAME/$dr")

            return fileUri
        }
    }
}