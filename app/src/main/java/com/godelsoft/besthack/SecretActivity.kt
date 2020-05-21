package com.godelsoft.besthack

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
//import com.sun.xml.internal.ws.streaming.XMLStreamWriterUtil.getOutputStream
//import jdk.nashorn.internal.runtime.ScriptingFunctions.readLine
import kotlinx.android.synthetic.main.activity_secret.*
import java.io.*
import java.net.InetAddress
import java.net.Socket


class SecretActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_secret)

        button_test_1.setOnClickListener {
            val test = tcpTequest(response_testbox.text.toString()) { res ->
                runOnUiThread {
                    response_testbox.setText(res)
                }
            }
            Thread(test).start()
        }

        button_test_2.setOnClickListener {
            response_testbox.setText("2222222 2222 2222222 22222222")
        }
    }
}