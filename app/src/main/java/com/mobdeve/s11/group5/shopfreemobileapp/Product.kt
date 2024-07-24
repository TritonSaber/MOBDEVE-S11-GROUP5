package com.mobdeve.s11.group5.shopfreemobileapp

data class Product (
    public var pId: Long,
    public var pName: String,
    public var pLocId: Long,
    public var pPrice: Double,
    public var imageUri: Int,
    public var pQuantity: Int?,
    public var pDesc: String,
    public var pCategory: String,
    public var pPerWeight: String?
)
