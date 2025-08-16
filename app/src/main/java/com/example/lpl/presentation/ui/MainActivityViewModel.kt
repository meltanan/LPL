package com.example.lpl.presentation.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lpl.data.util.Resource
import com.example.lpl.data.util.UiState
import com.example.lpl.domian.model.Client
import com.example.lpl.domian.repository.ClientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(val repository: ClientRepository) : ViewModel() {

    init {
        getClientsData()
    }
    private val _clients = MutableStateFlow<UiState<List<Client>>>(UiState.Loading())
    val clients = _clients
    fun getClientsData() {

        viewModelScope.launch {

            when (val response = repository.getClientsData()) {

                is Resource.Success -> {
                    response.data?.let {

                        _clients.emit(UiState.Loaded(it))
                    } ?: run {
                        _clients.emit(UiState.Loaded(emptyList()))
                    }
                }

                else -> {
                    _clients.emit(UiState.Error("response.uiErrorMessage"))
                }
            }
        }
    }

    fun updateClientImage(id: Int, imagePath: String) {
        _clients.value.data?.let { clients ->

            val k = clients.toMutableList()

            k.forEach { client ->
                client.image = imagePath
            }

            _clients.update { UiState.Loaded(k) }


//            clients.find { it.id == id }?.let { client ->
//
//                val updatedClientImage = client.copy(image = imagePath)
//
//                var ss = clients.toMutableList()
//
////                ss.forEach {
////                    if (it.id == id)
////
////                }
//
//            }
        }
    }
}