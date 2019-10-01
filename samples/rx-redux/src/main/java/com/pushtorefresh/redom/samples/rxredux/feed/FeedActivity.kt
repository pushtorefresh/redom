package com.pushtorefresh.redom.samples.rxredux.feed

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pushtorefresh.redom.android.AndroidIdRegistry
import com.pushtorefresh.redom.android.androidDom
import com.pushtorefresh.redom.android.recycler.Adapter
import com.pushtorefresh.redom.android.recycler.AndroidLayoutParamsFactory
import com.pushtorefresh.redom.android.recycler.Inflater
import com.pushtorefresh.redom.android.recycler.ViewTypeRegistryImpl
import com.pushtorefresh.redom.api.Button
import com.pushtorefresh.redom.api.ConstraintLayout
import com.pushtorefresh.redom.api.ImageView
import com.pushtorefresh.redom.api.LayoutParams
import com.pushtorefresh.redom.api.LinearLayout
import com.pushtorefresh.redom.api.TextView
import com.pushtorefresh.redom.samples.rxredux.R
import java.util.*

class FeedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)
        val root = findViewById<ViewGroup>(android.R.id.content)
        val recyclerView = RecyclerView(root.context)
        val idRegistry = AndroidIdRegistry<String>()
        val adapter =
            Adapter(ViewTypeRegistryImpl(), idRegistry, Inflater(AndroidLayoutParamsFactory(root.context, idRegistry)))
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(root.context)
        recyclerView.addOnChildAttachStateChangeListener(object : RecyclerView.OnChildAttachStateChangeListener {
            override fun onChildViewDetachedFromWindow(view: View) {
                Log.i("RecyclerView", "detach ${view::class.java}")
            }

            override fun onChildViewAttachedToWindow(view: View) {
                Log.i("RecyclerView", "attach ${view::class.java}")
            }
        })
        root.findViewById<ViewGroup>(android.R.id.content).addView(recyclerView)

        val dom = androidDom {
            ConstraintLayout {
                id = "root"

                layoutParams = ConstraintLayout.LayoutParams.create(
                    width = LayoutParams.Size.MatchParent,
                    height = LayoutParams.Size.MatchParent
                )

                LinearLayout {
                    id = "textContainer"
                    orientation = LinearLayout.Orientation.Horizontal
                    layoutParams = ConstraintLayout.LayoutParams.create(
                        width = LayoutParams.Size.MatchParent,
                        height = LayoutParams.Size.WrapContent,
                        topToTop = "root"
                    )

                    TextView {
                        text = "Very super-duper long text"
                        layoutParams = LinearLayout.LayoutParams.create(
                            width = LayoutParams.Size.WrapContent,
                            height = LayoutParams.Size.WrapContent,
                            weight = 1F
                        )
                    }
                    TextView {
                        text = "Short text"
                        gravity = EnumSet.of(TextView.Gravity.Right)
                        layoutParams = LinearLayout.LayoutParams.create(
                            width = LayoutParams.Size.WrapContent,
                            height = LayoutParams.Size.WrapContent,
                            weight = 1F
                        )
                    }
                }
                ImageView {
                    id = "image"
                    layoutParams = ConstraintLayout.LayoutParams.create(
                        topToBottom = "textContainer"
                    )
                    drawable = ImageView.Drawable.Http(
                        url = "https://www.cbronline.com/wp-content/uploads/2016/06/what-is-URL-770x503.jpg",
                        placeholderId = R.drawable.test,
                        scaleType = ImageView.ScaleType.CenterCrop
                    )
                }
                LinearLayout {
                    orientation = LinearLayout.Orientation.Horizontal
                    layoutParams = ConstraintLayout.LayoutParams.create(
                        width = LayoutParams.Size.MatchParent,
                        height = LayoutParams.Size.WrapContent,
                        topToBottom = "image"
                    )
                    Button {
                        background = ImageView.Drawable.Http(
                            url = "https://www.cbronline.com/wp-content/uploads/2016/06/what-is-URL-770x503.jpg",
                            placeholderId = R.drawable.test,
                            scaleType = ImageView.ScaleType.FitCenter
                        )
                    }
                    ImageView {

                    }
                    TextView {
                        text = "Some text"
                        layoutParams = LinearLayout.LayoutParams.create(
                            width = LayoutParams.Size.WrapContent,
                            height = LayoutParams.Size.WrapContent
                        )
                    }
                }
            }
        }.build()

        adapter.setComponents(dom)
    }
}
