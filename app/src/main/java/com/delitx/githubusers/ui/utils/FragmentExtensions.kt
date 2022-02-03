package com.delitx.githubusers.ui.utils

import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment

fun <T : View> Fragment.forceFindViewById(@IdRes id: Int): T = requireView().findViewById(id)
