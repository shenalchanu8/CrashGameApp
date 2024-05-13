package com.example.mycargame

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : AppCompatActivity(),GameArea{
    /* variables and layouts*/
    private lateinit var rootLayout :LinearLayout
    private lateinit var startbtn :Button
    private lateinit var mGameview: Gameview
    private lateinit var score:TextView
    private lateinit var highScoreText: TextView
    private var highScore: Int = 0
    private lateinit var sharedPreferences: SharedPreferences /* want  save the score and create variable*/

    /* change to page to page*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences("my_pref", Context.MODE_PRIVATE)
        highScore = sharedPreferences.getInt("high_score", 0)  /*save the high score */

        /*viwe the variables*/
        startbtn = findViewById(R.id.mystartBtn)
        rootLayout = findViewById(R.id.rootLayout)
        score = findViewById(R.id.myscore)
        highScoreText = findViewById(R.id.myhighscore)
        mGameview = Gameview(this,this)

        startbtn.setOnClickListener {
            startGame()

        }
        updateHighScoreText()
    }

    /*to continue the start game*/
    private fun startGame() {
        mGameview = Gameview(this, this)
        mGameview.setBackgroundResource(R.drawable.bgm)/*Add the backgroud image*/
        rootLayout.addView(mGameview) /*Game viwe */
        startbtn.visibility = View.GONE /* GONE :- */
        score.visibility = View.GONE


        /* play the game and viwe score and high score  */
        val previousScore = sharedPreferences.getInt("current_score", 0)
        score.text = "Score: $previousScore"


        updateHighScoreText()
    }
    /* high score update */
    private fun updateHighScoreText() {
        highScoreText.text = "High Score: $highScore"
    }



    override fun closeGame(mScore: Int) {

        if (mScore > highScore) {   /* check the score higher than high score*/
            highScore = mScore
            sharedPreferences.edit().putInt("high_score", highScore).apply()
            updateHighScoreText()
        }


        sharedPreferences.edit().putInt("current_score", mScore).apply()
        score.text = "Score : $mScore"
        rootLayout.removeView(mGameview)
        startbtn.visibility = View.VISIBLE
        score.visibility = View.VISIBLE
    }

    /* game eka nawatha nawatha siduweeema like loop
    * */
    override fun onResume() {
        super.onResume()
        startGame()
    }
}