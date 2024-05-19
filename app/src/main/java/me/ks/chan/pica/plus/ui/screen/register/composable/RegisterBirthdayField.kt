package me.ks.chan.pica.plus.ui.screen.register.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import me.ks.chan.pica.plus.R
import me.ks.chan.pica.plus.ui.icon.round.Cake
import me.ks.chan.pica.plus.ui.screen.register.viewmodel.RegisterFields
import me.ks.chan.pica.plus.ui.screen.register.viewmodel.RegisterState
import me.ks.chan.pica.plus.util.compose.FalseState
import me.ks.chan.pica.plus.util.kotlin.Blank
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun RegisterBirthdayField(
    modifier: Modifier = Modifier,
    state: RegisterState,
    birthdayMillisField: Long?,
    updateBirthdayMillis: (Long) -> Unit,
    onNext: KeyboardActionScope.() -> Unit = {},
) {
    var pickDate by remember(::FalseState)

    if (pickDate) {
        PickBirthdayDialog(
            birthdayMillisField = birthdayMillisField,
            onDismiss = { pickDate = false },
            onConfirm = updateBirthdayMillis,
        )
    }

    val focusManager = LocalFocusManager.current
    LaunchedEffect(key1 = pickDate) {
        if (!pickDate) {
            focusManager.clearFocus()
        }
    }

    RegisterTextField(
        modifier = modifier
            .onFocusChanged {
                if (it.isFocused) {
                    pickDate = true
                }
            },
        value = birthdayMillisField.let(::asDateString),
        enabled = state.editable,
        labelResId = R.string.screen_register_field_birthday_label,
        onValueChange = {},
        leadingIcon = {
            Icon(
                imageVector = Cake,
                contentDescription = stringResource(
                    id = R.string.screen_register_field_birthday_label
                )
            )
        },
        onNext = onNext,
        readOnly = true,
    )
}

@Composable
private fun PickBirthdayDialog(
    birthdayMillisField: Long?,
    onDismiss: () -> Unit,
    onConfirm: (Long) -> Unit,
) {
    @OptIn(ExperimentalMaterial3Api::class)
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = birthdayMillisField
    )

    @OptIn(ExperimentalMaterial3Api::class)
    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Row {
                TextButton(onClick = onDismiss) {
                    Text(text = stringResource(id = R.string.action_cancel))
                }

                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis
                            ?.let(onConfirm)
                        onDismiss()
                    },
                    enabled = datePickerState.selectedDateMillis != null
                ) {
                    Text(text = stringResource(id = R.string.action_ok))
                }
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

private const val BirthdayFormat = "yyyy-MM-dd"
private fun asDateString(millis: Long?): String {
    return when {
        millis == null -> Blank
        else -> {
            SimpleDateFormat(BirthdayFormat, Locale.getDefault())
                .format(millis)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun Preview() {
    var fields by remember { mutableStateOf(RegisterFields()) }
    Surface {
        RegisterBirthdayField(
            modifier = Modifier.fillMaxWidth(),
            state = RegisterState.Pending,
            birthdayMillisField = fields.birthdayMillis,
            updateBirthdayMillis = {
                fields = fields.copy(birthdayMillis = it)
            }
        )
    }
}