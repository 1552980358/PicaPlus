package me.ks.chan.pica.plus.util.androidx.navigation

private val String.asParam
    get() = "{$this}"

fun String.args(vararg args: Pair<String, String>): String {
    return StringBuilder(this)
        .also { stringBuilder ->
            args.forEach { (param, value) ->
                param.asParam.let { paramArg ->
                    stringBuilder.indexOf(paramArg).let { index: Int ->
                        if (index != -1) {
                            stringBuilder.replace(index, index + paramArg.length, value)
                        }
                    }
                }
            }
        }.toString()
}
