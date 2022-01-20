
package com.raywenderlich.android.petsave.common.utils

import com.raywenderlich.android.logging.Logger
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


inline fun CoroutineScope.createExceptionHandler(
    message: String,
    crossinline action: (throwable: Throwable) -> Unit
) = CoroutineExceptionHandler { _, throwable ->
  Logger.e(throwable, message)
  throwable.printStackTrace()


  launch {
    action(throwable)
  }
}