package me.ks.chan.pica.plus.ui.icon

import org.junit.Test
import java.io.File

class XMLToVectorIconFile {

    companion object {
        private const val XML_DIR = "src/test/res/drawable"
        private const val ICON_DIR = "src/main/java/me/ks/chan/pica/plus/ui/icon/"
    }

    @Test
    fun convertTestResDrawableToIcon() {
        File(XML_DIR)
            .listFiles()
            ?.filter { it.extension == "xml" }
            ?.forEach(::handleFileText)
    }

    private val viewportRegex = "^ {4}android:viewportWidth=\"(\\d+)\"".toRegex()
    private val filenameRegex = "^ic_(round|filled)_([a-z_]+)_24.xml\$".toRegex()
    private val pathRegex = "^ {6}android:pathData=\"([MQLZ\\d ,.]+)\"/>".toRegex()
    private val pathSeparator = "(?=[MQLZ])".toRegex()
    private fun handleFileText(
        file: File,
        filename: String = file.name,
        lineList: List<String> = file.readLines()
    ) {
        val viewportSize = lineList.firstNotNullOf { viewportRegex.find(it)?.destructured }
            .component1()

        val (type, name) = filenameRegex.find(filename)!!.destructured
        val iconName = name.lowercase().split("_")
            .joinToString("", transform = { it.replaceFirstChar(Char::uppercase) })

        val outputFile = File(ICON_DIR + type + File.separator + iconName + ".kt")
        if (!outputFile.exists()) {
            outputFile.parentFile?.let {
                if (!it.exists()) {
                    it.mkdirs()
                }
            }

            val path = lineList.firstNotNullOf { pathRegex.find(it)?.destructured }
                .component1()
                .split(pathSeparator)
                .filter(String::isNotBlank).joinToString(separator = "\n        ") {
                    when (it.first()) {
                        'M' -> { packPathMethod("moveTo", it) }
                        'Q' -> { packPathMethod("quadTo", it) }
                        'L' -> { packPathMethod("lineTo", it) }
                        'Z' -> { "close()" }
                        else -> ""
                    }
                }
            // val higher = """
            // |package me.ks.chan.pica.plus.ui.icon.$type
            // |
            // |import androidx.compose.material3.Icon
            // |import androidx.compose.runtime.Composable
            // |import androidx.compose.ui.tooling.preview.Preview
            // |import me.ks.chan.pica.plus.ui.icon.materialSymbol
            // |
            // |private const val Name = "$iconName"
            // |private const val ViewPortSize = ${viewportSize}F
            // |val $iconName by lazy {
            // |    materialSymbol(Name, ViewPortSize) {
            // |
            // """.trimMargin()
            // val lower = """
            // |
            // |    }
            // |}
            // |
            // |@Preview(showBackground = true)
            // |@Composable
            // |private fun $iconName() {
            // |    Icon(imageVector = $iconName, contentDescription = Name)
            // |}
            // """.trimMargin()
            // outputFile.writeText(
            //     higher + path + lower
            // )
            val fileContent = """
            |package me.ks.chan.pica.plus.ui.icon.$type
            |
            |import androidx.compose.material3.Icon
            |import androidx.compose.runtime.Composable
            |import androidx.compose.ui.tooling.preview.Preview
            |import me.ks.chan.pica.plus.ui.icon.materialSymbol
            |
            |private const val Name = "$iconName"
            |private const val ViewPortSize = ${viewportSize}F
            |val $iconName by lazy {
            |    materialSymbol(Name, ViewPortSize) {
            |        $path
            |    }
            |}
            |
            |@Preview(showBackground = true)
            |@Composable
            |private fun $iconName() {
            |    Icon(imageVector = $iconName, contentDescription = Name)
            |}
            """.trimMargin()
            outputFile.writeText(fileContent)
        }
    }

    private fun packPathMethod(method: String, path: String): String {
        return "${method}(${path.let(::parameterOptimize)})"
    }

    private fun parameterOptimize(parameter: String): String {
        return parameter.substring(1)
            .split(",", " ")
            .joinToString(separator = "F, ", postfix = "F")
    }

}