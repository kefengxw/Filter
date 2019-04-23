package com.filter.android.model.data

object InternalDataConfiguration {

    const val INDEX_BAR_LETTER_SPLIT = 0.819f//decide by Golden Section 0.618
    const val INIT_VISIT_TOKEN = ""
    var VISIT_TOKEN: String = INIT_VISIT_TOKEN

    fun updateToken(newToken: String) {
        VISIT_TOKEN = newToken
    }
}