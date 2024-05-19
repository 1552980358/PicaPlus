package me.ks.chan.pica.plus.ui.screen.categroy

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.delay
import me.ks.chan.pica.plus.R
import me.ks.chan.pica.plus.repository.pica.PicaComicCategory
import me.ks.chan.pica.plus.ui.composable.scaffold.ProvideNavBarItemSecondaryClick
import me.ks.chan.pica.plus.ui.icon.round.Search
import me.ks.chan.pica.plus.ui.screen.categroy.composable.CategoryListItem
import me.ks.chan.pica.plus.ui.screen.categroy.composable.categoryListShimmer
import me.ks.chan.pica.plus.ui.screen.categroy.viewmodel.CategoryModel
import me.ks.chan.pica.plus.ui.screen.categroy.viewmodel.CategoryState
import me.ks.chan.pica.plus.util.kotlinx.coroutine.defaultJob

@Composable
fun CategoryScreen() {
    val viewModel = categoryViewModel

    val state by viewModel.state.collectAsStateWithLifecycle()
    CategoryContent(
        state = state,
    )
}
private const val ScrollBehaviorHeightOffsetAnimation =
    "ScrollBehaviorHeightOffsetAnimation"

@Composable
private fun CategoryContent(
    state: CategoryState,
) {
    @OptIn(ExperimentalMaterial3Api::class)
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        canScroll = { state is CategoryState.Success }
    )

    val coroutineScope = rememberCoroutineScope()

    val lazyListState = rememberLazyListState()
    ProvideNavBarItemSecondaryClick {
        coroutineScope.defaultJob {
            lazyListState.animateScrollToItem(0)
            @OptIn(ExperimentalMaterial3Api::class)
            scrollBehavior.state.heightOffset -= scrollBehavior.state.heightOffsetLimit
        }
    }

    Scaffold(
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            MediumTopAppBar(
                title = {
                    Text(
                        text = stringResource(
                            id = R.string.screen_category_top_bar_title
                        )
                    )
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Search,
                            contentDescription = stringResource(
                                id = R.string.screen_category_top_bar_action_search
                            )
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        }
    ) { innerPaddings ->
        @OptIn(ExperimentalMaterial3Api::class)
        LazyColumn(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            state = lazyListState,
            contentPadding = innerPaddings,
        ) {
            when (state) {
                is CategoryState.Loading,
                is CategoryState.Error -> {
                    categoryListShimmer()
                }
                is CategoryState.Success -> {
                    items(items = state.categoryList) { category ->
                        CategoryListItem(category = category)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun CategoryPreview() {
    var state by remember {
        mutableStateOf<CategoryState>(CategoryState.Loading)
    }

    LaunchedEffect(key1 = Unit) {
        delay(5000)
        state = CategoryState.Success(
            PicaComicCategory.entries
                .map(::CategoryModel)
        )
    }
    CategoryContent(state = state)
}