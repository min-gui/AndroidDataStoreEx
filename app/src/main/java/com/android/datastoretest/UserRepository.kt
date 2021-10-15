package com.android.datastoretest

import android.content.Context
import androidx.datastore.core.DataStore

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

//top level
private val Context.dataStroe : DataStore<Preferences> by preferencesDataStore(
    name = "settings"
)
class UserRepository(context: Context) {


    //내부에 context 를 받아 사용하는 Datastore 인스턴스를 만듭니다.
    private val mDataStore : DataStore<Preferences> = context.dataStroe

    //키값의 데이터타입 및 이름정의.
    private object PreferencesKeys {
        val KEY_NAME = stringPreferencesKey("name")
        val KEY_NUM = intPreferencesKey("num")
    }


    //데이터 읽기
    suspend fun readNum() : Int? {
        val preferences = mDataStore.data.first()
        return preferences[PreferencesKeys.KEY_NUM] ?: 0
    }

    suspend fun readName() :String? {
        val preferences = mDataStore.data.first()
        return preferences[PreferencesKeys.KEY_NAME] ?: "null"
    }



    //데이터 생성.
    suspend fun createStoreData() {
        mDataStore.edit { preferences ->
            preferences[PreferencesKeys.KEY_NUM] = 1111
            preferences[PreferencesKeys.KEY_NAME] = "James"
        }
    }

    var flowNum: Flow<Int> = mDataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.KEY_NUM] ?: 0
        }

    var flowName: Flow<String> = mDataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.KEY_NAME] ?: ""
        }

}