package lgyw.com.kotlinlearn.widget

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.TextView
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.graphics.Rect
import android.graphics.RectF
import android.support.v4.view.ViewCompat
import android.view.View
import lgyw.com.kotlinlearn.bins.TextDanceBin




class TextDance :TextView{

    private var currentCharSequence:CharSequence = ""
    private var oldCharSequence:CharSequence = ""
    private val paint:Paint=Paint()
    private var textWidth:Float=0.0f
    private var textHeight=0.0f
    private var anim = ValueAnimator.ofFloat(0f, 1f)
    private val textDanceBin : TextDanceBin = TextDanceBin()
    private var oldTextBaseLineX:Float ?= null

    private var value:Float =1.0f
    private var isReDrawByGetXStartX=false

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
        anim.duration = 5000

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
            value= it.animatedValue as Float
            invalidate()
        }

        val animatorListenerAdapter =object: AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                getXStartDrawX()
            }
        }
        anim.addListener(animatorListenerAdapter)

        post {
            getXStartDrawX()
        }
    }


    private fun getXStartDrawX(){
        isReDrawByGetXStartX=true
        val layoutDirection = ViewCompat.getLayoutDirection(this)
        // 获取x坐标
        if (layoutDirection == View.LAYOUT_DIRECTION_LTR)
            oldTextBaseLineX=layout.getLineLeft(0)
        else
            oldTextBaseLineX=layout.getLineRight(0)
    }


    fun setTexts(text: CharSequence?) {
        value=0.0f
        setText(text)
        oldCharSequence = currentCharSequence
        currentCharSequence=text!!
        diffCharSequence()
    }


    /**
     *
     */
    private fun diffCharSequence(){
        var occasionaloldCharSequence = oldCharSequence.subSequence(0,oldCharSequence.length)
        val spaceChar = ' '
        val startX = layout.getLineLeft(0)
        textDanceBin.charMoveDistances.clear()
        textDanceBin.diffChars.clear()
        textDanceBin.newPositions.clear()
        textDanceBin.oldPositions.clear()
        textDanceBin.oldCharsXPosition.clear()

        for(index in 0 until  currentCharSequence.length){
            if(spaceChar == currentCharSequence[index]){
                continue
            }
            for(indexOld in 0 until  occasionaloldCharSequence.length){
                if(occasionaloldCharSequence[indexOld] == currentCharSequence[index]){
                    textDanceBin.oldPositions.add(indexOld)
                    textDanceBin.newPositions.add(index)
                    textDanceBin.diffChars.add(occasionaloldCharSequence[indexOld])
                    occasionaloldCharSequence=occasionaloldCharSequence.replaceRange(indexOld,indexOld+1," ")

                    //计算字母的偏移量
                    val oldTxtX=paint.measureText(oldCharSequence,0,indexOld)
                    val newTxtX=paint.measureText(currentCharSequence,0,index)

                    textDanceBin.oldCharsXPosition.add(oldTxtX)

                    val moveDistance=-oldTxtX-oldTextBaseLineX!!+newTxtX+startX
                    textDanceBin.charMoveDistances.add(moveDistance)
                    break
                }
            }
        }
        anim.start()
    }

    private val minRect = Rect()

    /**
     * 绘制Text
     */
    override fun onDraw(canvas: Canvas?) {
        if(currentCharSequence.isEmpty()){
            println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
            super.onDraw(canvas)
            currentCharSequence=text
            return
        }

        val fm = paint.fontMetrics
        paint.getTextBounds(text.toString(), 0, text.length, minRect)

        val startX = layout.getLineLeft(0)

        //绘制Diff Old txt
        canvas!!.save()
        canvas.translate(oldTextBaseLineX!!, baseline.toFloat())
        paint.alpha= ((1.0-value)*255).toInt()
        canvas.drawText(oldCharSequence,0,oldCharSequence.length,0.0f,-value*textHeight,paint)
        canvas.restore()



        //绘制Diff New Txt
        canvas.save()
        canvas.translate(startX, baseline.toFloat()+minRect.height()-fm.bottom)
        paint.alpha= (value*255).toInt()
        canvas.drawText(currentCharSequence,0,currentCharSequence.length,0.0f,-value*textHeight,paint)
        canvas.restore()


        //绘制 equle Text
        paint.alpha= 255
        if(value<0.5f){
            //新的Txt被SetText执行后 ，可通过layout.getLineLeft获取到新字体绘制的X起点
            canvas.save()
            canvas.translate(oldTextBaseLineX!!, baseline.toFloat())
            for (index in 0 until textDanceBin.diffChars.size){
                canvas.drawText(textDanceBin.diffChars[index].toString(), textDanceBin.oldCharsXPosition[index]+textDanceBin.charMoveDistances[index] *value*2,0.0f,paint)
            }
            canvas.restore()
        }else{
            if(isReDrawByGetXStartX){
                isReDrawByGetXStartX=false
            }else{
                canvas.save()
                canvas.translate(oldTextBaseLineX!!, baseline.toFloat())
                for (index in 0 until textDanceBin.diffChars.size){
                    canvas.drawText(textDanceBin.diffChars[index].toString(), textDanceBin.oldCharsXPosition[index]+textDanceBin.charMoveDistances[index],0.0f,paint)
                }
                canvas.restore()
            }

        }
    }

}
