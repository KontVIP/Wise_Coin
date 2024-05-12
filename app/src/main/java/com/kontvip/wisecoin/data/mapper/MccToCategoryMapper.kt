package com.kontvip.wisecoin.data.mapper

import com.google.gson.Gson
import com.kontvip.wisecoin.R
import com.kontvip.wisecoin.data.ResourceProvider
import com.kontvip.wisecoin.data.model.MCC
import com.kontvip.wisecoin.domain.core.typeToken
import dagger.Lazy

interface MccToCategoryMapper {

    fun map(mccId: Int): String

    class Default(
        private val resourceProvider: ResourceProvider,
        private val gson: Lazy<Gson>
    ) : MccToCategoryMapper {

        private val mccList: List<MCC> by lazy {
            gson.get().fromJson(resourceProvider.retrieveRawAsString(R.raw.mcc), typeToken<List<MCC>>())
        }

        override fun map(mccId: Int): String {
            //binary search
            var left = 0
            var right = mccList.size - 1
            var mccCategory = ""

            while (left <= right) {
                val mid = (left + right) / 2
                var midMccId = -1

                mccList[mid].map(object : MCC.Mapper<Unit> {
                    override fun map(id: Int, description: String) {
                        midMccId = id
                        mccCategory = description
                    }
                })

                when {
                    midMccId == mccId -> return mccCategory
                    midMccId < mccId -> left = mid + 1
                    else -> right = mid - 1
                }
            }
            return mccCategory
        }

    }
}