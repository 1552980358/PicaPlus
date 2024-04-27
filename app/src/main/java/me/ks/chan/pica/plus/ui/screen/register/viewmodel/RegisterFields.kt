package me.ks.chan.pica.plus.ui.screen.register.viewmodel

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import me.ks.chan.pica.plus.R
import me.ks.chan.pica.plus.ui.icon.round.Bot as BotIcon
import me.ks.chan.pica.plus.ui.icon.round.Gentleman as GentlemanIcon
import me.ks.chan.pica.plus.ui.icon.round.Lady as LadyIcon
import me.ks.chan.pica.plus.util.kotlin.Blank

data class RegisterFields(

    val username: String = Blank,

    val password: String = Blank,

    val passwordConfirm: String = Blank,

    val nickname: String = Blank,

    val birthdayMillis: Long? = null,

    val gender: Gender = Gender.Gentleman,

    val questionA: String = Blank,
    val answerA: String = Blank,

    val questionB: String = Blank,
    val answerB: String = Blank,

    val questionC: String = Blank,
    val answerC: String = Blank
) {

    enum class Gender(
        @StringRes val labelResId: Int,
        val icon: ImageVector,
        @StringRes val descriptionResId: Int,
    ) {
        Gentleman(
            labelResId = R.string.screen_register_field_gender_gentleman_text,
            icon = GentlemanIcon,
            descriptionResId = R.string.screen_register_field_gender_gentleman_description,
        ),

        Lady(
            labelResId = R.string.screen_register_field_gender_lady_text,
            icon = LadyIcon,
            descriptionResId = R.string.screen_register_field_gender_lady_description,
        ),

        Bot(
            labelResId = R.string.screen_register_field_gender_bot_text,
            icon = BotIcon,
            descriptionResId = R.string.screen_register_field_gender_bot_description,
        )
    }

}