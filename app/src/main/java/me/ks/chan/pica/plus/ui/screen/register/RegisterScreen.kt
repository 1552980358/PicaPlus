package me.ks.chan.pica.plus.ui.screen.register

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imeNestedScroll
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import me.ks.chan.pica.plus.R
import me.ks.chan.pica.plus.ui.icon.round.Person
import me.ks.chan.pica.plus.ui.icon.round.WavingHand
import me.ks.chan.pica.plus.ui.screen.register.composable.RegisterBirthdayField
import me.ks.chan.pica.plus.ui.screen.register.composable.RegisterButton
import me.ks.chan.pica.plus.ui.screen.register.composable.RegisterGenderField
import me.ks.chan.pica.plus.ui.screen.register.composable.RegisterPasswordField
import me.ks.chan.pica.plus.ui.screen.register.composable.RegisterQuestionFields
import me.ks.chan.pica.plus.ui.screen.register.composable.RegisterTextField
import me.ks.chan.pica.plus.ui.screen.register.viewmodel.RegisterFields
import me.ks.chan.pica.plus.ui.screen.register.viewmodel.RegisterState
import me.ks.chan.pica.plus.ui.theme.Spacing_16
import me.ks.chan.pica.plus.ui.theme.Spacing_8

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = registerViewModel
) {
    val fields by viewModel.fields.collectAsStateWithLifecycle()
    val state by viewModel.state.collectAsStateWithLifecycle()

    RegisterContent(
        state = state,
        updateState = viewModel::updateState,
        fields = fields,
        updateFields = viewModel::updateFields,
        startRegister = viewModel::registerAccount,
    )
}

@Composable
private fun RegisterContent(
    state: RegisterState,
    updateState: (RegisterState) -> Unit,
    fields: RegisterFields,
    updateFields: (RegisterFields) -> Unit,
    startRegister: () -> Unit,
) {
    Scaffold(
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            TopAppBar(
                title = { 
                    Text(
                        text = stringResource(
                            id = R.string.screen_register_top_bar_title
                        )
                    )
                }
            )
        }
    ) { paddings ->
        @OptIn(ExperimentalLayoutApi::class)
        LazyColumn(
            modifier = Modifier
                .padding(paddings)
                .padding(horizontal = Spacing_16)
                .imePadding()
                .imeNestedScroll()
        ) {
            item {
                RegisterTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = fields.username,
                    onValueChange = {
                        updateFields(fields.copy(username = it))
                    },
                    enabled = state.editable,
                    labelResId = R.string.screen_register_field_username_label,
                    leadingIcon = {
                        Icon(
                            imageVector = Person,
                            contentDescription = stringResource(
                                id = R.string.screen_register_field_username_label
                            )
                        )
                    }
                )
            }

            item {
                RegisterTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = fields.nickname,
                    onValueChange = {
                        updateFields(fields.copy(nickname = it))
                    },
                    enabled = state.editable,
                    labelResId = R.string.screen_register_field_nickname_label,
                    leadingIcon = {
                        Icon(
                            imageVector = WavingHand,
                            contentDescription = stringResource(
                                id = R.string.screen_register_field_nickname_label
                            )
                        )
                    },
                )
            }

            item {
                RegisterPasswordField(
                    modifier = Modifier.fillMaxWidth(),
                    value = fields.password,
                    onValueChange = {
                        updateFields(fields.copy(password = it))
                    },
                    labelResId = R.string.screen_register_field_password_label,
                    contentVisibleDescriptionResId = R.string.screen_register_field_password_description_visible,
                    contentInvisibleDescriptionResId = R.string.screen_register_field_password_description_invisible,
                    enabled = state.editable
                )
            }

            item {
                RegisterPasswordField(
                    modifier = Modifier.fillMaxWidth(),
                    value = fields.passwordConfirm,
                    onValueChange = {
                        updateFields(fields.copy(password = it))
                    },
                    labelResId = R.string.screen_register_field_password_confirm_label,
                    contentVisibleDescriptionResId = R.string.screen_register_field_password_confirm_description_visible,
                    contentInvisibleDescriptionResId = R.string.screen_register_field_password_confirm_description_invisible,
                    enabled = state.editable
                )
            }

            item {
                RegisterGenderField(
                    state = state,
                    genderField = fields.gender,
                    updateGender = { gender ->
                        updateFields(fields.copy(gender = gender))
                    }
                )
            }

            item {
                RegisterBirthdayField(
                    modifier = Modifier.fillMaxWidth(),
                    state = state,
                    birthdayMillisField = fields.birthdayMillis,
                    updateBirthdayMillis = {
                        updateFields(fields.copy(birthdayMillis = it))
                    }
                )
            }

            item {
                RegisterQuestionFields(
                    modifier = Modifier.padding(top = Spacing_8),
                    titleResId = R.string.screen_register_fields_question_a_title,
                    state = state,
                    question = fields.questionA,
                    onQuestionChange = {
                        updateFields(fields.copy(questionA = it))
                    },
                    answer = fields.answerA,
                    onAnswerChange = {
                        updateFields(fields.copy(answerA = it))
                    },
                )
            }

            item {
                RegisterQuestionFields(
                    modifier = Modifier.padding(top = Spacing_8),
                    titleResId = R.string.screen_register_fields_question_b_title,
                    state = state,
                    question = fields.questionB,
                    onQuestionChange = {
                        updateFields(fields.copy(questionB = it))
                    },
                    answer = fields.answerB,
                    onAnswerChange = {
                        updateFields(fields.copy(answerB = it))
                    },
                )
            }

            item {
                RegisterQuestionFields(
                    modifier = Modifier.padding(top = Spacing_8),
                    titleResId = R.string.screen_register_fields_question_c_title,
                    state = state,
                    question = fields.questionC,
                    onQuestionChange = {
                        updateFields(fields.copy(questionC = it))
                    },
                    answer = fields.answerC,
                    onAnswerChange = {
                        updateFields(fields.copy(answerC = it))
                    },
                )
            }

            item {
                RegisterButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = Spacing_8),
                    startRegister = startRegister,
                    fields = fields,
                    state = state,
                    updateState = updateState
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegisterPreview() {
    var state by remember { mutableStateOf<RegisterState>(RegisterState.Pending) }
    var fields by remember { mutableStateOf(RegisterFields()) }
    Surface {
        RegisterContent(
            state = state,
            updateState = {
                state = it
            },
            fields = fields,
            updateFields = {
                fields = it
            },
            startRegister = {
            }
        )
    }
}