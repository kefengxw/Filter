package com.filter.android.util

object UtilString {
    //decide index of String, in case of other language, it can be expand by getIndex
    fun getIndex(input: String): String {
        //convertStringToIndex
        return input.substring(0, 1)
    }
}