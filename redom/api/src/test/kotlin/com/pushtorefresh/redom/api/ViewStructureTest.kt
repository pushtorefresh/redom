package com.pushtorefresh.redom.api

import com.nhaarman.mockito_kotlin.mock
import com.pushtorefresh.redom.testutil.createComponent
import com.pushtorefresh.redom.testutil.createComponentGroup
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ViewStructureTest {

    @Test
    fun toViewStructure_TextView() {
        val textView = createComponent(TextView::class.java)

        assertThat(textView.viewStructure).isEqualTo(ViewStructure.View(TextView::class.java, null, null))
    }

    @Test
    fun toViewStructure_LinearLayoutNoChildren() {
        val textView = createComponentGroup(LinearLayout::class.java, children = emptyList(), idRegistry = mock())

        assertThat(textView.viewStructure).isEqualTo(
            ViewStructure.ViewGroup(
                LinearLayout::class.java,
                children = emptyList(),
                style = null,
                layoutParams = null
            )
        )
    }

    @Test
    fun toViewStructure_LinearLayoutTextView() {
        val textView = createComponentGroup(
            LinearLayout::class.java,
            children = listOf(createComponent(TextView::class.java)),
            idRegistry = mock()
        )

        assertThat(textView.viewStructure).isEqualTo(
            ViewStructure.ViewGroup(
                clazz = LinearLayout::class.java,
                children = listOf(ViewStructure.View(TextView::class.java, null, null)),
                style = null,
                layoutParams = null
            )
        )
    }

    @Test
    fun toViewStructure_LinearLayoutTextViewLinearLayout() {
        val textView = createComponentGroup(
            LinearLayout::class.java,
            children = listOf(
                createComponent(TextView::class.java),
                createComponentGroup(LinearLayout::class.java, children = emptyList(), idRegistry = mock())
            ),
            idRegistry = mock()
        )

        assertThat(textView.viewStructure).isEqualTo(
            ViewStructure.ViewGroup(
                LinearLayout::class.java,
                children = listOf(
                    ViewStructure.View(TextView::class.java, null, null),
                    ViewStructure.ViewGroup(
                        LinearLayout::class.java,
                        children = emptyList(),
                        style = null,
                        layoutParams = null
                    )
                ),
                style = null,
                layoutParams = null
            )
        )
    }
}
