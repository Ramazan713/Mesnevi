package com.masterplus.mesnevi.features.list.data.local

import androidx.room.DatabaseView

@DatabaseView(viewName = "listViews", value = """
     select L.id, L.name, L.isRemovable, L.isArchive, L.pos listPos,
     count(LC.id) itemCounts, ifnull(max(LC.pos),0) contentMaxPos 
     from Lists L left join  ListContents LC on  L.id = LC.listId
     group by L.id
""")
data class ListViewEntity(
    val id: Int?,
    val name: String,
    val isRemovable: Boolean,
    val isArchive: Boolean,
    val listPos: Int,
    val contentMaxPos: Int,
    val itemCounts: Int
)
