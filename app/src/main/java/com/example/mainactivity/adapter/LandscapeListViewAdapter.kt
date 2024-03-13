package com.example.mainactivity.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.mainactivity.R
import com.example.mainactivity.objectClass.landscape


class LandscapeListViewAdapter(activity:Activity,val landscapeId:Int,data:List<landscape>):
ArrayAdapter<landscape>(activity,landscapeId,data){
    inner class ViewHolder(val landscapePic:ImageView,val landscapeName:TextView,val landscapePrice:TextView,val landscapeDescription:TextView)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view:View
        val viewHolder:ViewHolder
        if (convertView==null){
            view=LayoutInflater.from(context).inflate(landscapeId,parent,false)
            val landscapePic:ImageView=view.findViewById(R.id.landscape_pic)
            val landscapeName:TextView=view.findViewById(R.id.landscape_name)
            val landscapePrice:TextView=view.findViewById(R.id.landscape_price)
            val landscapeDescription:TextView=view.findViewById(R.id.landscape_description)
            viewHolder=ViewHolder(landscapePic,landscapeName,landscapePrice,landscapeDescription)
            view.tag=viewHolder
        }else{
            view=convertView
            viewHolder=view.tag as ViewHolder
        }
        val landscape=getItem(position)
        if (landscape!=null){
//            viewHolder.landscapePic.setImageBitmap(landscape.pic)
            Glide.with(context).load(landscape.imageURL).into(viewHolder.landscapePic)
            viewHolder.landscapeName.text=landscape.name
            viewHolder.landscapePrice.text=landscape.ticket_price
            viewHolder.landscapeDescription.text=landscape.description
        }
        return view
    }
}