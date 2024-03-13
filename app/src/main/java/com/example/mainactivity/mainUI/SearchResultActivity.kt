package com.example.mainactivity.mainUI;

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View
import android.widget.ListView
import android.widget.TextView
import com.example.mainactivity.R
import com.example.mainactivity.adapter.LandscapeListViewAdapter
import com.example.mainactivity.objectClass.landscape
import java.sql.Connection
import java.sql.DriverManager
import kotlin.concurrent.thread


class SearchResultActivity:AppCompatActivity() {
    private val landscapeList=ArrayList<landscape>()
    private lateinit var noResult:TextView
    private lateinit var listView:ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)
        supportActionBar?.hide()
        noResult=findViewById(R.id.noResult)
        listView=findViewById(R.id.resultListView)
        val adapter=LandscapeListViewAdapter(this, R.layout.item,landscapeList)
        initLandscape {
            listView.adapter=adapter
            listView.setOnItemClickListener { parent, view, position, id ->
                val landscape=landscapeList[position]
                val editor=getSharedPreferences("index", Context.MODE_PRIVATE).edit()
                editor.putString("name",landscape.name)
                editor.putString("description",landscape.description)
                editor.putString("price",landscape.ticket_price)
                editor.putString("time",landscape.time)
                editor.putString("location",landscape.location)
                editor.apply()
                val intent= Intent(this, LandscapeDetailActivity::class.java)
                startActivity(intent)
            }
            if(landscapeList.isEmpty()){
                listView.visibility=View.GONE
                noResult.visibility=View.VISIBLE
            }
        }
    }
    private fun initLandscape(callback:()->Unit){
        val sentence=intent.getStringExtra("key")
        thread {
            Class.forName("com.mysql.jdbc.Driver")
            val connection: Connection = DriverManager.getConnection(
                   "jdbc:mysql://rm-bp1c01kn7va4r8nlhqo.mysql.rds.aliyuncs.com:3306/touristapp?useSSL=true",
                   "kamisatoxiao",
                   "lihaipeng010717@"
               )
            val query="select * from tourist_spot where name like ? or description like ?"
            val preparedStatement=connection.prepareStatement(query)
            val searchKeyword="%"+sentence+"%"
            preparedStatement.setString(1,searchKeyword)
            preparedStatement.setString(2,searchKeyword)
            val resultSet=preparedStatement.executeQuery()
            while (resultSet.next()){
                var imageURL=resultSet.getString("description_pic_1")
                val name=resultSet.getString("name")
                val price=resultSet.getString("ticket_price")
                val description=resultSet.getString("description")
                val time=resultSet.getString("opening_time")
                val location=resultSet.getString("location")
                val isChecked=resultSet.getInt("isChecked")
                val landscape_add=landscape(imageURL,name,time,price,location,description,isChecked)
                landscapeList.add(landscape_add)
            }
            runOnUiThread{
                callback.invoke()
            }
            resultSet.close()
            connection.close()
        }
    }
}