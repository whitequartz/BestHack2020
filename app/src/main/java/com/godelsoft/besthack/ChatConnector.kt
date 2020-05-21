package com.godelsoft.besthack

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.lang.Exception
import java.net.InetAddress
import java.net.Socket

class ChatConnector(private val chatID: Long, private val callback: (String?) -> Unit) : Runnable {
    private var isActive = true
    private lateinit var socket: Socket;

    override fun run() {
        val addr = InetAddress.getByName(GlobalDataLoader.serverIP)
        println("Trying to connect: $addr:${GlobalDataLoader.serverPort}...")
        socket = Socket(addr, GlobalDataLoader.serverPort)
        val inStream = BufferedReader(InputStreamReader(socket.getInputStream()))
        val outStream = PrintWriter(OutputStreamWriter(socket.getOutputStream(), "UTF-8"))

        print("[LISTEN CHAT]:")
        println(chatID)
        outStream.println("LISTEN $chatID")
        outStream.flush()

        while (isActive) {
            try {
                val line: String? = inStream.readLine()
                if (line == null)
                    callback("") // TODO: exception
                callback(line)
            }
            catch (e: Exception) {
                isActive = false
            }
        }
    }

    fun stop() {
        isActive = false
        socket.close()
    }
}