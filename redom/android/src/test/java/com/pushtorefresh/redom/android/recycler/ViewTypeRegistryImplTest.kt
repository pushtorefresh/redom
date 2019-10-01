package com.pushtorefresh.redom.android.recycler

import com.nhaarman.mockito_kotlin.mock
import com.pushtorefresh.redom.api.LinearLayout
import com.pushtorefresh.redom.api.TextView
import com.pushtorefresh.redom.testutil.createComponent
import com.pushtorefresh.redom.testutil.createComponentGroup
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ViewTypeRegistryImplTest {

    @Test
    fun viewTypeOf_TextView() {
        val viewTypeRegistry = ViewTypeRegistryImpl()
        val viewType = viewTypeRegistry.viewTypeOf(createComponent(TextView::class.java))

        assertThat(viewType).isEqualTo(0)
    }

    @Test
    fun viewTypeOf_TextView_twice() {
        val viewTypeRegistry = ViewTypeRegistryImpl()
        val viewType1 = viewTypeRegistry.viewTypeOf(createComponent(TextView::class.java))
        val viewType2 = viewTypeRegistry.viewTypeOf(createComponent(TextView::class.java))

        assertThat(viewType1).isEqualTo(0)
        assertThat(viewType2).isEqualTo(0)
    }

    @Test
    fun viewTypeOf_TextView_same() {
        val viewTypeRegistry = ViewTypeRegistryImpl()
        val component = createComponent(TextView::class.java)

        val viewType1 = viewTypeRegistry.viewTypeOf(component)
        val viewType2 = viewTypeRegistry.viewTypeOf(component)

        assertThat(viewType1).isEqualTo(0)
        assertThat(viewType2).isEqualTo(0)
    }

    @Test
    fun viewTypeOf_TextViewAndLinearLayoutWithChildren() {
        val viewTypeRegistry = ViewTypeRegistryImpl()
        val viewType1 = viewTypeRegistry.viewTypeOf(createComponent(TextView::class.java))
        val viewType2 = viewTypeRegistry.viewTypeOf(
            createComponentGroup(
                LinearLayout::class.java,
                children = listOf(
                    createComponent(TextView::class.java),
                    createComponent(TextView::class.java)
                ),
                idRegistry = mock()
            )
        )

        assertThat(viewType1).isEqualTo(0)
        assertThat(viewType2).isEqualTo(1)
    }

    @Test
    fun viewTypeOf_TextViewAndLinearLayoutWithChildrenSame() {
        val viewTypeRegistry = ViewTypeRegistryImpl()
        val viewType1 = viewTypeRegistry.viewTypeOf(createComponent(TextView::class.java))
        val componentGroup = createComponentGroup(
            LinearLayout::class.java,
            children = listOf(
                createComponent(TextView::class.java),
                createComponent(TextView::class.java)
            ),
            idRegistry = mock()
        )
        val viewType2 = viewTypeRegistry.viewTypeOf(componentGroup)
        val viewType3 = viewTypeRegistry.viewTypeOf(componentGroup)

        assertThat(viewType1).isEqualTo(0)
        assertThat(viewType2).isEqualTo(1)
        assertThat(viewType3).isEqualTo(1)
    }

    @Test
    fun viewTypeOf_TextViewAndLinearLayoutWithDifferentChildren() {
        val viewTypeRegistry = ViewTypeRegistryImpl()
        val viewType1 = viewTypeRegistry.viewTypeOf(createComponent(TextView::class.java))
        val viewType2 = viewTypeRegistry.viewTypeOf(
            createComponentGroup(
                LinearLayout::class.java,
                children = listOf(
                    createComponent(TextView::class.java),
                    createComponent(TextView::class.java)
                ),
                idRegistry = mock()
            )
        )
        val viewType3 = viewTypeRegistry.viewTypeOf(
            createComponentGroup(
                LinearLayout::class.java,
                children = listOf(
                    createComponent(TextView::class.java)
                ),
                idRegistry = mock()
            )
        )

        assertThat(viewType1).isEqualTo(0)
        assertThat(viewType2).isEqualTo(1)
        assertThat(viewType3).isEqualTo(2)
    }
}
