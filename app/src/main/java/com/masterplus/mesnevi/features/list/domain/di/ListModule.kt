package com.masterplus.mesnevi.features.list.domain.di

import com.masterplus.mesnevi.core.data.local.AppDatabase
import com.masterplus.mesnevi.features.list.data.repo.ListContentsRepoImpl
import com.masterplus.mesnevi.features.list.data.repo.ListRepoImpl
import com.masterplus.mesnevi.features.list.data.repo.ListViewRepoImpl
import com.masterplus.mesnevi.features.list.domain.repo.ListContentsRepo
import com.masterplus.mesnevi.features.list.domain.repo.ListRepo
import com.masterplus.mesnevi.features.list.domain.repo.ListViewRepo
import com.masterplus.mesnevi.features.list.domain.use_case.list_contents.AddFavoriteListContents
import com.masterplus.mesnevi.features.list.domain.use_case.list_contents.AddListContents
import com.masterplus.mesnevi.features.list.domain.use_case.list_contents.GetSelectableLists
import com.masterplus.mesnevi.features.list.domain.use_case.list_contents.ListContentsUseCases
import com.masterplus.mesnevi.features.list.domain.use_case.lists.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ListModule {

    @Provides
    @Singleton
    fun provideListRepo(db: AppDatabase): ListRepo =
        ListRepoImpl(db.listDao())

    @Provides
    @Singleton
    fun provideListContentsRepo(db: AppDatabase): ListContentsRepo =
        ListContentsRepoImpl(db.listContentsDao())

    @Provides
    @Singleton
    fun provideListViewRepo(db: AppDatabase): ListViewRepo =
        ListViewRepoImpl(db.listViewDao())


    @Provides
    @Singleton
    fun provideListUseCases(listRepo: ListRepo,
                            listViewRepo: ListViewRepo,
                            listContentsRepo: ListContentsRepo
    ) =
        ListUseCases(
            insertList = InsertList(listRepo),
            updateList = UpdateList(listRepo),
            deleteList = DeleteList(listRepo,listContentsRepo),
            getLists = GetLists(listViewRepo),
            copyList = CopyList(listRepo,listContentsRepo),
            getContentsFromList = GetContentsFromList(listContentsRepo)
        )


    @Provides
    @Singleton
    fun provideContentListsUseCases(
                            listRepo: ListRepo,
                            listViewRepo: ListViewRepo,
                            listContentsRepo: ListContentsRepo
    ) =
        ListContentsUseCases(
            addFavoriteListContents = AddFavoriteListContents(listContentsRepo, listViewRepo, listRepo),
            addListContents = AddListContents(listContentsRepo),
            getSelectableLists = GetSelectableLists(listViewRepo)
        )
}