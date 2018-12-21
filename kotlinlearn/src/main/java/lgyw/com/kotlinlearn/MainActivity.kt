package lgyw.com.kotlinlearn

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.widget.TextView

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity (){

    private var txts:TextView?=null
    private lateinit var test:String

    private val handler : Handler= Handler{

        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    private fun initViews() {
        println(message = "initViews be invoke")
        var txt="hello world!"
        mainText.text=txt
        txt="ghbtredo"
        mainText.text=txt
    }

}

