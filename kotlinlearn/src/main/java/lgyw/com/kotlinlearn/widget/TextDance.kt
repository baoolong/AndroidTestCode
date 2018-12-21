package lgyw.com.kotlinlearn.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.TextView
import android.animation.ValueAnimator
import android.graphics.Rect
import android.view.animation.Animation
import android.support.v4.view.ViewCompat
import android.view.View


class TextDance :TextView{

    private var currentCharSequence:CharSequence?=null
    private var oldCharSequence:CharSequence?=null
    private val paint:Paint=Paint()
    private var textWidth:Float=0.0f
    private var textHeight=0.0f
    private var anim = ValueAnimator.ofFloat(0f, 1f)

    private val oldPositions:MutableList<Int> = mutableListOf()
    private val newPositions:MutableList<Int> = mutableListOf()
    private val diffChars:MutableList<Char> = mutableListOf()

    constructor ( context:Context):super(context)
    constructor ( context:Context, attrs :AttributeSet ):super(context,attrs)
    constructor ( context:Context, attrs :AttributeSet , defStyleAttr:Int):super(context,attrs,defStyleAttr)


    init {
        paint.textSize=this.textSize
        paint.color= currentTextColor

        textWidth=paint.measureText("A")
        val rect  =Rect()
        paint.getTextBounds("A",0,1,rect)
        textHeight= rect.height().toFloat()

        // 设置动画运行的时长
        anim.duration = 500


        // 设置动画延迟播放时间
        anim.startDelay = 500


        // 设置动画重复播放次数 = 重放次数+1
        // 动画播放次数 = infinite时,动画无限重复
        anim.repeatCount = 0


        // 设置重复播放动画模式
        // ValueAnimator.RESTART(默认):正序重放
        // ValueAnimator.REVERSE:倒序回放
        anim.repeatMode = ValueAnimator.RESTART



        anim.addUpdateListener {
            val value:Float= it.animatedValue as Float

            //TODO 如果0-0.5 则是平移（0.5平移结束）  0-1.0则全带有透明度   0.5-1.0则上垂直移动

            val canvas: Canvas= Canvas()

            //绘制Diff Old txt
            paint.alpha= ((1.0-value)*255).toInt()
            if(value<0.5f){
                canvas.drawText(occasionaloldCharSequence!!,0,occasionaloldCharSequence!!.length,0.0f,0.0f,paint)
            }else if(value>=0.5){
                canvas.drawText(occasionaloldCharSequence!!,0,occasionaloldCharSequence!!.length,0.0f,(value-0.5f)*2f*textHeight,paint)
            }


            //绘制Diff New Txt
            paint.alpha= (value*255).toInt()
            if(value<0.5f){
                canvas.drawText(occasionalnewCharSequence!!,0,occasionalnewCharSequence!!.length,0.0f,0.0f,paint)
            }else if(value>=0.5){
                canvas.drawText(occasionalnewCharSequence!!,0,occasionalnewCharSequence!!.length,0.0f,(value-0.5f)*2f*textHeight,paint)
            }

            //绘制 equle Text
            paint.alpha= 255
            if(value<0.5f){
                for (index in 0 until diffChars.size){
                    canvas.drawText(diffChars[index].toString(),getXPosition(index),0.0f,paint)
                }
            }
        }

    }


    private fun getXPosition(positon:Int):Float{
        val layoutDirection = ViewCompat.getLayoutDirection(this)
        // 获取x坐标
        val oldStartX = if (layoutDirection == View.LAYOUT_DIRECTION_LTR)
            layout.getLineLeft(0)
        else
            layout.getLineRight(0)
        return oldStartX
    }


    override fun setText(text: CharSequence?, type: TextView.BufferType?) {
        super.setText(text, type)
        oldCharSequence=currentCharSequence
        currentCharSequence=text
    }

    var occasionalnewCharSequence:CharSequence?=null
    var occasionaloldCharSequence:CharSequence?=null

    override fun onDraw(canvas: Canvas?) {

        occasionalnewCharSequence=currentCharSequence!!.subSequence(0,oldCharSequence!!.length)
        occasionaloldCharSequence= oldCharSequence!!.subSequence(0,oldCharSequence!!.length)

        val spaceChar = ' '

        if(oldCharSequence!=null) {
            for(index in 0 until  currentCharSequence!!.length){
                if(spaceChar == currentCharSequence!![index]){
                    continue
                }
                for(indexOld in 0 until  occasionaloldCharSequence!!.length){
                    if(occasionaloldCharSequence!![indexOld] == currentCharSequence!![index]){
                        oldPositions.add(indexOld)
                        newPositions.add(index)
                        diffChars.add(occasionaloldCharSequence!![indexOld])
                        println(message = "equle char is ${occasionaloldCharSequence!![indexOld]}                  ***oldPositions is $oldPositions  *****newPositions is $newPositions")
                        occasionaloldCharSequence=occasionaloldCharSequence!!.replaceRange(indexOld,indexOld+1," ")
                        occasionalnewCharSequence=occasionalnewCharSequence!!.replaceRange(index,index+1," ")
                        println(message = "occasionalCharSequence after remove is $occasionaloldCharSequence")
                        break
                    }
                }
            }
            println(message = oldCharSequence.toString())
        }else{
            occasionalnewCharSequence=currentCharSequence!!
        }
        canvas?.drawText(oldCharSequence!!, 0, oldCharSequence!!.length, 0.0f, 0.0f, paint)
    }

}
