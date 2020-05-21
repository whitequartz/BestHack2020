package com.godelsoft.besthack

import org.json.JSONObject
import java.io.*
import java.lang.Exception
import java.net.InetAddress
import java.net.Socket

class Response(json: String) : JSONObject(json) {
    val succ: Boolean? = this.optBoolean("Succ")
    val data: String? = this.optString("Data")
}

class TcpRequest(private val data : String, private val callback: (Response?) -> Unit) : Runnable {
    override fun run() {
        try {
            val addr = InetAddress.getByName(GlobalDataLoader.serverIP)
            println("Trying to connect: $addr:${GlobalDataLoader.serverPort}...")
            val socket = Socket(addr, GlobalDataLoader.serverPort)
            val inStream = BufferedReader(InputStreamReader(socket.getInputStream()))
            val outStream = PrintWriter(OutputStreamWriter(socket.getOutputStream(), "UTF-8"))

            print("[REQUEST]:")
            println(data)
            outStream.println(data)
            outStream.flush()

            var line: String? = inStream.readLine()
            if (line == null) {
                callback(null)
                return
            }
            if (line[line.lastIndex] != '}' && line[line.lastIndex] != ']')
                line += if (line[0] == ']') ']' else '}'
            println("[RESPONSE]:$line")
            var res: Response? = null
            try {
                res = Response(line)
            } catch (e: Exception) {}
            callback(res)
            socket.close()
        } catch (x: IOException) {
            println("ERROR 12432145235")
            x.printStackTrace()
        }
    }
}

