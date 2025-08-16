package com.example.lpl.data.repositoryImplementation

import com.example.lpl.data.remote.api.ClientAPI
import com.example.lpl.data.remote.network.APISHelpers

import com.example.lpl.data.util.Resource
import com.example.lpl.domian.model.Client
import com.example.lpl.domian.repository.ClientRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClientRepositoryImpl @Inject constructor(
    private val api: ClientAPI,
    private val apisHelpers: APISHelpers
) : ClientRepository {
    override suspend fun getClientsData(): Resource<List<Client>> {
        return apisHelpers.callApi { api.getClients() }
    }
}