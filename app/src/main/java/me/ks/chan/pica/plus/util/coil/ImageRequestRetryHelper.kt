package me.ks.chan.pica.plus.util.coil

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import coil.request.ImageRequest

// This is a 2.6 hack method from Coil official
// https://github.com/coil-kt/coil/issues/884#issuecomment-975932886
// This is believed to be removed in Coil 3.0

@Composable
fun rememberImageRequestRetryHelper(): ImageRequestRetryHelper {
    return remember(calculation = ::ImageRequestRetryHelperImpl)
}

@Composable
fun ImageRequest.Builder.retryWith(
    imageRequestRetryHelper: ImageRequestRetryHelper,
): ImageRequest.Builder {
    if (imageRequestRetryHelper !is ImageRequestRetryHelperImpl) {
        throw IllegalArgumentException("ImageRequestRetryHelper must be created using `rememberImageRequestRetryHelper()`")
    }
    return setParameter(RetryHash, imageRequestRetryHelper.retryHash)
}

private const val RetryHash = "retry_hash"
private const val RetryHashInitial = 0

interface ImageRequestRetryHelper {
    fun retry()
}

private class ImageRequestRetryHelperImpl: ImageRequestRetryHelper {

    var retryHash by mutableIntStateOf(RetryHashInitial)

    override fun retry() {
        retryHash++
    }

}
