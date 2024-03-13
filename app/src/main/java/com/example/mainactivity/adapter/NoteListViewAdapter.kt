package com.example.mainactivity.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.mainactivity.R
import com.example.mainactivity.objectClass.notes

class NoteListViewAdapter(context:Context,val noteId:Int,data:List<notes>):
ArrayAdapter<notes>(context,noteId,data) {
    inner class ViewHolder(val username:TextView,val landscapeScore:TextView,val mainNote:TextView)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view:View
        val viewHolder:ViewHolder
        if(convertView==null){
            view=LayoutInflater.from(context).inflate(noteId,parent,false)
            val username:TextView=view.findViewById(R.id.username)
            val landscapeScore:TextView=view.findViewById(R.id.landscape_score)
            val mainNote:TextView=view.findViewById(R.id.mainNote)
            viewHolder=ViewHolder(username,landscapeScore,mainNote)
            view.tag=viewHolder
        }else{
            view=convertView
            viewHolder=view.tag as ViewHolder
        }
        val note=getItem(position)
        if (note!=null){
            viewHolder.username.text=note.username
            viewHolder.landscapeScore.text=note.score.toString()
            viewHolder.mainNote.text=note.mainNote
        }
        return view
    }
}