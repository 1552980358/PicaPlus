package me.ks.chan.pica.plus.ui.screen.register.composable

import androidx.annotation.StringRes
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import me.ks.chan.pica.plus.util.android.NullResources

@Composable
fun RegisterTextField(
    value: String,
    onValueChange: (String) -> Unit,
    @StringRes labelResId: Int,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    isError: Boolean = false,
    errorResId: Int = NullResources,
    leadingIcon: @Composable () -> Unit = {},
    onNext: KeyboardActionScope.() -> Unit = {},
    readOnly: Boolean = false,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        enabled = enabled,
        label = { Text(text = stringResource(id = labelResId)) },
        interactionSource = interactionSource,
        isError = isError,
        keyboardActions = KeyboardActions(onNext = onNext),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        leadingIcon = leadingIcon,
        readOnly = readOnly,
        singleLine = true,
        supportingText = when {
            errorResId == NullResources || !isError -> null
            else -> {
                { Text(text = stringResource(id = errorResId)) }
            }
        },
        trailingIcon = trailingIcon,
    )
}
