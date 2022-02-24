package com.zaiming.android.lighthousegallery.utils.captureException

import java.lang.Exception

object LogcatDumper {

    fun dump(): MutableList<String> {
        return try {
            val process = Runtime.getRuntime().exec(arrayOf("logcat", "-d", "-s", "-v", "raw", "Go"))

            val result = process.inputStream.bufferedReader().useLines {
                val list = mutableListOf<String>()

                it.forEach { line ->
                    list.add(line)
                }
                list
            }

            process.waitFor()

            result
        } catch (e: Exception) {
            mutableListOf()
        }
    }

}