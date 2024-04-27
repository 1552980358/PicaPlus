package me.ks.chan.pica.plus.ui.screen.register.composable

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import me.ks.chan.pica.plus.R
import me.ks.chan.pica.plus.ui.screen.register.model.RegisterFieldsSubmittable
import me.ks.chan.pica.plus.ui.screen.register.viewmodel.RegisterFields
import me.ks.chan.pica.plus.ui.screen.register.viewmodel.RegisterState

@Composable
fun RegisterButton(
    startRegister: () -> Unit,
    fields: RegisterFields,
    state: RegisterState,
    updateState: (RegisterState) -> Unit,
    modifier: Modifier = Modifier,
) {
    LaunchedEffect(key1 = fields) {
        if (RegisterFieldsSubmittable.check(fields)) {
            if (state !is RegisterState.Submittable) {
                updateState(RegisterState.Submittable)
            }
        }
    }

    Button(
        modifier = modifier,
        onClick = startRegister,
        enabled = state.submittable,
    ) {
        Text(text = stringResource(id = R.string.screen_register_button_submit))
    }
}
