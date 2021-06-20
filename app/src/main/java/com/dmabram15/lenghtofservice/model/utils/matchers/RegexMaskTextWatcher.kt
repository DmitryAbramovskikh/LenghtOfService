package com.dmabram15.lenghtofservice.model.utils.matchers

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.util.regex.Matcher
import java.util.regex.Pattern

class RegexMaskTextWatcher(private val date: EditText) : TextWatcher {

    private val template: CharSequence = "DD.MM.YYYY"
    private val dateCheckRegex = Pattern.compile("^\\d{2}.\\d{2}.\\d{4}\$")
    private lateinit var matcher : Matcher

    override fun afterTextChanged(s: Editable) {
        matcher = dateCheckRegex.matcher(s)
        if (!matcher.matches() && s.length >= 10) {
            date.error = "Проверьте правильность введенной даты"
        }
    }

    override fun beforeTextChanged(
        s: CharSequence?, start: Int,
        count: Int, after: Int
    ) {

    }

    override fun onTextChanged(
        s: CharSequence?, start: Int,
        before: Int, count: Int
    ) {

    }
}