package com.godelsoft.besthack

import org.json.JSONObject
import java.io.*
import java.net.InetAddress
import java.net.Socket

class Response(json: String) : JSONObject(json) {
    val succ: Boolean? = this.optBoolean("succ")
    val data: String? = this.optString("data")
//    val data = this.optJSONArray("data")
//        ?.let { 0.until(it.length()).map { i -> it.optJSONObject(i) } }
//        ?.map { Foo(it.toString()) }
}

class TcpRequest(private val data : String, private val callback: (Response) -> Unit) : Runnable {
    private var address = "10.0.2.2"
    private var port = 8081

    override fun run() {
        try {
            val addr = InetAddress.getByName(address)
            println("Trying to connect: $addr:$port...")
            val socket = Socket(addr, port)
            val inStream = BufferedReader(InputStreamReader(socket.getInputStream()))
            val outStream = PrintWriter(OutputStreamWriter(socket.getOutputStream(), "UTF-8"))

            print("[REQUEST]:")
            outStream.println(data)
            outStream.flush()

            val line = inStream.readLine()
            println("[RESPONSE]:$line")
            callback(Response(line))
            socket.close()
        } catch (x: IOException) {
            println("ERROR 12432145235")
            x.printStackTrace()
        }
    }
}

