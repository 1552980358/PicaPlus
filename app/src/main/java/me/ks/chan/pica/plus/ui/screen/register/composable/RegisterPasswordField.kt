package me.ks.chan.pica.plus.ui.screen.register.composable

import androidx.annotation.StringRes
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import me.ks.chan.pica.plus.ui.icon.round.Password
import me.ks.chan.pica.plus.ui.icon.round.Visibility
import me.ks.chan.pica.plus.ui.icon.round.VisibilityOff
import me.ks.chan.pica.plus.util.androidx.compose.FalseState

private const val PasswordCrossFade = "password"

@Composable
fun RegisterPasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    @StringRes labelResId: Int,
    @StringRes contentVisibleDescriptionResId: Int,
    @StringRes contentInvisibleDescriptionResId: Int,
    enabled: Boolean,
    modifier: Modifier = Modifier,
    onNext: KeyboardActionScope.() -> Unit = {},
) {
    var visibility by remember(::FalseState)

    RegisterTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        enabled = enabled,
        labelResId = labelResId,
        leadingIcon = {
            Icon(
                imageVector = Password,
                contentDescription = stringResource(id = labelResId)
            )
        },
        onNext = onNext,
        trailingIcon = {
            IconButton(onClick = { visibility = !visibility }) {
                Crossfade(
                    targetState = visibility,
                    label = PasswordCrossFade
                ) { isVisible ->
                    Icon(
                        imageVector = when {
                            isVisible -> VisibilityOff
                            else -> Visibility
                        },
                        contentDescription = stringResource(
                            id = when {
                                isVisible -> contentVisibleDescriptionResId
                                else -> contentInvisibleDescriptionResId
                            }
                        ),
                    )
                }
            }
        }
    )
}