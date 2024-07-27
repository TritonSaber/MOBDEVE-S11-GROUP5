package com.mobdeve.s11.group5.shopfreemobileapp

data class Transaction (
    public var tUserid: String?,
    public var tDate: String,
    public var tLocId: Long?,
    public var tLocName: String,
    public var tTotal: Double,
    public var tCart: ArrayList<Product>,
    public var tCompleted: Boolean?
)