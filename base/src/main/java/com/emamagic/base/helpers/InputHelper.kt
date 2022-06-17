package com.emamagic.base.helpers

object InputHelper {

    @JvmStatic
    fun numberToPersian(number: String): String {
        var num = number
        val persian = arrayOf("۰", "۱", "۲", "۳", "۴", "۵", "۶", "۷", "۸", "۹")
        for (i in 0..9) {
            num = num.replace(i.toString(), persian[i])
        }
        return num
    }

}