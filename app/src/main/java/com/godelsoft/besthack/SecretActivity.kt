package com.godelsoft.besthack

import android.os.Bundle
import android.provider.SyncStateContract
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
            val test = Test { res ->
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

class Test(private val callback: (String) -> Unit) : Runnable {
    private var port = 8081
    private var address = "10.0.2.2"

    override fun run() {
        try {
            val addr = InetAddress.getByName(address)

            println("Поключаемся к $addr:$port...")
            val socket = Socket(addr, port)

            val `in` = BufferedReader(InputStreamReader(socket.getInputStream()))
            val out = PrintWriter(OutputStreamWriter(socket.getOutputStream(), "UTF-8"))

            print("[Запрос]:")
            var line = "AlPHa\n"

            out.println(line)
            out.flush()

            line = `in`.readLine()
            println("[Ответ]:$line")
            callback(line)

            socket.close()
        } catch (x: IOException) {
            println("Ошибка ввода/вывода")
            x.printStackTrace()
        }
    }
}