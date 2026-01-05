package com.madhukar.webcompiler

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import java.util.regex.Pattern

object SyntaxHighlighter {

    private fun applyPattern(
        text: SpannableString,
        regex: String,
        color: Int
    ) {
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(text)
        while (matcher.find()) {
            text.setSpan(
                ForegroundColorSpan(color),
                matcher.start(),
                matcher.end(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }

    fun highlightHTML(code: String): SpannableString {
        val span = SpannableString(code)

        // Tags
        applyPattern(span, "</?\\w+[^>]*>", Color.parseColor("#60A5FA")) // blue

        // Attributes
        applyPattern(span, "\\w+(?==)", Color.parseColor("#FBBF24")) // yellow

        // Strings
        applyPattern(span, "\"[^\"]*\"", Color.parseColor("#34D399")) // green

        // Comments
        applyPattern(span, "<!--[\\s\\S]*?-->", Color.parseColor("#9CA3AF")) // gray

        return span
    }

    fun highlightCSS(code: String): SpannableString {
        val span = SpannableString(code)

        // Selectors
        applyPattern(span, "[.#]?[a-zA-Z0-9_-]+(?=\\s*\\{)", Color.parseColor("#60A5FA"))

        // Properties
        applyPattern(span, "[a-z-]+(?=\\s*:)", Color.parseColor("#FBBF24"))

        // Values
        applyPattern(span, ":[^;]+;", Color.parseColor("#34D399"))

        // Numbers
        applyPattern(span, "\\b\\d+(px|em|rem|%)\\b", Color.parseColor("#C084FC"))

        // Comments
        applyPattern(span, "/\\*[\\s\\S]*?\\*/", Color.parseColor("#9CA3AF"))

        return span
    }

    fun highlightJS(code: String): SpannableString {
        val span = SpannableString(code)

        // Keywords
        applyPattern(
            span,
            "\\b(function|var|let|const|if|else|for|while|return|new)\\b",
            Color.parseColor("#60A5FA")
        )

        // Strings
        applyPattern(span, "\"[^\"]*\"|'[^']*'", Color.parseColor("#34D399"))

        // Numbers
        applyPattern(span, "\\b\\d+\\b", Color.parseColor("#C084FC"))

        // Comments
        applyPattern(span, "//.*", Color.parseColor("#9CA3AF"))
        applyPattern(span, "/\\*[\\s\\S]*?\\*/", Color.parseColor("#9CA3AF"))

        return span
    }
}
