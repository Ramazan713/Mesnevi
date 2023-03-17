package com.masterplus.mesnevi.core.data.local

import androidx.room.withTransaction

class TransactionProvider(
    private val db: AppDatabase
) {
    suspend fun <R> runAsTransaction(block: suspend () -> R): R {
        return db.withTransaction(block)
    }
}