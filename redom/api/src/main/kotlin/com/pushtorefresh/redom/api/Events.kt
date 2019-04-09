package com.pushtorefresh.redom.api

sealed class ViewEvent {
    object Click : ViewEvent()
}

sealed class TextViewEvent : ViewEvent() {
    data class TextChange(val oldText: CharSequence, val newText: CharSequence): TextViewEvent()
}
