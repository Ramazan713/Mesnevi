package com.masterplus.mesnevi.features.app.presentation.navigation

import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.masterplus.mesnevi.core.data.K
import com.masterplus.mesnevi.features.app.domain.model.AppRoute
import com.masterplus.mesnevi.features.app.domain.model.BottomNavRoute
import com.masterplus.mesnevi.features.list.presentation.show_list.ShowListPage
import com.masterplus.mesnevi.features.savepoint.presentation.edit_savepoint.EditSavePointPage
import com.masterplus.mesnevi.features.savepoint.presentation.select_savepoint.SelectSavePointPage
import com.masterplus.mesnevi.features.search.domain.model.SearchCriteria
import com.masterplus.mesnevi.features.search.presentation.search_result.SearchResultPage
import com.masterplus.mesnevi.features.books.presentation.show_books.ShowBookPage
import com.masterplus.mesnevi.features.books.presentation.detail_book.DetailBookPage
import com.masterplus.mesnevi.features.list.presentation.archive_list.ArchiveListPage
import com.masterplus.mesnevi.features.list.presentation.detail_list.DetailListPage
import com.masterplus.mesnevi.features.settings.presentation.SettingPage

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    bottomBarVisibilityViewModel: BottomBarVisibilityViewModel = hiltViewModel()
){
    NavHost(navController, startDestination = BottomNavRoute.Home.route, modifier) {
        composable(BottomNavRoute.Home.route) {
            ShowBookPage(
                onNavigateToDetailBook = {
                    navController.navigate(
                        AppRoute.DetailBook.navigateWithData(it.id?:0)
                    ) },
                onNavigateToSelectSavePoint = {title,typeIds: List<Int> ->
                    navController.navigate(AppRoute.SelectSavePoint
                        .navigateWithData(typeIds, title))
                },
                onNavigateToSearchResult = {query,criteria->
                    navController.navigate(AppRoute.SearchResult.navigateWithData(query,criteria.keyValue))
                },
            )
        }
        composable(BottomNavRoute.Setting.route) {
            SettingPage()
        }
        composable(BottomNavRoute.List.route) {
            ShowListPage(
                onNavigateToDetailList = {listId ->
                    navController.navigate(AppRoute.DetailList.navigateWithData(listId))
                },
                onNavigateToSelectSavePoint = {title,typeIds: List<Int> ->
                    navController.navigate(AppRoute.SelectSavePoint
                        .navigateWithData(typeIds, title))
                },
                onNavigateToArchive = {navController.navigate(AppRoute.ArchiveList.navigateWithData())}
            )
        }
        composable(AppRoute.ArchiveList.route){
            ArchiveListPage(
                onNavigateBack = {navController.popBackStack()},
                onNavigateToDetailList = {listId ->
                    navController.navigate(AppRoute.DetailList.navigateWithData(listId))
                },
            )
        }
        composable(
            AppRoute.DetailList.route,
            arguments = listOf(
                navArgument("listId"){type = NavType.IntType},
                navArgument("scrollPos"){type = NavType.IntType}
            )
        ){stackEntry->
            val listId = stackEntry.arguments?.getInt("listId") ?: 0
            val scrollPos = stackEntry.arguments?.getInt("scrollPos")?:0

            DetailListPage(
                listId = listId,
                scrollPos = scrollPos,
                onNavigateBack = {navController.popBackStack()},
                onNavigateToShowBook = {bookId,pos->
                    navController.navigate(AppRoute.DetailBook.navigateWithData(bookId, pos))
                }
            )
        }

        composable(
            AppRoute.SearchResult.route,
            arguments = listOf(
                navArgument("query"){type = NavType.StringType},
                navArgument("criteriaValue"){type = NavType.IntType},
                navArgument("scrollPos"){type = NavType.IntType}
            )
        ){stackEntry ->
            val query = stackEntry.arguments?.getString("query")?:""
            val criteriaValue = stackEntry.arguments?.getInt("criteriaValue")?:
                SearchCriteria.defaultValue.keyValue
            val scrollPos = stackEntry.arguments?.getInt("scrollPos")?:0
            SearchResultPage(query,criteriaValue,scrollPos,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToShowBook = {bookId,pos->
                    navController.navigate(AppRoute.DetailBook.navigateWithData(bookId,pos))
                }
            )
        }

        composable(
            AppRoute.EditSavePoint.route,
            arguments = listOf(
                navArgument("typeId"){type = NavType.IntType},
                navArgument("saveKey"){type = NavType.StringType},
                navArgument("pos"){type = NavType.IntType},
                navArgument("shortTitle"){type = NavType.StringType}
            )
        ){stackEntry->
            val typeId = stackEntry.arguments?.getInt("typeId")?:1
            val shortTitle = stackEntry.arguments?.getString("shortTitle")?:""
            val saveKey = stackEntry.arguments?.getString("saveKey")?:""
            val pos = stackEntry.arguments?.getInt("pos")?:0

            EditSavePointPage(
                typeId = typeId,
                saveKey = saveKey,
                pos = pos,
                shortTitle = shortTitle,
                onClosed = {navController.popBackStack()},
                onNavigateLoad = {savePoint ->
                    navController.previousBackStackEntry?.savedStateHandle?.
                            set("pos",savePoint.itemPosIndex)
                    navController.popBackStack()
                }
            )
        }

        composable(
            AppRoute.SelectSavePoint.route,
            arguments = listOf(
                navArgument("typeIds"){type = NavType.StringType},
                navArgument("title"){type = NavType.StringType}
            )
        ){stackEntry->
            val args = AppRoute.SelectSavePoint.fromJson(stackEntry.arguments)

            SelectSavePointPage(
                typeIds = args.second,
                title = args.first,
                onNavigateBack = {navController.popBackStack()},
                onNavigateToSearch = {query, criteriaValue, pos ->
                    navController.navigate(AppRoute.SearchResult.navigateWithData(query, criteriaValue,pos))
                },
                onNavigateToDetailBook = {bookId, pos ->
                    navController.navigate(AppRoute.DetailBook.navigateWithData(bookId,pos))
                },
                onNavigateToList = {listId, pos ->
                    navController.navigate(AppRoute.DetailList.navigateWithData(listId,pos))
                }
            )
        }

        composable(
            AppRoute.DetailBook.route,
            deepLinks = listOf(
                navDeepLink {
                    action = Intent.ACTION_VIEW
                    uriPattern = "${K.shareLinkUrl}/{bookId}/{scrollPos}"
                }
            ),
            arguments = listOf(
                navArgument("bookId") { type = NavType.IntType },
                navArgument("scrollPos") { type = NavType.IntType }
            )
        ){backStackEntry->

            val bookId = backStackEntry.arguments?.getInt("bookId")?:0
            val scrollPos = backStackEntry.arguments?.getInt("scrollPos")?:0

            val pointPos = backStackEntry.savedStateHandle.get<Int>("pos")
            backStackEntry.savedStateHandle.remove<Int>("pos")

            DetailBookPage(
                bookId = bookId,
                scrollPos = pointPos?:scrollPos,
                onNavigatePop = {navController.popBackStack()},
            )
        }
    }
}