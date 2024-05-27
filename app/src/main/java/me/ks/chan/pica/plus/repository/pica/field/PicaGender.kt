package me.ks.chan.pica.plus.repository.pica.field

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class PicaGender {

    @SerialName("m")
    Gentleman,

    @SerialName("f")
    Lady,

    @SerialName("bot")
    Bot,

}