package com.github.andrew1121andy.firebasematching.model

import java.io.Serializable

class User: Serializable {
    var uid = "" // ユーザーの識別
    var name = "" // 名前
    var birthday = 0L // 誕生日(UnixTimeで表現)
    var gender = 0 // 性別, 1:男性, 2:女性
    var fromOrdinal = 0 // 出身のFrom enumのordinalを持つ
    var nativeLanguageOrdinal = 0 // LanguageのOrdinal
    var learnLanguageOrdinal = 0 // LanguageのOrdinal

    var hasAllProfile = false
}