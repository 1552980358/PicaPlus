package me.ks.chan.pica.plus.ui.screen.register.composable

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import me.ks.chan.pica.plus.R
import me.ks.chan.pica.plus.ui.icon.round.Lock
import me.ks.chan.pica.plus.ui.icon.round.ShieldQuestion
import me.ks.chan.pica.plus.ui.screen.register.viewmodel.RegisterState
import me.ks.chan.pica.plus.ui.theme.Spacing_16
import me.ks.chan.pica.plus.ui.theme.Spacing_8

@Composable
fun RegisterQuestionFields(
    @StringRes titleResId: Int,
    state: RegisterState,
    question: String,
    onQuestionChange: (String) -> Unit,
    answer: String,
    onAnswerChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.padding(horizontal = Spacing_16, vertical = Spacing_8),
        ) {
            Text(
                text = stringResource(id = titleResId),
                style = MaterialTheme.typography.titleSmall,
            )

            RegisterTextField(
                modifier = Modifier.fillMaxWidth(),
                value = question,
                enabled = state.editable,
                onValueChange = onQuestionChange,
                labelResId = R.string.screen_register_field_question_label,
                leadingIcon = {
                    Icon(
                        imageVector = ShieldQuestion,
                        contentDescription = stringResource(
                            id = R.string.screen_register_field_question_label
                        )
                    )
                },
            )

            RegisterTextField(
                modifier = Modifier.fillMaxWidth(),
                value = answer,
                enabled = state.editable,
                onValueChange = onAnswerChange,
                labelResId = R.string.screen_register_field_answer_label,
                leadingIcon = {
                    Icon(
                        imageVector = Lock,
                        contentDescription = stringResource(
                            id = R.string.screen_register_field_answer_label
                        )
                    )
                },
            )
        }
    }
}