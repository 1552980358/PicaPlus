package me.ks.chan.pica.plus.ui.screen.register.model

import me.ks.chan.pica.plus.ui.screen.register.viewmodel.RegisterFields
import me.ks.chan.pica.plus.util.kotlinx.coroutine.defaultBlocking

private const val UsernameRegex = """/[\dA-Za-z._]{4,16}/g"""
private const val PasswordRegex = """/[\dA-Za-z.]{8,16}/g"""

interface RegisterFieldsSubmittable {

    suspend fun check(fields: RegisterFields): Boolean

    companion object: RegisterFieldsSubmittable {

        override suspend fun check(fields: RegisterFields): Boolean {
            return (
                username(fields.username) &&
                password(fields.password) &&
                fields.password == fields.passwordConfirm
            )
        }

        private suspend inline fun regexCheck(
            regex: String,
            value: String
        ) = defaultBlocking {
            regex.toRegex().matches(value)
        }

        private suspend inline fun username(username: String) =
            regexCheck(UsernameRegex, username)

        private suspend inline fun password(password: String) =
            regexCheck(PasswordRegex, password)

    }

}