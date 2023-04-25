package about.cats.utils

import android.text.Spannable
import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan

fun SpannableString.setTextSize(size: Int) =
    setSpan(AbsoluteSizeSpan(size), 0, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

fun SpannableString.setTextStyle(style: Int) =
    setSpan(StyleSpan(style), 0, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

fun SpannableString.setTextColor(color: Int) =
    setSpan(ForegroundColorSpan(color), 0, length, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)

fun SpannableString.setTextClickListener(listener: ClickableSpan) =
    setSpan(listener, 0, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
