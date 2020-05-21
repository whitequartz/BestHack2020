package com.godelsoft.besthack

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.io.*
import java.net.InetAddress
import java.net.Socket
import java.util.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val test = Test()
        Thread(test).start()
    }
}

class Test : Runnable {
    private var port = 8081 // Порт, такой же, как у сервера
    private var address = "127.0.0.1" // Адрес сервера

    override fun run() {
            try {
                // Преобразуем адрес из строки во внутреннее представление
                val addr: InetAddress = InetAddress.getByName(address)

                // Создаём сокет и подключаем его к серверу
                println("Поключаемся к $address:$port...")
                val socket = Socket(addr, port)

                // Потоки ввода/вывода
                // BufferedReader позволяет читать вход по строкам
                val `in` = BufferedReader(InputStreamReader(socket.getInputStream()))
                // PrintWriter позволяет использовать println
                val out = PrintWriter(OutputStreamWriter(socket.getOutputStream(), "UTF-8"))
                // Создаем объект для чтения строк с клавиатуры
                val scan = Scanner(System.`in`, "UTF-8")

                // Читаем данные с клавиатуры и отправляем серверу
                // Запрашиваем строку
                print("[Запрос]:")
                var line: String = scan.nextLine()

                // Отправляем строку серверу
                out.println(line)
                out.flush() // принудительная отправка

                // Получаем ответ
                line = `in`.readLine() // ждем пока сервер отошлет строку текста.
                println("[Ответ]:$line")

                // Закрываем соединение
                socket.close()
            } catch (x: IOException) {
                println("Ошибка ввода/вывода")
                x.printStackTrace()
            }
    }
}