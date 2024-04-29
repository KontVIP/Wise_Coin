package com.kontvip.wisecoin.data

import com.google.gson.Gson
import com.kontvip.wisecoin.R
import com.kontvip.wisecoin.data.core.IdRequest
import com.kontvip.wisecoin.data.model.MCC
import com.kontvip.wisecoin.domain.typeToken
import dagger.Lazy

interface MCCMapper {

    fun map(mccId: Int): MCC

    class Default(
        private val resourceProvider: ResourceProvider,
        private val gson: Lazy<Gson>
    ) : MCCMapper {

        private val mccList: List<MCC> by lazy {
            gson.get().fromJson(resourceProvider.retrieveRawAsString(R.raw.mcc), typeToken<List<MCC>>())
        }

        override fun map(mccId: Int): MCC {
            //binary search
            var left = 0
            var right = mccList.size - 1

            while (left <= right) {
                val mid = (left + right) / 2
                val midMcc = mccList[mid]
                var midMccId = -1
                midMcc.onIdRequested(object : IdRequest {
                    override fun onIdProvided(id: String) {
                        id.toIntOrNull()?.let { midMccId = it }
                    }
                })

                when {
                    midMccId == mccId -> return midMcc
                    midMccId < mccId -> left = mid + 1
                    else -> right = mid - 1
                }
            }
            return MCC(-1, "")
        }

    }
}