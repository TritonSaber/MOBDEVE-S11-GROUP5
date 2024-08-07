package com.mobdeve.s11.group5.shopfreemobileapp

import android.net.Uri

data class Product (
    public var pName: String,
    public var pLocId: String,
    public var pPrice: Double,
    public var pStorageURL: String, //downloadURL
    public var pImageUri: Uri?, //downloaded URI
    public var pQuantity: Int?,
    public var pDesc: String?,
    public var pCategory: String,
    public var pPerWeight: String?
)
