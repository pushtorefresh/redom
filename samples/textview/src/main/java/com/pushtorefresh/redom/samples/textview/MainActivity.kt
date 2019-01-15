package com.pushtorefresh.redom.samples.textview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.pushtorefresh.redom.android.androidDom
import com.pushtorefresh.redom.android.recycler.Adapter
import com.pushtorefresh.redom.android.recycler.Inflator
import com.pushtorefresh.redom.android.recycler.ViewTypeRegistryImpl
import com.pushtorefresh.redom.api.TextView
import io.reactivex.Observable

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = RecyclerView(this)
        val adapter = Adapter(ViewTypeRegistryImpl(), Inflator)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        findViewById<ViewGroup>(android.R.id.content).addView(recyclerView)

        val dom = androidDom<UI> {
            repeat(100) { index ->
                TextView {
                    change.text = Observable.just(index.toString())
                }
            }
        }.build()
        adapter.setComponents(dom.children)
    }

    object UI
}
