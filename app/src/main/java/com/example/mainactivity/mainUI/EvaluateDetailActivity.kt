package com.example.mainactivity.mainUI

import android.app.Activity
import android.os.Bundle
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.RatingBar.OnRatingBarChangeListener
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.mainactivity.R
import com.example.mainactivity.util.DatabaseUtils.Companion.connect2Database
import java.text.DecimalFormat
import kotlin.concurrent.thread
import kotlin.random.Random

class EvaluateDetailActivity : AppCompatActivity() {
    private lateinit var mainScore: RatingBar
    private lateinit var sceneScore: RatingBar
    private lateinit var interestScore: RatingBar
    private lateinit var worth2goScore: RatingBar
    private lateinit var mainEvaluate: TextView
    private lateinit var sceneEvaluate: TextView
    private lateinit var interestEvaluate: TextView
    private lateinit var worth2goEvaluate: TextView
    private lateinit var note2send: EditText
    private lateinit var otherScoreArea: LinearLayout
    private lateinit var correctWordsCount:TextView
    private lateinit var area3:LinearLayout
    private var ratingBars: MutableList<RatingBar?> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evaluate_detail)
        val sharedPreferences = getSharedPreferences("index", MODE_PRIVATE)
        val name = sharedPreferences.getString("name", "")
        val tl_head = findViewById<Toolbar>(R.id.tl_head)
        tl_head.title = name
        tl_head.setNavigationIcon(R.drawable.back)
        setSupportActionBar(tl_head)
        otherScoreArea = findViewById(R.id.otherScoreArea)
        mainScore = findViewById(R.id.mainScore)
        sceneScore = findViewById(R.id.sceneScore)
        interestScore = findViewById(R.id.interestScore)
        worth2goScore = findViewById(R.id.worth2goScore)
        mainEvaluate = findViewById(R.id.mainEvaluate)
        sceneEvaluate = findViewById(R.id.sceneEvaluate)
        interestEvaluate = findViewById(R.id.interestEvaluate)
        worth2goEvaluate = findViewById(R.id.worth2goEvaluate)
        note2send = findViewById(R.id.note2send)
        ratingBars.add(mainScore)
        ratingBars.add(sceneScore)
        ratingBars.add(interestScore)
        ratingBars.add(worth2goScore)
        mainScore.setOnRatingBarChangeListener(OnRatingBarChangeListener { ratingBar, rating, fromUser ->
            if (rating == 0f) {
                otherScoreArea.setVisibility(View.GONE)
                mainEvaluate.setText("点击来评分")
            } else if (rating == 1f) {
                otherScoreArea.setVisibility(View.VISIBLE)
                mainEvaluate.setText("不佳")
            } else if (rating == 2f) {
                otherScoreArea.setVisibility(View.VISIBLE)
                mainEvaluate.setText("一般")
            } else if (rating == 3f) {
                otherScoreArea.setVisibility(View.VISIBLE)
                mainEvaluate.setText("不错")
            } else if (rating == 4f) {
                otherScoreArea.setVisibility(View.VISIBLE)
                mainEvaluate.setText("满意")
            } else if (rating == 5f) {
                otherScoreArea.setVisibility(View.VISIBLE)
                mainEvaluate.setText("超棒")
            }
        })
        sceneScore.setOnRatingBarChangeListener(OnRatingBarChangeListener { ratingBar, rating, fromUser ->
            if (rating == 0f) {
                sceneEvaluate.setText("点击来评分")
            } else if (rating == 1f) {
                sceneEvaluate.setText("不佳")
            } else if (rating == 2f) {
                sceneEvaluate.setText("一般")
            } else if (rating == 3f) {
                sceneEvaluate.setText("不错")
            } else if (rating == 4f) {
                sceneEvaluate.setText("满意")
            } else if (rating == 5f) {
                sceneEvaluate.setText("超棒")
            }
        })
        interestScore.setOnRatingBarChangeListener(OnRatingBarChangeListener { ratingBar, rating, fromUser ->
            if (rating == 0f) {
                interestEvaluate.setText("点击来评分")
            } else if (rating == 1f) {
                interestEvaluate.setText("不佳")
            } else if (rating == 2f) {
                interestEvaluate.setText("一般")
            } else if (rating == 3f) {
                interestEvaluate.setText("不错")
            } else if (rating == 4f) {
                interestEvaluate.setText("满意")
            } else if (rating == 5f) {
                interestEvaluate.setText("超棒")
            }
        })
        worth2goScore.setOnRatingBarChangeListener(OnRatingBarChangeListener { ratingBar, rating, fromUser ->
            if (rating == 0f) {
                worth2goEvaluate.setText("点击来评分")
            } else if (rating == 1f) {
                worth2goEvaluate.setText("不佳")
            } else if (rating == 2f) {
                worth2goEvaluate.setText("一般")
            } else if (rating == 3f) {
                worth2goEvaluate.setText("不错")
            } else if (rating == 4f) {
                worth2goEvaluate.setText("满意")
            } else if (rating == 5f) {
                worth2goEvaluate.setText("超棒")
            }
        })
        note2send.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val wordCounts=note2send.length()
                correctWordsCount=findViewById(R.id.correctWordsCounts)
                area3=findViewById(R.id.area3)
                correctWordsCount.text=(15-wordCounts).toString()
                if (wordCounts>=15){
                    area3.visibility=View.GONE
                }else{
                    area3.visibility=View.VISIBLE
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val notes = note2send.text.toString()
        val id = item.itemId
        if (id == R.id.send) {
            var totalRating = 0f
            val numRatingBars = ratingBars.size
            for (ratingBar in ratingBars) {
                totalRating += ratingBar!!.rating
            }
            val averageRating = totalRating / numRatingBars
            val decimalFormat = DecimalFormat("0.0")
            val formattedAverageRating = decimalFormat.format(averageRating.toDouble())
            val sharedPreferences = getSharedPreferences("index", MODE_PRIVATE)
            val landscapeName= sharedPreferences.getString("name", "")
            addNote2Database(landscapeName, notes, formattedAverageRating,this)
        } else if (id == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
    private fun addNote2Database(name:String?, notes:String, score:String,activity:Activity) {
        val count=15-(notes.length)
        if (notes.isEmpty()&&score=="0.0"){
            Toast.makeText(this, "请填写所有必填信息以完成点评！", Toast.LENGTH_SHORT).show()
        } else if(notes.length in 0..14&&score!="0.0"){
            Toast.makeText(this, "还差"+count+"字即可发表评论", Toast.LENGTH_SHORT).show()
        }else if (notes.length>=15){
            val username="匿名用户"+Random.nextInt(10000)
            val likes=Random.nextInt(1000).toString()
            thread {
                val query="insert into landscape_note(noteID,landscape_name,username,mainNote,score,likes) values(DEFAULT,?,?,?,?,?)"
                val preparedStatement=connect2Database().prepareStatement(query)
                preparedStatement.setString(1,name)
                preparedStatement.setString(2,username)
                preparedStatement.setString(3,notes)
                preparedStatement.setString(4,score)
                preparedStatement.setString(5,likes)
                preparedStatement.executeUpdate()
                Looper.prepare()
                Toast.makeText(this, "提交成功", Toast.LENGTH_SHORT).show()
                activity.finish()
                Looper.loop()
                preparedStatement.close()
                connect2Database().close()
            }
        }
    }
}