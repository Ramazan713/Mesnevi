package com.masterplus.mesnevi.features.list.domain.model

data class ListView(
    val id: Int?,
    val name: String,
    val isRemovable: Boolean,
    val isArchive: Boolean,
    val listPos: Int,
    val contentMaxPos: Int,
    val itemCounts: Int
)
