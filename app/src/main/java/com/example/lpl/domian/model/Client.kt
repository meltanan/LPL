package com.example.lpl.domian.model

data class Client(
    val postId: Int,
    val id: Int,
    val name: String,
    val email: String,
    val body: String,
    var image: String = ""
)
