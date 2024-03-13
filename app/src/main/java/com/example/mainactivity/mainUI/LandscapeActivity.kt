package com.example.mainactivity.mainUI

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.example.mainactivity.R
import com.example.mainactivity.adapter.LandscapeListViewAdapter
import com.example.mainactivity.objectClass.landscape
import com.example.mainactivity.util.DatabaseUtils.Companion.connect2Database
import kotlin.concurrent.thread

class LandscapeActivity : AppCompatActivity() {
    private val landscapeList=ArrayList<landscape>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landscape)
        val listView:ListView=findViewById(R.id.landscapeListView)
        val adapter=LandscapeListViewAdapter(this, R.layout.item,landscapeList)
        initLandscape{
            listView.adapter=adapter
            listView.setOnItemClickListener { parent,view,position,id ->
                val landscape=landscapeList[position]
                val editor=getSharedPreferences("index",Context.MODE_PRIVATE).edit()
                editor.putString("name",landscape.name)
                editor.putString("description",landscape.description)
                editor.putString("price",landscape.ticket_price)
                editor.putString("time",landscape.time)
                editor.putString("location",landscape.location)
                editor.apply()
                val intent=Intent(this, LandscapeDetailActivity::class.java)
                startActivity(intent)
            }
        }
    }
    private fun initLandscape(callback:()->Unit){
        val preferences=getSharedPreferences("index",Context.MODE_PRIVATE)
        val province=preferences.getString("province","")
        thread {
            val query="select * from tourist_spot where city=?"
            val preparedStatement= connect2Database().prepareStatement(query)
            preparedStatement.setString(1,province)
            val resultSet=preparedStatement.executeQuery()
            while(resultSet.next()){
                val imageURL=resultSet.getString("description_pic_1")
                val name=resultSet.getString("name")
                val price=resultSet.getString("ticket_price")
                val description=resultSet.getString("description")
                val time=resultSet.getString("opening_time")
                val location=resultSet.getString("location")
                val isChecked=resultSet.getInt("isChecked")
                val landscape_add=landscape(imageURL,name,time,price,location,description, isChecked)
                landscapeList.add(landscape_add)
            }
            runOnUiThread{
                callback.invoke()
            }
            resultSet.close()
            connect2Database().close()
        }
    }
}