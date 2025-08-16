package com.example.lpl.domian.repository

import com.example.lpl.data.util.Resource
import com.example.lpl.domian.model.Client

interface ClientRepository {
    suspend fun getClientsData(): Resource<List<Client>>
}