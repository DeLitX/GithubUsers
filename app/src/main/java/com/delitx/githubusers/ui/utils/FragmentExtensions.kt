package com.delitx.githubusers.ui.utils

import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

fun <T : View> Fragment.forceFindViewById(@IdRes id: Int): T = requireView().findViewById(id)

fun <T> Flow<T>.collectInLifecycleScope(
    owner: LifecycleOwner,
    collectionLambda: suspend (T) -> Unit
) {
    owner.lifecycleScope.launch {
        owner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            launch {
                collect(collectionLambda)
            }
        }
    }
}
