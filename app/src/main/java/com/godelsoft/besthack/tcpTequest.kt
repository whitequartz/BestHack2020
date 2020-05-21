package com.godelsoft.besthack

import java.io.*
import java.net.InetAddress
import java.net.Socket

class tcpTequest(private val data : String, private val callback: (String) -> Unit) : Runnable {
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
            outStream.println(data + "\n")
            outStream.flush()

            val line = inStream.readLine()
            println("[RESPONSE]:$line")
            callback(line)
            socket.close()
        } catch (x: IOException) {
            println("ERROR 12432145235")
            x.printStackTrace()
        }
    }
}

