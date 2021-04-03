package com.rybeau.golfapp

import android.text.InputFilter
import android.text.Spanned

/**
 * Input filter that restricts the integer values that can be entered into EditText fields
 */
class HoleEntryFilter(private val minimum: Int, private val maximum : Int) : InputFilter {

    override fun filter(source: CharSequence?, start: Int, end: Int, dest: Spanned?, dstart: Int, dend: Int): CharSequence? {
        try{
            val input = Integer.parseInt(dest.toString() + source.toString())
            if(input in minimum..maximum){
                return null
            }
        } catch (error : NumberFormatException) {}
        return ""
    }


}