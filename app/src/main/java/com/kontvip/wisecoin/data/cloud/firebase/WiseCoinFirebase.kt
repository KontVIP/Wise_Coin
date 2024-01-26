package com.kontvip.wisecoin.data.cloud.firebase

import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.lang.IllegalArgumentException

interface WiseCoinFirebase {

    fun lastUpdateTimeMillis(
        userId: String,
        onSuccessListener: OnSuccessListener<DataSnapshot>,
        onFailureListener: OnFailureListener
    )

    class Default : WiseCoinFirebase {
        companion object {
            private val DATABASE_URL = System.getProperty("firebaseUrl")!!
        }

        private val database = Firebase.database(DATABASE_URL)
        override fun lastUpdateTimeMillis(
            userId: String,
            onSuccessListener: OnSuccessListener<DataSnapshot>,
            onFailureListener: OnFailureListener
        ) {
            if (userId.isBlank()) {
                onFailureListener.onFailure(IllegalArgumentException("User id is blank!"))
                return
            }
            database.getReference("Users/$userId/lastUpdate").get()
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener)
        }

    }

}