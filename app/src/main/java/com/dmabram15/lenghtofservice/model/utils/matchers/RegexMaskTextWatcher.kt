package com.dmabram15.lenghtofservice.model.utils.matchers

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.dmabram15.lenghtofservice.model.utils.converters.DateConverter
import com.google.android.material.textfield.TextInputLayout
import org.apache.commons.validator.GenericValidator
import java.util.regex.Matcher
import java.util.regex.Pattern

class RegexMaskTextWatcher(private val date: EditText, private val inputLayout: TextInputLayout) : TextWatcher {

    private val dateCheckRegex = Pattern.compile("^\\d{2}.\\d{2}.\\d{4}\$")
    private lateinit var matcher : Matcher

    override fun afterTextChanged(s: Editable) {
        matcher = dateCheckRegex.matcher(s)
        if (s.length == 10) {
            if(GenericValidator
                    .isDate(s.toString(), "dd.MM.yyyy", true)
            ) {
                showSuccess()
            }
            else showError("проверь дату")
        }
    }

    private fun showError(message : String) {
        inputLayout.isErrorEnabled = true
        inputLayout.error = message
    }

    private fun showHelp(message : String) {
        inputLayout.isErrorEnabled = false
        inputLayout.helperText = message
    }

    private fun showSuccess() {
        inputLayout.isErrorEnabled = false
        inputLayout.helperText = "успешно"
    }

    override fun beforeTextChanged(
        s: CharSequence?, start: Int,
        count: Int, after: Int
    ) {
        showHelp("в формате dd.mm.yyyy")
    }

    override fun onTextChanged(
        s: CharSequence?, start: Int,
        before: Int, count: Int
    ) {

    }
}