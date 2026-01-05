package com.madhukar.webcompiler

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.graphics.Color

object OldHighlighter {

    fun highlightHTML(text: String): Spannable {
        val spannable = SpannableString(text)

        // HTML tags
        Regex("</?\\w+[^>]*>").findAll(text).forEach {
            spannable.setSpan(
                ForegroundColorSpan(Color.parseColor("#60A5FA")),
                it.range.first,
                it.range.last + 1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        // Strings
        Regex("\"[^\"]*\"").findAll(text).forEach {
            spannable.setSpan(
                ForegroundColorSpan(Color.parseColor("#F59E0B")),
                it.range.first,
                it.range.last + 1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        return spannable
    }

    fun highlightCSS(text: String): Spannable {
        val spannable = SpannableString(text)

        // CSS properties
        Regex("\\b(color|background|font-size|margin|padding|display|width|height)\\b")
            .findAll(text).forEach {
                spannable.setSpan(
                    ForegroundColorSpan(Color.parseColor("#34D399")),
                    it.range.first,
                    it.range.last + 1,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }

        // Numbers
        Regex("\\b\\d+(px|em|%)?\\b").findAll(text).forEach {
            spannable.setSpan(
                ForegroundColorSpan(Color.parseColor("#F472B6")),
                it.range.first,
                it.range.last + 1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        return spannable
    }

    fun highlightJS(text: String): Spannable {
        val spannable = SpannableString(text)

        // JS keywords
        Regex("\\b(function|var|let|const|if|else|for|while|return|console)\\b")
            .findAll(text).forEach {
                spannable.setSpan(
                    ForegroundColorSpan(Color.parseColor("#A78BFA")),
                    it.range.first,
                    it.range.last + 1,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }

        // Strings
        Regex("\"[^\"]*\"").findAll(text).forEach {
            spannable.setSpan(
                ForegroundColorSpan(Color.parseColor("#F59E0B")),
                it.range.first,
                it.range.last + 1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        return spannable
    }
}
