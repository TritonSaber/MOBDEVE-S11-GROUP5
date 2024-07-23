package com.mobdeve.s11.group5.shopfreemobileapp

import android.net.Uri

data class Product (
    public var pId: Long,
    public var pName: String,
    public var pLocId: Long,
    public var pPrice: Double,
    public var imageUri: Uri,
    public var pQuantity: Int?,
    public var pDesc: String
)
