package com.duman.livedatabase

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class UserInfo (val id:String, val nama:String, val status:String){
    constructor(): this("","",""){

    }
}
