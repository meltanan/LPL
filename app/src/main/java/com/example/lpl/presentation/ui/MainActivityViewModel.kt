package com.example.lpl.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
            val clientsListToBeUpdated = clients.toMutableList()

            clientsListToBeUpdated.forEach { client ->
                if (client.id == id) {
                    client.image = imagePath
                }
            }

            _clients.update { UiState.Loaded(clientsListToBeUpdated) }
        }
    }
}