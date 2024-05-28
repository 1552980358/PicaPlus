package me.ks.chan.pica.plus.ui.icon.round

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import me.ks.chan.pica.plus.ui.icon.materialSymbol

private const val Name = "Star"
private const val ViewPortSize = 960F
val Star by lazy {
    materialSymbol(Name, ViewPortSize) {
        moveTo(354F, 673F)
        lineTo(480F, 597F)
        lineTo(606F, 674F)
        lineTo(573F, 530F)
        lineTo(684F, 434F)
        lineTo(538F, 421F)
        lineTo(480F, 285F)
        lineTo(422F, 420F)
        lineTo(276F, 433F)
        lineTo(387F, 530F)
        lineTo(354F, 673F)
        close()
        moveTo(480F, 691F)
        lineTo(314F, 791F)
        quadTo(303F, 798F, 291F, 797F)
        quadTo(279F, 796F, 270F, 789F)
        quadTo(261F, 782F, 256F, 771.5F)
        quadTo(251F, 761F, 254F, 748F)
        lineTo(298F, 559F)
        lineTo(151F, 432F)
        quadTo(141F, 423F, 138.5F, 411.5F)
        quadTo(136F, 400F, 140F, 389F)
        quadTo(144F, 378F, 152F, 371F)
        quadTo(160F, 364F, 174F, 362F)
        lineTo(368F, 345F)
        lineTo(443F, 167F)
        quadTo(448F, 155F, 458.5F, 149F)
        quadTo(469F, 143F, 480F, 143F)
        quadTo(491F, 143F, 501.5F, 149F)
        quadTo(512F, 155F, 517F, 167F)
        lineTo(592F, 345F)
        lineTo(786F, 362F)
        quadTo(800F, 364F, 808F, 371F)
        quadTo(816F, 378F, 820F, 389F)
        quadTo(824F, 400F, 821.5F, 411.5F)
        quadTo(819F, 423F, 809F, 432F)
        lineTo(662F, 559F)
        lineTo(706F, 748F)
        quadTo(709F, 761F, 704F, 771.5F)
        quadTo(699F, 782F, 690F, 789F)
        quadTo(681F, 796F, 669F, 797F)
        quadTo(657F, 798F, 646F, 791F)
        lineTo(480F, 691F)
        close()
        moveTo(480F, 490F)
        lineTo(480F, 490F)
        lineTo(480F, 490F)
        lineTo(480F, 490F)
        lineTo(480F, 490F)
        lineTo(480F, 490F)
        lineTo(480F, 490F)
        lineTo(480F, 490F)
        lineTo(480F, 490F)
        lineTo(480F, 490F)
        lineTo(480F, 490F)
        close()
    }
}

@Preview(showBackground = true)
@Composable
private fun Star() {
    Icon(imageVector = Star, contentDescription = Name)
}