package com.example.lpl.presentation.ui

import android.R
import android.content.ActivityNotFoundException
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.example.lpl.common.ShowProgressIndicator
import com.example.lpl.common.TextComponent
import com.example.lpl.common.showToast
import com.example.lpl.data.util.UiState
import com.example.lpl.domian.model.Client

@Composable
fun HomeScree(
    viewModel: MainActivityViewModel = hiltViewModel()
) {

    val clients = viewModel.clients.collectAsStateWithLifecycle().value

    @Composable
    fun ClientCard(client: Client) {
        var openGallery by rememberSaveable { mutableStateOf(false) }
        var updateImage by rememberSaveable { mutableStateOf(false) }
        val galleryErrorMessage = "There is no gallery on your device"

        var selectedImageUri = ""

        if (updateImage) {
            viewModel.updateClientImage(client.id, selectedImageUri)
        }


        val galleryLauncher =
            rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                uri?.let {

                    selectedImageUri = it.toString()

                }
            }

        if (openGallery) {
            try {
                galleryLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
                openGallery = false
            } catch (_: ActivityNotFoundException) {

                LocalContext.current.showToast(galleryErrorMessage)
            }
        }

        Card(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .height(300.dp),
            border = BorderStroke(2.dp, Color.Blue),
        ) {

            Row(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxWidth()
                    .padding( 12.dp),
                //verticalAlignment = Alignment.CenterVertically


            ) {
                Card(
                    modifier = Modifier.padding(top = 16.dp),
                    shape = CircleShape
                )
                {

                    val path = client.image?.let {
                        if (it.isEmpty())
                            R.drawable.ic_menu_add
                        else
                            it
                    } ?: R.drawable.ic_menu_add

                    val painter2 =  rememberAsyncImagePainter(path)

                    Image(
                        painter2,
                        modifier = Modifier
                            .size(80.dp)
                            .padding(5.dp)
                            .clickable{ openGallery = true},
                        colorFilter = ColorFilter.tint(color = Color.Blue),
                        contentDescription = "",
                    )
                }

                Column {

                    TextComponent("Name: ",client.name)
                    TextComponent("ID: ", client.id.toString())
                    TextComponent("Email: ",client.email)
                    TextComponent("Body: ",client.body)
                }
            }
        }
    }

    @Composable
    fun ShowClientsData(clients: List<Client>) {
        LazyColumn(modifier = Modifier.padding(top = 10.dp)) {
            items(clients) { client ->

                ClientCard( client)
            }
        }
    }

    when (clients) {
        is UiState.Loading -> {
            ShowProgressIndicator()
        }

        is UiState.Loaded -> {
            clients.data?.let {
                ShowClientsData(it)
            }
        }

        else -> {
            Text("Nope")
        }
    }
}