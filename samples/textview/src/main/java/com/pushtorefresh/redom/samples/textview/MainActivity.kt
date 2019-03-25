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
import com.pushtorefresh.redom.api.LinearLayout
import com.pushtorefresh.redom.api.LinearLayout.Orientation.Vertical
import com.pushtorefresh.redom.api.TextView
import io.reactivex.Observable

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
                change {
                    orientation = Observable.just(Vertical)
                }

                TextView {
                    change {
                        text = Observable.just("1")
                    }
                }

                TextView {
                    change {
                        text = Observable.just("2")
                    }
                }

                Button {
                    change.text = Observable.just("Button")
                    observe {
                        output += clicks.doOnNext { Toast.makeText(this@MainActivity, "Button", Toast.LENGTH_LONG).show() }.map { UI }
                    }
                }
            }

            repeat(100) { index ->
                TextView {
                    change.text = Observable.just(index.toString())
                    output += observe.textChanges.doOnNext { println("Text change observed $it") }.map { UI }
                    output += observe.clicks.doOnNext { println("Click observed $it") }.map { UI }
                }
            }
        }.build())
    }

    object UI
}
