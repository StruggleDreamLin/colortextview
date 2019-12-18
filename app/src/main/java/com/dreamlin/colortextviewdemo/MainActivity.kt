package com.dreamlin.colortextviewdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dreamlin.colortextview.MultiColorTextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tvSetText = findViewById<MultiColorTextView>(R.id.tv_set_texts)
        val tvRegexOne = findViewById<MultiColorTextView>(R.id.tv_regex_one)
        val tvRegexMulti = findViewById<MultiColorTextView>(R.id.tv_regex_multi)
        tvSetText.setColorTexts(
            "第五是骑单车过陌生的马路|" +
                    "在拥挤的人海中踌躇|第四是给空白的纸上画上五线谱|" +
                    "每一行都好像是世界的遗嘱", "#548B54|#6959CD|#76EEC6|#7EC0EE"
        )
        tvRegexOne.applyColorForText("一", "#FF7F24")
        tvRegexMulti.setText(
            "愿你风尘仆仆 深情不被辜负 虽回不到过去 也回不到当初 愿你半生漂浮此生能有归宿 愿你风雨落幕能有人免你孤独"
        )
        tvRegexMulti.applyColorForTexts(
            arrayOf("#FF6EB4", "#FF83FA", "#FF4500"),
            "愿你风尘仆仆", "虽回不到过去", "愿你风雨落幕能有人免你孤独"
        )
    }
}
