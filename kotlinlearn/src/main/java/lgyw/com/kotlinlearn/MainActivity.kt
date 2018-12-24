package lgyw.com.kotlinlearn

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity (){

    private lateinit var test:String

    private val handler : Handler= Handler()

    private val strs: Array<String> = arrayOf("ghbfregtrhtrtfreghtrjhtyjredo","hello wofrgytjytlkuloithyrld!","hqnuirbgxcvnreiwjhiuogf","gtrihreiugbdvdjkbgfreuiwrew","mbvxvyuqyutrgrqpA","fjrihwrtqpmfghetas","muiyuwqtynzvxsscpowhr")
    var timer:Timer=Timer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startTimer ()
    }


    fun startTimer (){
        val timers = object: TimerTask() {
            override fun run() {

                handler.post {
                    val txt=strs[(Math.random()*7).toInt()]
                    mainText.setTexts(txt)

                }
            }
        }
        timer.schedule(timers,5000,6*1000)
    }
}

