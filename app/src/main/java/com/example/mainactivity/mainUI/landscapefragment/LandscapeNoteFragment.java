package com.example.mainactivity.mainUI.landscapefragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.mainactivity.R;
import com.example.mainactivity.adapter.NoteListViewAdapter;
import com.example.mainactivity.objectClass.notes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class LandscapeNoteFragment extends Fragment {
    private ArrayList<notes> notesList=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_note,null);
        ListView listView=rootView.findViewById(R.id.NoteListView);
        NoteListViewAdapter adapter=new NoteListViewAdapter(requireContext(),R.layout.item_note,notesList);
        initLandscapeNote(()->
            listView.setAdapter(adapter)
        );
        return rootView;
    }
    private void initLandscapeNote(Runnable callback){
        SharedPreferences preferences=requireContext().getSharedPreferences("index", Context.MODE_PRIVATE);
        String landscapeName=preferences.getString("name","");
        new Thread(()->{
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection= DriverManager.getConnection(
                        "jdbc:mysql://rm-bp1c01kn7va4r8nlhqo.mysql.rds.aliyuncs.com:3306/touristapp?useSSL=true",
                        "kamisatoxiao",
                        "lihaipeng010717@");
                String query="select * from landscape_note where landscape_name=?";
                PreparedStatement preparedStatement=connection.prepareStatement(query);
                preparedStatement.setString(1,landscapeName);
                ResultSet resultSet=preparedStatement.executeQuery();
                while (resultSet.next()){
                    String username=resultSet.getString("username");
                    String mainNote=resultSet.getString("mainNote");
                    String score=resultSet.getString("score");
                    int likes=resultSet.getInt("likes");
                    notes note_add=new notes(username,landscapeName,mainNote,score,likes);
                    notesList.add(note_add);
                }
                requireActivity().runOnUiThread(callback);
                resultSet.close();
                connection.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }).start();
    }
}
