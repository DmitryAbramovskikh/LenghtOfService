package com.dmabram15.lenghtofservice.presentation.view.viewmatcher

import android.text.Editable
import android.text.TextWatcher
import org.apache.commons.validator.GenericValidator
import java.util.regex.Matcher
import java.util.regex.Pattern

class RegexMaskTextWatcher(private val listener: OnInputTextStateChangeListener) : TextWatcher {

    private val dateCheckRegex = Pattern.compile("^\\d{2}.\\d{2}.\\d{4}\$")
    private lateinit var matcher : Matcher

    private val pattern = "dd.MM.yyyy"

    override fun afterTextChanged(s: Editable) {
        matcher = dateCheckRegex.matcher(s)
        if (s.length >= pattern.length) {
            if(GenericValidator
                    .isDate(s.toString(), pattern, true)
            ) {
                listener.onInputSuccess()
            }
            else listener.onInputError("") //TODO довавить сообщение
        }
    }

    override fun beforeTextChanged(
        s: CharSequence?, start: Int,
        count: Int, after: Int
    ) {
        listener.onInputBegin()
    }

    override fun onTextChanged(
        s: CharSequence?, start: Int,
        before: Int, count: Int
    ) {
    }
}