package com.example.mainactivity.mainfragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.mainactivity.R
import com.example.mainactivity.adapter.LandscapeAdapter
import com.example.mainactivity.mainUI.LandscapeDetailActivity
import com.example.mainactivity.objectClass.landscape
import com.example.mainactivity.util.DatabaseUtils.Companion.connect2Database
import kotlin.concurrent.thread

class RouteFragment:Fragment(){
    private val landscapeList=ArrayList<landscape>()
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var adapter: LandscapeAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView=inflater.inflate(R.layout.fragment_route,container,false)
        val want2goListView:ListView=rootView.findViewById(R.id.want2goList)
        adapter=LandscapeAdapter(requireContext(),R.layout.item_mylist,landscapeList)
        swipeRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayout)
        initLandscape{
            want2goListView.adapter=adapter
            want2goListView.setOnItemClickListener { parent,view,position,id ->
                val landscape=landscapeList[position]
                val editor=requireContext().getSharedPreferences("index", Context.MODE_PRIVATE).edit()
                editor.putString("name",landscape.name)
                editor.putString("description",landscape.description)
                editor.putString("price",landscape.ticket_price)
                editor.putString("time",landscape.time)
                editor.putString("location",landscape.location)
                editor.putInt("isChecked",landscape.isChecked)
                editor.apply()
                val intent=Intent(requireContext(), LandscapeDetailActivity::class.java)
                startActivity(intent)
            }
        }
        swipeRefreshLayout.setOnRefreshListener {
            refreshLandscapeData()
        }
        return rootView
    }
    private fun initLandscape(callback:()->Unit){
        thread {
            val query="select * from tourist_spot where isChecked=1"
            val preparedStatement=connect2Database().prepareStatement(query)
            val resultSet=preparedStatement.executeQuery()
            while (resultSet.next()){
                val imageURL=resultSet.getString("description_pic_1")
                val name=resultSet.getString("name")
                val price=resultSet.getString("ticket_price")
                val description=resultSet.getString("description")
                val time=resultSet.getString("opening_time")
                val location=resultSet.getString("location")
                val isChecked=resultSet.getInt("isChecked")
                val landscape_add=landscape(imageURL,name,time,price,location,description,isChecked)
                landscapeList.add(landscape_add)
            }
            activity?.runOnUiThread {
                callback.invoke()
            }
            resultSet.close()
            connect2Database().close()
        }
    }
    private fun refreshLandscapeData(){
        landscapeList.clear()
        initLandscape {
            adapter.notifyDataSetChanged()
            swipeRefreshLayout.isRefreshing = false
        }
    }
}