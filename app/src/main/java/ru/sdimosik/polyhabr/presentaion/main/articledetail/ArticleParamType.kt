package ru.sdimosik.polyhabr.presentaion.main.articledetail

import android.os.Bundle
import androidx.navigation.NavType
import com.google.gson.Gson
import ru.sdimosik.polyhabr.domain.model.ArticleDomain

class ArticleParamType : NavType<ArticleDomain>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): ArticleDomain? {
        return bundle.getParcelable(key)
    }

    override fun parseValue(value: String): ArticleDomain {
        return Gson().fromJson(value, ArticleDomain::class.java)
    }

    override fun put(bundle: Bundle, key: String, value: ArticleDomain) {
        bundle.putParcelable(key, value)
    }
}
