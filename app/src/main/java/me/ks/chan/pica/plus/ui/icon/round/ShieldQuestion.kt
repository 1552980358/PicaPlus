package me.ks.chan.pica.plus.ui.icon.round

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import me.ks.chan.pica.plus.ui.icon.materialSymbol

private const val Name = "ShieldQuestion"
private const val ViewPortSize = 960F
val ShieldQuestion by lazy {
    materialSymbol(Name, ViewPortSize) {
        moveTo(480F, 880F)
        quadTo(341F, 845F, 250.5F, 720.5F)
        quadTo(160F, 596F, 160F, 444F)
        lineTo(160F, 200F)
        lineTo(480F, 80F)
        lineTo(800F, 200F)
        lineTo(800F, 444F)
        quadTo(800F, 596F, 709.5F, 720.5F)
        quadTo(619F, 845F, 480F, 880F)
        close()
        moveTo(480F, 796F)
        quadTo(584F, 763F, 652F, 664F)
        quadTo(720F, 565F, 720F, 444F)
        lineTo(720F, 255F)
        lineTo(480F, 165F)
        lineTo(240F, 255F)
        lineTo(240F, 444F)
        quadTo(240F, 565F, 308F, 664F)
        quadTo(376F, 763F, 480F, 796F)
        close()
        moveTo(480F, 480F)
        quadTo(480F, 480F, 480F, 480F)
        quadTo(480F, 480F, 480F, 480F)
        lineTo(480F, 480F)
        lineTo(480F, 480F)
        lineTo(480F, 480F)
        lineTo(480F, 480F)
        quadTo(480F, 480F, 480F, 480F)
        quadTo(480F, 480F, 480F, 480F)
        close()
        moveTo(480F, 680F)
        quadTo(497F, 680F, 509.5F, 667.5F)
        quadTo(522F, 655F, 522F, 638F)
        quadTo(522F, 621F, 509.5F, 608.5F)
        quadTo(497F, 596F, 480F, 596F)
        quadTo(463F, 596F, 450.5F, 608.5F)
        quadTo(438F, 621F, 438F, 638F)
        quadTo(438F, 655F, 450.5F, 667.5F)
        quadTo(463F, 680F, 480F, 680F)
        close()
        moveTo(481F, 556F)
        quadTo(493F, 556F, 502.5F, 547F)
        quadTo(512F, 538F, 512F, 526F)
        quadTo(513F, 519F, 515F, 512.5F)
        quadTo(517F, 506F, 521F, 500F)
        quadTo(528F, 490F, 536.5F, 482F)
        quadTo(545F, 474F, 553F, 466F)
        quadTo(570F, 449F, 582.5F, 428F)
        quadTo(595F, 407F, 595F, 382F)
        quadTo(595F, 337F, 560.5F, 308.5F)
        quadTo(526F, 280F, 480F, 280F)
        quadTo(448F, 280F, 421F, 295F)
        quadTo(394F, 310F, 378F, 337F)
        quadTo(372F, 348F, 376.5F, 359F)
        quadTo(381F, 370F, 393F, 375F)
        quadTo(404F, 380F, 415F, 374.5F)
        quadTo(426F, 369F, 434F, 360F)
        quadTo(443F, 349F, 454.5F, 343.5F)
        quadTo(466F, 338F, 480F, 338F)
        quadTo(502F, 338F, 518.5F, 351F)
        quadTo(535F, 364F, 535F, 384F)
        quadTo(535F, 401F, 524.5F, 415.5F)
        quadTo(514F, 430F, 501F, 442F)
        quadTo(490F, 453F, 479F, 463.5F)
        quadTo(468F, 474F, 460F, 487F)
        quadTo(455F, 495F, 453F, 505F)
        quadTo(451F, 515F, 451F, 525F)
        quadTo(451F, 538F, 459.5F, 547F)
        quadTo(468F, 556F, 481F, 556F)
        close()
    }
}

@Preview(showBackground = true)
@Composable
private fun ShieldQuestion() {
    Icon(imageVector = ShieldQuestion, contentDescription = Name)
}