package com.pushtorefresh.redom.api

import com.pushtorefresh.redom.testutil.createComponent
import com.pushtorefresh.redom.testutil.createComponentGroup
import org.assertj.core.api.Assertions
import org.junit.Test

class ViewStructureTest {

    @Test
    fun toViewStructure_TextView() {
        val textView = createComponent(TextView::class.java)

        Assertions.assertThat(textView.toViewStructure()).isEqualTo(ViewStructure.View(TextView::class.java))
    }

    @Test
    fun toViewStructure_LinearLayoutNoChildren() {
        val textView = createComponentGroup(LinearLayout::class.java, children = emptyList())

        Assertions.assertThat(textView.toViewStructure()).isEqualTo(ViewStructure.ViewGroup(LinearLayout::class.java, children = emptyList()))
    }

    @Test
    fun toViewStructure_LinearLayoutTextView() {
        val textView = createComponentGroup(
                LinearLayout::class.java,
                children = listOf(createComponent(TextView::class.java))
        )

        Assertions.assertThat(textView.toViewStructure()).isEqualTo(ViewStructure.ViewGroup(LinearLayout::class.java, children = listOf(ViewStructure.View(TextView::class.java))))
    }

    @Test
    fun toViewStructure_LinearLayoutTextViewLinearLayout() {
        val textView = createComponentGroup(
                LinearLayout::class.java,
                children = listOf(
                        createComponent(TextView::class.java),
                        createComponentGroup(LinearLayout::class.java, children = emptyList())
                )
        )

        Assertions.assertThat(textView.toViewStructure()).isEqualTo(
                ViewStructure.ViewGroup(LinearLayout::class.java, children = listOf(
                        ViewStructure.View(TextView::class.java),
                        ViewStructure.ViewGroup(LinearLayout::class.java, children = emptyList())
                ))
        )
    }
}
