package com.masterplus.mesnevi.features.search.domain.model

import com.masterplus.mesnevi.R
import com.masterplus.mesnevi.core.domain.constants.IMenuItemEnum
import com.masterplus.mesnevi.core.domain.constants.IconInfo
import com.masterplus.mesnevi.core.domain.preferences.model.IEnumPrefValue
import com.masterplus.mesnevi.core.domain.util.UiText

enum class SearchCriteria(
    override val title: UiText,
    val isRegEx: Boolean,
    override val keyValue: Int
): IEnumPrefValue, IMenuItemEnum {
    inMultipleKeys(
        title = UiText.Resource(R.string.search_in_multiple_keys),
        isRegEx = true,
        keyValue = 1
    ) {
        override fun getFTSMatchQueryExpression(query: String): String {
            return query.split(" ").map { "*${it}*" }.joinToString(separator = " ")
        }

        override val iconInfo: IconInfo?
            get() = null
    },
    multipleKeys(
        title = UiText.Resource(R.string.search_multiple_keys),
        isRegEx = true,
        keyValue = 2
    ) {

        override fun getFTSMatchQueryExpression(query: String): String {
            return query
        }
        override val iconInfo: IconInfo?
            get() = null
    },
    inOneExpression(
        title = UiText.Resource(R.string.search_in_one_expression),
        isRegEx = false,
        keyValue = 3
    ) {
        override fun getFTSMatchQueryExpression(query: String): String {
            return query.split(" ").map { "*$it*" }.joinToString(separator = " ").let {
                "\"$it\""
            }
        }
        override val iconInfo: IconInfo?
            get() = null
    },
    oneExpression(
        title = UiText.Resource(R.string.search_one_expression),
        isRegEx = true,
        keyValue = 4
    ) {
        override fun getFTSMatchQueryExpression(query: String): String {
            return "\"$query\""
        }
        override val iconInfo: IconInfo?
            get() = null
    };

    abstract fun getFTSMatchQueryExpression(query: String): String

    companion object{

        val defaultValue = inMultipleKeys

        fun fromKeyValue(keyValue: Int): SearchCriteria {
            return when(keyValue){
                1-> inMultipleKeys
                2-> multipleKeys
                3-> inOneExpression
                4-> oneExpression
                else -> defaultValue
            }
        }
    }
}