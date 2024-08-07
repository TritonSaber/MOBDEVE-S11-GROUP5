package com.mobdeve.s11.group5.shopfreemobileapp

import android.net.Uri

data class Market (
    public var mName: String,
    public var mLoc: String,
    public var mDesc: String?,
    public var mImageURI: Uri?,
    public var mStorageURL: String?
)