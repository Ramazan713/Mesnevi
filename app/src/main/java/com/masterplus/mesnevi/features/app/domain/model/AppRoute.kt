package com.masterplus.mesnevi.features.app.domain.model

import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

sealed class AppRoute(val route:String){

    object DetailBook: AppRoute("detailBook/{bookId}/{scrollPos}"){
        fun navigateWithData(bookId: Int,scrollPos: Int = 0): String{
            return "detailBook/${bookId}/${scrollPos}"
        }
    }

    object SearchResult: AppRoute("searchResult/{query}/{criteriaValue}/{scrollPos}"){
        fun navigateWithData(query: String,criteriaValue: Int,scrollPos: Int = 0): String{
            return "searchResult/$query/$criteriaValue/$scrollPos"
        }
    }

    object SelectSavePoint: AppRoute("selectSavePoint/{title}/{typeIds}"){
        fun navigateWithData(typeIds: List<Int>,title: String): String{
            val encodedArray = Gson().toJson(typeIds.toTypedArray())
            return "selectSavePoint/$title/$encodedArray"
        }
        fun fromJson(arguments: Bundle?): Pair<String,List<Int>>{
            val title = arguments?.getString("title")?:""
            val typeIdStr = arguments?.getString("typeIds")?:""
            val type = TypeToken.get(Array<Int>::class.java).type
            val typeIds = Gson().fromJson<Array<Int>>(typeIdStr,type)
            return Pair(title,typeIds.toList())
        }
    }

    object EditSavePoint: AppRoute("editSavePoint/{typeId}/{saveKey}/{pos}/{shortTitle}"){
        fun navigateWithData(typeId: Int,saveKey: String,pos: Int,shortTitle: String): String{
            return "editSavePoint/$typeId/$saveKey/$pos/$shortTitle"
        }
    }

    object ArchiveList: AppRoute("archiveList"){
        fun navigateWithData():String = route
    }

    object DetailList: AppRoute("detailList/{listId}/{scrollPos}"){
        fun navigateWithData(listId: Int,scrollPos: Int = 0): String{
            return "detailList/$listId/$scrollPos"
        }
    }

}
