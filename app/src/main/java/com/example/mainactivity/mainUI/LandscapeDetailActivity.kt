package com.example.mainactivity.mainUI

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.mainactivity.R
import com.example.mainactivity.mainUI.landscapefragment.LandscapeDescriptionFragment
import com.example.mainactivity.mainUI.landscapefragment.LandscapeNoteFragment
import com.example.mainactivity.util.DatabaseUtils.Companion.connect2Database
import java.util.concurrent.CompletableFuture
import kotlin.concurrent.thread

class LandscapeDetailActivity : AppCompatActivity() {
    private lateinit var landscapeDescriptionFragment: LandscapeDescriptionFragment
    private lateinit var landscapeNoteFragment: LandscapeNoteFragment
    private lateinit var settingArea:LinearLayout
    private lateinit var giveComment:TextView
    private lateinit var add2MyList:TextView
    private lateinit var move2MyList:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landscape_detail)
        supportActionBar?.hide()
        val preferences=getSharedPreferences("index", Context.MODE_PRIVATE)
        val name=preferences.getString("name","")
        val time=preferences.getString("time","")
        val price=preferences.getString("price","")
        val location=preferences.getString("location","")
        val landscapeName:TextView=findViewById(R.id.landscape_name)
        val landscapePic:ImageView=findViewById(R.id.landscape_image)
        val landscapeTime:TextView=findViewById(R.id.landscape_time)
        val landscapeLocation:TextView=findViewById(R.id.landscape_location)
        val landscapePrice:TextView=findViewById(R.id.landscape_price)
        val btn_description:TextView=findViewById(R.id.tv_description)
        val btn_note:TextView=findViewById(R.id.tv_note)
        val moreSettingButton:ImageView=findViewById(R.id.moreSetting)
        giveComment=findViewById(R.id.giveComment)
        add2MyList=findViewById(R.id.add2myList)
        landscapeName.text=name
        landscapeTime.text=time
        landscapePrice.text=price
        landscapeLocation.text=location
        landscapeDescriptionFragment= LandscapeDescriptionFragment()
        landscapeNoteFragment= LandscapeNoteFragment()
        supportFragmentManager.beginTransaction().add(R.id.description,landscapeDescriptionFragment).commit()
        move2MyList=findViewById(R.id.move2myList)
        getIsCheckedID(name).thenAccept{isChecked->
            if (isChecked==1){
                add2MyList.visibility=View.GONE
                move2MyList.visibility=View.VISIBLE
            }else if(isChecked==0){
                add2MyList.visibility=View.VISIBLE
                move2MyList.visibility=View.GONE
            }
        }
        btn_description.setOnClickListener {
            val fragmentManager=supportFragmentManager
            val transaction=fragmentManager.beginTransaction()
            transaction.replace(R.id.description,landscapeDescriptionFragment)
            transaction.commit()
        }
        btn_note.setOnClickListener {
            val fragmentManager=supportFragmentManager
            val transaction=fragmentManager.beginTransaction()
            transaction.replace(R.id.description,landscapeNoteFragment)
            transaction.commit()
        }
        moreSettingButton.setOnClickListener {
            settingArea=findViewById(R.id.settingArea)
            if(settingArea.visibility==View.GONE){
                settingArea.visibility=View.VISIBLE
            }else{
                settingArea.visibility=View.GONE
            }
        }
        giveComment.setOnClickListener {
            val intent=Intent(this,EvaluateDetailActivity::class.java)
            startActivity(intent)
        }
        add2MyList.setOnClickListener {
            settingArea=findViewById(R.id.settingArea)
            move2MyList=findViewById(R.id.move2myList)
            changeLandscapeIsChecked(name)
            Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show()
            settingArea.visibility=View.GONE
            add2MyList.visibility=View.GONE
            move2MyList.visibility=View.VISIBLE
        }
        move2MyList.setOnClickListener {
            settingArea=findViewById(R.id.settingArea)
            move2MyList=findViewById(R.id.move2myList)
            changeLandscapeIsNotChecked(name)
            Toast.makeText(this, "移除成功", Toast.LENGTH_SHORT).show()
            settingArea.visibility=View.GONE
            add2MyList.visibility=View.VISIBLE
            move2MyList.visibility=View.GONE
        }
        thread {
            val query="select * from tourist_spot where name=?"
            val preparedStatement=connect2Database().prepareStatement(query)
            preparedStatement.setString(1,name)
            val resultSet=preparedStatement.executeQuery()
            while(resultSet.next()){
                val imageURL=resultSet.getString("description_pic_2")
                runOnUiThread {
                    Glide.with(applicationContext)
                        .load(imageURL)
                        .into(landscapePic)
                }
            }
            resultSet.close()
            preparedStatement.close()
            connect2Database().close()
        }
    }
    private fun changeLandscapeIsChecked(name:String?){
        thread {
            val query="update tourist_spot set isChecked=? where name=?"
            val preparedStatement=connect2Database().prepareStatement(query)
            preparedStatement.setInt(1,1)
            preparedStatement.setString(2,name)
            preparedStatement.executeUpdate()
            preparedStatement.close()
            connect2Database().close()
        }
    }
    private fun changeLandscapeIsNotChecked(name:String?){
        thread {
            val query="update tourist_spot set isChecked=? where name=?"
            val preparedStatement=connect2Database().prepareStatement(query)
            preparedStatement.setInt(1,0)
            preparedStatement.setString(2,name)
            preparedStatement.executeUpdate()
            preparedStatement.close()
            connect2Database().close()
        }
    }
    private fun getIsCheckedID(name: String?):CompletableFuture<Int> {
        val future=CompletableFuture<Int>()
        thread {
            val query="select isChecked from tourist_spot where name=?"
            val preparedStatement=connect2Database().prepareStatement(query)
            preparedStatement.setString(1,name)
            val resultSet=preparedStatement.executeQuery()
            while (resultSet.next()){
                val isChecked=resultSet.getInt("isChecked")
                future.complete(isChecked)
            }
            preparedStatement.close()
            connect2Database().close()
        }
        return future
    }
}