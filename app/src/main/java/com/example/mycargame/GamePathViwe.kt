package com.example.mycargame

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View

//game view veriable
class Gameview (var c : Context,var gameTask: GameArea):View(c){
    private var myPaint:Paint?=null
    private var myspeed= 1
    private var mytime = 0
    private var myscore = 0
    private var myMousePosition = 0
    private val mycat = ArrayList<HashMap<String,Any>>()
//view size
    var viewWidth = 0
    var viewHeight = 0
    init{
        myPaint = Paint()

    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        viewWidth = this.measuredWidth
        viewHeight = this.measuredHeight
// random cat
        if(mytime%700<10 +myspeed){
            val map = HashMap<String,Any>()
            map["lane"]=(0..2).random()
            map["startTime"] = mytime
            mycat.add(map)
        }
//        cat size and speed
        mytime = mytime+10+myspeed
        val catWidth = viewWidth/5
        val catHeight = catWidth+10
        myPaint !!. style = Paint.Style.FILL
        val d = resources.getDrawable(R.drawable.mouse,null)
//        mouse image

//        mouse position
        d.setBounds(
            myMousePosition * viewWidth / 3 +viewWidth/15+25,
            viewHeight-2 - catHeight,
            myMousePosition*viewWidth/3+ viewWidth/15+catWidth-25,
            viewHeight-2
        )
        d.draw(canvas!!)

        myPaint!!.color = Color.GREEN
        var highScore = 0
        for (i in mycat.indices){
            try{
                val bomX = mycat[i]["lane"] as Int *viewWidth / 3 +viewWidth /15
                var bomY = mytime- mycat[i]["startTime"] as Int
                val d2 = resources.getDrawable(R.drawable.cat,null)


                d2.setBounds(
                    bomX + 25 , bomY - catHeight , bomX + catWidth - 25 , bomY

                )
                d2.draw(canvas)
                if(mycat[i]["lane"] as Int == myMousePosition){
                    if(bomY> viewHeight - 2 - catHeight && bomY < viewHeight - 2){

                        gameTask.closeGame(myscore)
                    }
                }
                if(bomY > viewHeight + catHeight)
                {
//                    check score and assign the high score
                    mycat.removeAt(i)
                    myscore++
                    myspeed = 1 + Math.abs(myscore /8)
                    if(myscore > highScore){
                        highScore = myscore
                    }
                }
            }
            catch (e:Exception){
                e.printStackTrace()
            }
        }
//        game view ake score and speed display
        myPaint!!.color = Color.WHITE
        myPaint!!.textSize = 40f
        canvas.drawText("Score : $myscore",80f, 80f, myPaint!!)
        canvas.drawText("Speed : $myspeed",380f, 80f, myPaint!!)
        invalidate()

    }
// touch control
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event!!.action){
            MotionEvent.ACTION_DOWN ->{
                val x1 = event.x
                if(x1< viewWidth/2){
                    if(myMousePosition>0){
                        myMousePosition--
                    }
                }
                if(x1>viewWidth / 2){
                    if(myMousePosition<2){
                        myMousePosition++
                    }
                }
                invalidate()
            }
            MotionEvent.ACTION_UP ->{

            }

        }
        return true
    }
}