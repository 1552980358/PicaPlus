package me.ks.chan.pica.plus.ui.screen.greeting

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.serialization.SerializationException
import me.ks.chan.pica.plus.repository.pica.users.profile.PicaProfileRepository
import me.ks.chan.pica.plus.repository.pica.users.profile.PicaProfileRepositoryResult
import me.ks.chan.pica.plus.ui.screen.greeting.model.GreetingState
import me.ks.chan.pica.plus.util.kotlinx.coroutine.defaultJob
import java.io.IOException

val greetingViewModel: GreetingViewModel
    @Composable
    get() = viewModel()

class GreetingViewModel: ViewModel() {

    private val _loadingState = MutableStateFlow<GreetingState>(GreetingState.Loading)
    val loadingState = _loadingState.asStateFlow()

    init {
        viewModelScope.defaultJob {
            PicaProfileRepository.repositoryFlow
                .catch { throwable ->
                    _loadingState.value = GreetingState.Error(
                        type = when (throwable) {
                            is IOException -> {
                                GreetingState.Error.Type.Connection
                            }
                            is SerializationException,
                            is IllegalArgumentException -> {
                                GreetingState.Error.Type.InvalidResponse
                            }
                            else -> {
                                GreetingState.Error.Type.Unknown
                            }
                        }
                    )
                }
                .collect { result ->
                    _loadingState.value = when (result) {
                        is PicaProfileRepositoryResult.Success -> {
                            GreetingState.Success(
                                nickname = result.nickname,
                                avatar = result.avatar
                            )
                        }
                        is PicaProfileRepositoryResult.Error -> {
                            GreetingState.Error(
                                type = when (result.type) {
                                    PicaProfileRepositoryResult.Error.Type.Unauthorized -> {
                                        GreetingState.Error.Type.Unauthorized
                                    }
                                    else -> {
                                        GreetingState.Error.Type.Unknown
                                    }
                                }
                            )
                        }
                    }
                }
        }
    }

}