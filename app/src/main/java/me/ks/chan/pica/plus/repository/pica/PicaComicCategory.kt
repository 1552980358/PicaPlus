package me.ks.chan.pica.plus.repository.pica

import me.ks.chan.pica.plus.R

enum class PicaComicCategory(
    val raw: String,
    val titleResId: Int,
) {

    EveryoneReading("大家都在看", R.string.pica_category_everyone_reading),

    HeroRecommendation("大濕推薦", R.string.pica_category_master_recommendation),

    TodayInPreviousYears("那年今天", R.string.pica_category_today_in_previous_years),

    PicaRecommendation("官方都在看", R.string.pica_category_pica_recommendation),

    PicaTranslated("嗶咔漢化", R.string.pica_category_pica_translated),

    FullColor("全彩", R.string.pica_category_full_color),

    LongStory("長篇", R.string.pica_category_long_story),

    Doujin("同人", R.string.pica_category_doujin),

    ShortStory("短篇", R.string.pica_category_short_story),

    MadokaShin("圓神領域", R.string.pica_category_madoka_shin),

    GranblueFantasy("碧藍幻想", R.string.pica_category_granblue_fantasy),

    CG("CG雜圖", R.string.pica_category_cg),

    English("英語 ENG", R.string.pica_category_english),

    Raw("生肉", R.string.pica_category_raw),

    PureLove("純愛", R.string.pica_category_pure_love),

    Lesbian("百合花園", R.string.pica_category_lesbian),

    Gay("耽美花園", R.string.pica_category_gay),

    Otokonoko("偽娘哲學", R.string.pica_category_otokonoko),

    Harem("後宮閃光", R.string.pica_category_harem),

    Futanari("扶他樂園", R.string.pica_category_futanari),

    Tankobon("單行本", R.string.pica_category_tankobon),

    ElderSister("姐姐系", R.string.pica_category_elder_sister),

    YoungerSister("妹妹系", R.string.pica_category_younger_sister),

    Sadomasochism("SM", R.string.pica_category_sadomasochism),

    SexualSwap("性轉換", R.string.pica_category_sexual_swap),

    FootFetishism("足の恋", R.string.pica_category_foot_fetishism),

    MarriedWoman("人妻", R.string.pica_category_married_woman),

    NTR("NTR", R.string.pica_category_ntr),

    Rape("強暴", R.string.pica_category_rape),

    NonHuman("非人類", R.string.pica_category_non_human),

    KantaiCollection("艦隊收藏", R.string.pica_category_kantai_collection),

    LoveLive("Love Live", R.string.pica_category_love_live),

    SwardOnline("SAO 刀劍神域", R.string.pica_category_sward_online),

    Fate("Fate", R.string.pica_category_fate),

    TohoProject("東方", R.string.pica_category_toho_project),

    WebCartoon("WEBTOON", R.string.pica_category_web_cartoon),

    Copyrighted("禁書目錄", R.string.pica_category_copyrighted),

    Western("歐美", R.string.pica_category_western),

    Cosplay("Cosplay", R.string.pica_category_cosplay),

    Bizarre("重口地帶", R.string.pica_category_bizarre)

}