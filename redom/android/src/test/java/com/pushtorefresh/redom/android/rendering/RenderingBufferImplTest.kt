// Modified version of file from Domic project (see NOTICE).

package com.pushtorefresh.redom.android.rendering

class RenderingBufferImplTest : AbstractRenderingBufferTest() {
    override fun <T> createRenderingBuffer(): RenderingBuffer<T> = RenderingBufferImpl()
}
