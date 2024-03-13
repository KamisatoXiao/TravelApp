package com.example.mainactivity.objectClass

import android.graphics.drawable.Drawable
import android.widget.ImageView

class landscape(val imageURL:String,val name:String,val time:String,val ticket_price:String,val location:String,val description:String,val isChecked:Int)
class notes(
    val username: String,
    val landscapeName: String,
    val mainNote:String,
    val score:String,
    val likes:Int)
class setting(val settingItem:String)
class works(val title:String,username:String,send_time:String,main_text:String,likes:Int,views:Int,collected:Int)