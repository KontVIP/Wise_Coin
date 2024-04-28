package com.kontvip.wisecoin.domain

import com.google.gson.Gson
import com.kontvip.wisecoin.R
import com.kontvip.wisecoin.data.ResourceProvider
import com.kontvip.wisecoin.data.model.MCC

interface MCCInteractor {

    fun findMccById(mccId: Int): MCC

    class Default(
        private val resourceProvider: ResourceProvider,
        private val gson: Gson
    ) : MCCInteractor {

        private val mccList: List<MCC> by lazy {
            gson.fromJson(resourceProvider.retrieveRawAsString(R.raw.mcc), typeToken<List<MCC>>())
        }

        override fun findMccById(mccId: Int): MCC {
            //binary search
            var left = 0
            var right = mccList.size - 1

            while (left <= right) {
                val mid = (left + right) / 2
                val midMcc = mccList[mid]
                val midMccId = midMcc.id

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