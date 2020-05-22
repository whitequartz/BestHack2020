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

        initDeviceMenu()

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