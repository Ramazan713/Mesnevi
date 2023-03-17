package com.masterplus.mesnevi.core.domain.constants

import com.masterplus.mesnevi.R
import com.masterplus.mesnevi.core.domain.util.UiText
import com.masterplus.mesnevi.features.search.domain.model.SearchCriteria

sealed class SavePointType(val typeId: Int, val description: UiText){
    data class Book(val bookId: Int): SavePointType(1,UiText.Resource(R.string.notebook)) {
        override fun toSaveKey(): String {
            return "1-$bookId"
        }

        companion object{
            val typeId get() = 1
            fun fromSaveKey(saveKey: String): Book? {
                val arr = saveKey.split("-")
                if(arr.size!=2)return null
                val bookId = arr[1].toIntOrNull() ?: return null
                return Book(bookId)
            }
        }
    }


    data class List(val listId: Int): SavePointType(2,UiText.Resource(R.string.list)) {
        override fun toSaveKey(): String {
            return "1-$listId"
        }
        companion object{
            val typeId get() = 2
            fun fromSaveKey(saveKey: String): List? {
                val arr = saveKey.split("-")
                if(arr.size!=2)return null
                val listId = arr[1].toIntOrNull() ?: return null
                return List(listId)
            }
        }
    }


    data class Search(val query: String,val criteria: SearchCriteria): SavePointType(3,UiText.Resource(R.string.search)) {
        override fun toSaveKey(): String {
            return "1-${criteria.keyValue}-$query"
        }
        companion object{
            val typeId get() = 3
            fun fromSaveKey(saveKey: String): Search? {
                val arr = saveKey.split("-")
                if(arr.size!=3)return null
                val criteriaValue = arr[1].toIntOrNull() ?: return null
                val criteria = SearchCriteria.fromKeyValue(criteriaValue)
                val query = arr[2]
                return Search(query, criteria)
            }
        }
    }

    abstract fun toSaveKey(): String

    companion object{
        fun fromTypeId(typeId: Int,saveKey: String): SavePointType?{
            return when(typeId){
                1-> Book.fromSaveKey(saveKey)
                2-> List.fromSaveKey(saveKey)
                3-> Search.fromSaveKey(saveKey)
                else-> null
            }
        }
    }

}
