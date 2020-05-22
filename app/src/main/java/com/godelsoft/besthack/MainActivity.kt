package com.godelsoft.besthack

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.TableRow
import androidx.cardview.widget.CardView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.view.children
import androidx.recyclerview.widget.LinearLayoutManager
import com.godelsoft.besthack.recycleViewAdapters.IssueAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import org.jetbrains.anko.startActivity
import org.json.JSONArray
import java.util.*
import android.util.Log

class MainActivity : AppCompatActivity() {
    private lateinit var recycleAdapter: IssueAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainContext = this
        main = this

        recycleView.layoutManager = LinearLayoutManager(this)
        recycleAdapter =
            IssueAdapter(this)
        recycleView.adapter = recycleAdapter

        motionLayout.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(
                    motionLayout: MotionLayout,
                    i: Int,
                    i1: Int
            ) {
            }

            override fun onTransitionChange(
                    motionLayout: MotionLayout,
                    i: Int,
                    i1: Int,
                    v: Float
            ) {
            }

            override fun onTransitionCompleted(motionLayout: MotionLayout, i: Int) {
//                recycleView.setScrollEnable(motionLayout.currentState == R.id.end)
            }
            override fun onTransitionTrigger(
                    motionLayout: MotionLayout,
                    i: Int,
                    b: Boolean,
                    v: Float
            ) {
            }
        })

        var s1 = ""
        val req = TcpRequest("GET_DEVICES ${User.current.ID}") { res ->
            if (res?.succ == true) {
                val devices = JSONArray(res.data).let {
                    0.until(it.length()).map { i -> it.optJSONObject(i) }
                }
//                        .map { Device(
//                    DeviceType.values()[it.optInt("Type") ?: 0],
//                    it.optString("Model") ?: "",
//                    it.optInt("Cost"),
//                    CalFormatter.getCalendarFromDate(Date(it.optLong("BuyTime"))),
//                    it
//                )}

                User.current.devices = arrayListOf(
                        Device(DeviceType.SYSTEM, devices[0].optString("Model"), devices[0].optInt("Cost"), CalFormatter.getCalendarFromDate(Date(devices[0].optLong("BuyTime"))), devices[0].optLong("ValidTime")),
                        Device(DeviceType.MONITOR, devices[1].optString("Model"), devices[1].optInt("Cost"), CalFormatter.getCalendarFromDate(Date(devices[1].optLong("BuyTime"))), devices[1].optLong("ValidTime")),
                        Device(DeviceType.MONITOR, devices[2].optString("Model"), devices[2].optInt("Cost"), CalFormatter.getCalendarFromDate(Date(devices[2].optLong("BuyTime"))), devices[2].optLong("ValidTime")),
                        Device(DeviceType.KEYBOARD, devices[3].optString("Model"), devices[3].optInt("Cost"), CalFormatter.getCalendarFromDate(Date(devices[3].optLong("BuyTime"))), devices[3].optLong("ValidTime")),
                        Device(DeviceType.MOUSE, devices[4].optString("Model"), devices[4].optInt("Cost"), CalFormatter.getCalendarFromDate(Date(devices[4].optLong("BuyTime"))), devices[4].optLong("ValidTime")),
                        Device(DeviceType.HEADPHONES, devices[5].optString("Model"), devices[5].optInt("Cost"), CalFormatter.getCalendarFromDate(Date(devices[5].optLong("BuyTime"))), devices[5].optLong("ValidTime")),
                        Device(DeviceType.CAMERA, devices[6].optString("Model"), devices[6].optInt("Cost"), CalFormatter.getCalendarFromDate(Date(devices[6].optLong("BuyTime"))), devices[6].optLong("ValidTime")),
                        Device(DeviceType.MICROPHONE, devices[7].optString("Model"), devices[7].optInt("Cost"), CalFormatter.getCalendarFromDate(Date(devices[7].optLong("BuyTime"))), devices[7].optLong("ValidTime")),
                        Device(DeviceType.WIFI, devices[8].optString("Model"), devices[8].optInt("Cost"), CalFormatter.getCalendarFromDate(Date(devices[8].optLong("BuyTime"))), devices[8].optLong("ValidTime")))
                initDeviceMenu()
            }
        }
        Thread(req).start()
//        User.current.devices = l



//        User.current.devices = arrayListOf(
//                Device(DeviceType.SYSTEM, s1, 74000, Calendar.getInstance().apply { set(2019, 10, 27) }, 700.daysToMillis()),
//                Device(DeviceType.MONITOR, "AOC 23\"", 14000, Calendar.getInstance().apply { set(2019, 10, 27) }, 264.daysToMillis()),
//                Device(DeviceType.MONITOR, "AOC 27\"", 17000, Calendar.getInstance().apply { set(2019, 10, 27) }, 124.daysToMillis()),
//                Device(DeviceType.KEYBOARD, "ASUS Claymore", 10000, Calendar.getInstance().apply { set(2019, 10, 27) }, 424.daysToMillis()),
//                Device(DeviceType.MOUSE, "Zowie EC2-A", 5000, Calendar.getInstance().apply { set(2019, 10, 27) }, 600.daysToMillis()),
//                Device(DeviceType.HEADPHONES, "Razer Kraken", 7500, Calendar.getInstance().apply { set(2019, 10, 27) }, 500.daysToMillis()),
//                Device(DeviceType.CAMERA, "LG 270", 3000, Calendar.getInstance().apply { set(2019, 10, 27) }, 365.daysToMillis()),
//                Device(DeviceType.MICROPHONE, "YETI", 74000, Calendar.getInstance().apply { set(2019, 10, 27) }, 200.daysToMillis()),
//                Device(DeviceType.WIFI, "Xiaomi A8", 74000, Calendar.getInstance().apply { set(2019, 10, 27) }, 900.daysToMillis()))


//        initDeviceMenu()


        tryGetIssues()

        when(User.current.type) {
            UserType.WORKER -> {
                floatingActionButton.visibility = VISIBLE
                floatingActionButton.setOnClickListener {
                    Issue.startCreation()
                }
            }
            UserType.SUPPORT -> {
                floatingActionButton.visibility = GONE
            }
            UserType.TEAM_LEAD -> {
                floatingActionButton.visibility = GONE
            }
        }

        floatingActionButton.setOnClickListener {
            val req = TcpRequest("ADD_ISSUE ${User.current.ID} ") { res ->
                if (res?.succ == true) {
                    val newIssueID = res.data
                    runOnUiThread {
                        startActivity(Intent(main, IssueChatActivity::class.java).apply {
                            putExtra("chatId", newIssueID)
                        })
                    }
                }
            }
            Thread(req).start()
        }

        account.setOnClickListener {
            startActivity<MyAccountActivity>()
        }
    }

    override fun onResume() {
        tryGetIssues()
        super.onResume()
    }

    private fun initDeviceMenu() {
        for ((r, v) in devicePanel.children.withIndex()) {
            if (v is TableRow) {
                for ((i, card) in v.children.withIndex()) {
                    val device =  User.current.devices[3 * r + i]
                    if (device == null) {
                        card.deviceName.text = "Отсутствует"
                        card.lastValidDate.visibility = GONE
                        card.progressBar.visibility = GONE
                        (card as CardView).setCardBackgroundColor(Color.parseColor("#f5b2ae"))
                    } else {
                        card.lastValidDate.visibility = VISIBLE
                        card.progressBar.visibility = VISIBLE
                        card.deviceName.text = device.model
                        card.lastValidDate.text = CalFormatter.datef(device.getInvalidDate())
                        card.progressBar.progress = device.getProgress()
                        if (card.progressBar.progress == 0) {
                            (card as CardView).setCardBackgroundColor(Color.parseColor("#f5b2ae"))
                        } else {
                            (card as CardView).setCardBackgroundColor(Color.parseColor("#FAFAFA"))
                        }
                        card.progressBar.progressDrawable.setColorFilter(
                            device.getProgressColor(),
                            PorterDuff.Mode.SRC_IN
                        )
                    }
                }
            }
        }
    }

    fun tryGetIssues() {
        try {
            val req = "GET_CUR_ISSUES ${User.current.ID}"
            val test = TcpRequest(req) { res ->
                if (res?.succ == true) {
                    runOnUiThread {
                        val issues = JSONArray(res.data).let {
                            0.until(it.length()).map { i -> it.optJSONObject(i) }
                        }.map { IssueJSON(it.toString()) }.map {
                            Issue(
                                it.id,
                                it.title,
                                it.time.toString(),
                                it.description,
                                Chat()
                            )
                        }
                        recycleAdapter.update(issues)
                    }
                } else {
                    println("Insuccess current issues loading")
                    // TODO: error?
                }
            }
            Thread(test).start()
        } catch (e: Exception) {
            println("connection error 113244132")
            // TODO: connection error
        }
    }

    companion object {
        lateinit var mainContext: Context
        lateinit var main: MainActivity
    }
}