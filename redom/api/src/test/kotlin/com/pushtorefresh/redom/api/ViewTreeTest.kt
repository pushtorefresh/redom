package com.pushtorefresh.redom.api

import com.pushtorefresh.redom.testutil.createComponent
import com.pushtorefresh.redom.testutil.createComponentGroup
import org.assertj.core.api.Assertions
import org.junit.Test

class ViewTreeTest {

    @Test
    fun toViewTree_TextView() {
        val textView = createComponent(TextView::class.java)

        Assertions.assertThat(textView.toViewTree()).isEqualTo(ViewTree.View(TextView::class.java))
    }

    @Test
    fun toViewTree_LinearLayoutNoChildren() {
        val textView = createComponentGroup(LinearLayout::class.java, children = emptyList())

        Assertions.assertThat(textView.toViewTree()).isEqualTo(ViewTree.ViewGroup(LinearLayout::class.java, children = emptyList()))
    }

    @Test
    fun toViewTree_LinearLayoutTextView() {
        val textView = createComponentGroup(
                LinearLayout::class.java,
                children = listOf(createComponent(TextView::class.java))
        )

        Assertions.assertThat(textView.toViewTree()).isEqualTo(ViewTree.ViewGroup(LinearLayout::class.java, children = listOf(ViewTree.View(TextView::class.java))))
    }

    @Test
    fun toViewTree_LinearLayoutTextViewLinearLayout() {
        val textView = createComponentGroup(
                LinearLayout::class.java,
                children = listOf(
                        createComponent(TextView::class.java),
                        createComponentGroup(LinearLayout::class.java, children = emptyList())
                )
        )

        Assertions.assertThat(textView.toViewTree()).isEqualTo(
                ViewTree.ViewGroup(LinearLayout::class.java, children = listOf(
                        ViewTree.View(TextView::class.java),
                        ViewTree.ViewGroup(LinearLayout::class.java, children = emptyList())
                ))
        )
    }
}
