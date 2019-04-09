package com.pushtorefresh.redom.samples.textview

import android.os.Bundle
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pushtorefresh.redom.android.androidDom
import com.pushtorefresh.redom.android.recycler.Adapter
import com.pushtorefresh.redom.android.recycler.Inflater
import com.pushtorefresh.redom.android.recycler.ViewTypeRegistryImpl
import com.pushtorefresh.redom.api.Button
import com.pushtorefresh.redom.api.CheckBox
import com.pushtorefresh.redom.api.EditText
import com.pushtorefresh.redom.api.LinearLayout
import com.pushtorefresh.redom.api.LinearLayout.Orientation.Vertical
import com.pushtorefresh.redom.api.Switch
import com.pushtorefresh.redom.api.TextView
import com.pushtorefresh.redom.api.TextViewEvent
import com.pushtorefresh.redom.api.ViewEvent

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = RecyclerView(this)
        val adapter = Adapter(ViewTypeRegistryImpl(), Inflater)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        findViewById<ViewGroup>(android.R.id.content).addView(recyclerView)

        adapter.setComponents(androidDom<UI> {
            LinearLayout {

                orientation = Vertical

                TextView {
                    text = "hello"

                    events = {
                        when (it) {
                            is ViewEvent.Click -> println(it)
                        }
                    }
                }

                TextView {
                    text = "2"
                }

                Button {
                    text = "Button"

                    events = {
                        when (it) {
                            is ViewEvent.Click -> Toast.makeText(this@MainActivity, "Button", Toast.LENGTH_LONG).show()
                        }
                    }
                }

                CheckBox {
                    checked = true
                }

                Switch {
                    checked = false
                }

                EditText {
                    text = "Yooo, change me"
                }
            }

            repeat(100) { index ->
                TextView {
                    text = index.toString()
                    events = {
                        when (it) {
                            is TextViewEvent.TextChange -> println("Text change observed $it")
                            is ViewEvent.Click -> println("Click observed $it")
                        }
                    }
                }
            }
        }.build())
    }

    object UI
}
