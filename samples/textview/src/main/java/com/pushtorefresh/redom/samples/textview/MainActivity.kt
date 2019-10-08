package com.pushtorefresh.redom.samples.textview

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pushtorefresh.redom.android.AndroidIdRegistry
import com.pushtorefresh.redom.android.GlideImageLoader
import com.pushtorefresh.redom.android.androidDom
import com.pushtorefresh.redom.android.recycler.Adapter
import com.pushtorefresh.redom.android.recycler.AndroidLayoutParamsFactory
import com.pushtorefresh.redom.android.recycler.Inflater
import com.pushtorefresh.redom.android.recycler.ViewTypeRegistryImpl
import com.pushtorefresh.redom.api.Button
import com.pushtorefresh.redom.api.CheckBox
import com.pushtorefresh.redom.api.EditText
import com.pushtorefresh.redom.api.LinearLayout
import com.pushtorefresh.redom.api.LinearLayout.Orientation.Vertical
import com.pushtorefresh.redom.api.Switch
import com.pushtorefresh.redom.api.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = RecyclerView(this)
        val idRegistry = AndroidIdRegistry<String>()
        val adapter = Adapter(
            viewTypeRegistry = ViewTypeRegistryImpl(),
            inflater = Inflater(
                AndroidLayoutParamsFactory(this, idRegistry)
            ),
            idRegistry = idRegistry,
            imageLoader = GlideImageLoader(this)
        )
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        findViewById<ViewGroup>(android.R.id.content).addView(recyclerView)

        adapter.setComponents(androidDom {
            LinearLayout {

                orientation = Vertical

                TextView {
                    text = "1"
                }

                TextView {
                    text = "2"
                }

                Button {
                    text = "Button"
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
                    onTextChange = { changes ->
                        println("Text change observed $changes")
                    }
                    onClick = {
                        println("Click observed")
                    }
                }
            }
        }.build())
    }

    object UI
}
