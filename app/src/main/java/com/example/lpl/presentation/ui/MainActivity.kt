package com.example.lpl.presentation.ui

import android.R
import android.content.ActivityNotFoundException
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.lpl.data.util.UiState
import com.example.lpl.domian.model.Client
import com.example.lpl.ui.theme.LPLTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlin.collections.forEach
import kotlin.getValue

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        enableEdgeToEdge()
        setContent {
            val clients = viewModel.clients.collectAsStateWithLifecycle().value

            LPLTheme {

                when (clients) {
                    is UiState.Loaded -> {
                        clients.data?.let {
                            Greeting(viewModel, it)
                        }
                    }

                    else -> {
                        Text("Nope")
                    }
                }

            }
        }
    }
}

@Composable
fun Greeting(viewModel: MainActivityViewModel, clients: List<Client>) {
    LazyColumn(modifier = Modifier.padding(top = 10.dp)) {
        items(clients) { client ->

            ClientCard(viewModel, client)
        }
    }
}

@Composable
fun ClientCard(viewModel: MainActivityViewModel, client: Client) {
    var openGallery by rememberSaveable { mutableStateOf(false) }
    var updateImage by rememberSaveable { mutableStateOf(false) }

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

    //LaunchedEffect(openGallery) {
        if (openGallery) {
        try {
            galleryLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
            openGallery = false
        } catch (_: ActivityNotFoundException) {
            //context.showToast(galleryErrorMessage)
        }
        }
    //}

    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .height(300.dp),
        border = BorderStroke(2.dp, Color.Blue),
    ) {

//        val galleryLauncher =
//            rememberLauncherForActivityResult(ActivityResultContracts.PickMultipleVisualMedia()) { uris ->
//                processPickedImages(uris)
//            }

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
fun TextComponent(type: String, text: String) {
    Row {

        Text(
            modifier = Modifier.padding(start = 6.dp, top = 4.dp),
            text = type,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            modifier = Modifier.padding(start = 6.dp, top = 4.dp),
            text = text.replace("\n", ". "),
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal
        )
    }
}