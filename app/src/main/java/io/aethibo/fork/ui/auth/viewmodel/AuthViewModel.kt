package io.aethibo.fork.ui.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.aethibo.data.utils.Resource
import io.aethibo.domain.AccessTokenResponse
import io.aethibo.usecases.GetAccessTokenUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val getAccessToken: GetAccessTokenUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {

    private val _accessTokenStatus: MutableStateFlow<Resource<AccessTokenResponse>> =
        MutableStateFlow(Resource.Init())
    val accessTokenStatus: StateFlow<Resource<AccessTokenResponse>>
        get() = _accessTokenStatus

    fun getAccessToken(clientId: String, clientSecret: String, code: String) {
        _accessTokenStatus.value = Resource.Loading()

        viewModelScope.launch(dispatcher) {
            val result: Resource<AccessTokenResponse> =
                getAccessToken.invoke(clientId, clientSecret, code)

            _accessTokenStatus.value = result
        }
    }
}