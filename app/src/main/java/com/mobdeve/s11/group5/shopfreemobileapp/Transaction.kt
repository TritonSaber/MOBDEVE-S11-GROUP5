package com.mobdeve.s11.group5.shopfreemobileapp

import java.util.Date

data class Transaction (
    public var tDate: Date,
    public var tLocId: Long,
    public var tLocName: String,
    public var tTotal: Double,
    public var tCart: ArrayList<Product>,
    public var tCompleted: Boolean
)