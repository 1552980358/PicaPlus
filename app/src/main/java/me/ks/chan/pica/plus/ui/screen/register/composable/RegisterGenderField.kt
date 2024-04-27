package me.ks.chan.pica.plus.ui.screen.register.composable

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import me.ks.chan.pica.plus.R
import me.ks.chan.pica.plus.ui.screen.register.viewmodel.RegisterFields
import me.ks.chan.pica.plus.ui.screen.register.viewmodel.RegisterState
import me.ks.chan.pica.plus.ui.theme.Spacing_8

private const val GenderCrossFade = "Gender"

@Composable
fun RegisterGenderField(
    state: RegisterState,
    genderField: RegisterFields.Gender,
    updateGender: (RegisterFields.Gender) -> Unit,
) {
    Column(
        modifier = Modifier.padding(top = Spacing_8)
    ) {
        Text(text = stringResource(id = R.string.screen_register_field_gender_title))

        @OptIn(ExperimentalMaterial3Api::class)
        SingleChoiceSegmentedButtonRow {
            RegisterFields.Gender.entries.forEachIndexed { index, gender ->
                SegmentedButton(
                    selected = genderField == gender,
                    onClick = { updateGender(gender) },
                    enabled = state.editable,
                    shape = SegmentedButtonDefaults.itemShape(
                        index = index,
                        count = RegisterFields.Gender.entries.size
                    ),
                    icon = {
                        Crossfade(
                            targetState = genderField == gender,
                            label = GenderCrossFade,
                        ) { isSelected ->
                            Icon(
                                imageVector = when {
                                    isSelected -> Icons.Rounded.Check
                                    else -> gender.icon
                                },
                                contentDescription = stringResource(
                                    id = when {
                                        isSelected -> gender.descriptionResId
                                        else -> gender.labelResId
                                    }
                                )
                            )
                        }
                    }
                ) {
                    Text(text = stringResource(id = gender.labelResId))
                }
            }
        }
    }
}