package com.godelsoft.besthack

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
//import com.sun.xml.internal.ws.streaming.XMLStreamWriterUtil.getOutputStream
//import jdk.nashorn.internal.runtime.ScriptingFunctions.readLine
import kotlinx.android.synthetic.main.activity_secret.*

class SecretActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_secret)

        button_test_1.setOnClickListener {
            val data = "TEST ${response_testbox.text.toString()}"
            val test = TcpRequest(data) { res ->
                if (res?.succ == true) {
                    runOnUiThread {
                        response_testbox.setText(res.data)
                    }
                }
            }
            Thread(test).start()
        }

        button_test_2.setOnClickListener {
            response_testbox.setText("helllLLL0VeEEEEEEEEEEEEEu!ersDA")
//            val foos = Response("""{"Succ":true,"Data":"QweqwrtTerwerwe"}""")
        }
    }
}