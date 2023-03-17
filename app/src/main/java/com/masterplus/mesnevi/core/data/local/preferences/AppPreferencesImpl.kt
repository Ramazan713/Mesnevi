package com.masterplus.mesnevi.core.data.local.preferences

import android.content.SharedPreferences
import android.util.Log
import com.masterplus.mesnevi.core.domain.preferences.AppPreferences
import com.masterplus.mesnevi.core.domain.preferences.model.EnumPrefKey
import com.masterplus.mesnevi.core.domain.preferences.model.IEnumPrefValue
import com.masterplus.mesnevi.core.domain.preferences.model.PrefKey
import com.masterplus.mesnevi.features.search.presentation.search_result.*
import javax.inject.Inject

class AppPreferencesImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
): AppPreferences {

    override fun <T>setItem(item: PrefKey<T>, value: T){
        when(value){
            is String->{
                sharedPreferences.edit().putString(item.key,value).apply()
            }
            is Int->{
                sharedPreferences.edit().putInt(item.key,value).apply()
            }
            is Boolean->{
                sharedPreferences.edit().putBoolean(item.key,value).apply()
            }
            is Float->{
                sharedPreferences.edit().putFloat(item.key,value).apply()
            }
            is Long->{
                sharedPreferences.edit().putLong(item.key,value).apply()
            }
        }
    }

    override fun<T>getItem(item: PrefKey<T>): T{
        val result: T? = when(item.default){
            is String->{
                sharedPreferences.getString(item.key,item.default) as T?
            }
            is Int->{
                sharedPreferences.getInt(item.key,item.default) as T?
            }
            is Boolean->{
                sharedPreferences.getBoolean(item.key,item.default) as T?
            }
            is Float->{
                sharedPreferences.getFloat(item.key,item.default) as T?
            }
            is Long->{
                sharedPreferences.getLong(item.key,item.default) as T?
            }
            else -> {
                null
            }
        }
        return result?:item.default
    }

    override fun <E:Enum<E>> setEnumItem(criteria: EnumPrefKey<E>,value: IEnumPrefValue){
        sharedPreferences.edit().putInt(criteria.key,value.keyValue).apply()
    }

    override fun <E:Enum<E>> getEnumItem(criteria: EnumPrefKey<E>): E{
        val keyValue = sharedPreferences.getInt(criteria.key,criteria.default.keyValue)
        return criteria.from(keyValue)
    }

    override fun clear(){
        sharedPreferences.edit().clear().apply()
    }

    override fun toDict(): Map<String, Any> {
        val result = mutableMapOf<String,Any>()
        AppPreferences.prefValues.forEach { pref->
            val value = getItem(pref)
            result[pref.key] = mapOf("value" to value, "type" to "classic")
        }
        AppPreferences.prefEnumValues.forEach { pref->
            val value = getEnumItem(pref)
            if(value is IEnumPrefValue){
                result[pref.key] = mapOf("value" to value.keyValue, "type" to "enum")
            }

        }
        return result
    }

    override fun fromDict(map: Map<String, Any>) {
        for (key in map.keys){
            val valueDict = (map[key] as? Map<*, *>) ?: continue
            val value = valueDict["value"] ?: continue
            val type = valueDict["type"] ?: continue

            when(type){
                "classic"->{
                    AppPreferences.prefValues.find { it.key == key }?.let { pref->
                        setItem(pref,value)
                    }
                }
                "enum"->{
                    val keyValue = value.toString().toFloatOrNull()?.toInt() ?: continue
                    AppPreferences.prefEnumValues.find { it.key == key }?.let { pref->
                        sharedPreferences.edit().putInt(key,keyValue).apply()
                    }
                }
            }
        }
    }
}