package me.ks.chan.pica.plus.ui.screen.register.viewmodel

sealed interface RegisterState {

    val editable: Boolean
        get() = this !is Loading

    val submittable: Boolean
        get() = this !is Pending && this !is Loading

    data object Pending: RegisterState

    data object Submittable: RegisterState

    data object Loading: RegisterState {
        override val editable: Boolean
            get() = false
    }

    data object Success: RegisterState

    sealed interface Error: RegisterState {

        data object Network: Error

        data object DuplicateUsername: Error

        data object UnknownResponse: Error

        data object Unknown: Error

    }

}