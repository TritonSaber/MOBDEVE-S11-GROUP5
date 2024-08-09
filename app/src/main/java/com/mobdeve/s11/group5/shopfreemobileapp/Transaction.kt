package com.mobdeve.s11.group5.shopfreemobileapp

data class Transaction (
    public var tUserid: String?,
    public var tDate: String,
    public var tTotal: Double?,
    public var tCart: ArrayList<CartItem>,
    public var tCompleted: Boolean?
)