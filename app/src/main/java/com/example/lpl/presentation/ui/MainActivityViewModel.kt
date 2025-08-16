package com.example.lpl.presentation.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lpl.domian.repository.ClientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor() : ViewModel() {

     fun getClientsData() {

         Log.d("demo", "yes")
//        viewModelScope.launch {
//            val res = clientRepo.getClientsData().data
//
//            Log.d("demo", res.toString())
//        }
    }
}