package me.ks.chan.pica.plus.ui.screen.greeting.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import me.ks.chan.pica.plus.R
import me.ks.chan.pica.plus.ui.composable.shimmer.shimmer
import me.ks.chan.pica.plus.ui.icon.filled.Person
import me.ks.chan.pica.plus.ui.theme.Avatar_48
import me.ks.chan.pica.plus.util.compose.FalseState
import me.ks.chan.pica.plus.util.compose.TrueState

@Composable
fun GreetingAsyncAvatar(
    avatar: String?,
) {
    val error = remember(::FalseState)
    when {
        !error.value && avatar != null -> {
            AsyncAvatar(
                avatar = avatar,
                setError = error::value::set
            )
        }
        else -> { DefaultAvatar() }
    }
}

@Composable
private fun AsyncAvatar(
    avatar: String,
    setError: (Boolean) -> Unit,
) {
    var loading by remember(::TrueState)

    AsyncImage(
        modifier = Modifier
            .size(Avatar_48)
            .clip(RoundedCornerShape(Avatar_48 / 2))
            .shimmer(
                loading = loading,
                cornerRadius = Avatar_48 / 2
            ),
        model = ImageRequest.Builder(LocalContext.current)
            .crossfade(true)
            .data(avatar)
            .listener(
                onSuccess =  { _, _ -> loading = false },
                onError = { _, _ -> setError(true) }
            )
            .build(),
        contentDescription = stringResource(
            id = R.string.screen_greeting_image_description_avatar
        ),
    )
}

@Composable
private fun DefaultAvatar() {
    Image(
        modifier = Modifier
            .size(Avatar_48),
        imageVector = Person,
        contentDescription = stringResource(
            id = R.string.screen_greeting_image_description_avatar
        ),
    )
}