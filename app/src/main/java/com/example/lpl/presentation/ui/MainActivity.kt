package com.example.lpl.presentation.ui

import android.R
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
                            Greeting(it)
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
fun Greeting(clients: List<Client>) {
    LazyColumn(modifier = Modifier.padding(top = 10.dp)) {
        items(clients) { client ->

            ClientCard(client)
        }
    }
}

@Composable
fun ClientCard(client: Client) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .height(240.dp),
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
                .padding(vertical = 12.dp, horizontal = 16.dp),
            //verticalAlignment = Alignment.CenterVertically


        ) {
            Card(
                modifier = Modifier.padding(top = 16.dp),
                shape = CircleShape,
                //elevation = 2.dp
            )
            {
                val painter = rememberAsyncImagePainter(R.drawable.ic_menu_add)

                Image(
                    painter,
                    modifier = Modifier
                        .size(80.dp)
                        .padding(5.dp),
                    colorFilter = ColorFilter.tint(color = Color.Blue),
                    contentDescription = "",
                )
            }

            Column {

                TextComponent("Name: ${client.name}")
                TextComponent("ID: ${client.id}")
                TextComponent("Email: ${client.email}")
                TextComponent("Body: ${client.body}")
            }
        }

    }

}

@Composable
fun TextComponent(text: String) {
    Text(
        modifier = Modifier.padding(start = 6.dp, top = 4.dp),
        text = text,
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LPLTheme {

        Card(
            modifier = Modifier.fillMaxSize(),
            shape = CircleShape,
            //elevation = 2.dp
        )
        {

            val painter = rememberAsyncImagePainter(R.drawable.ic_popup_sync)

            Image(
                painter,
                modifier = Modifier
                    .fillMaxSize(.3f)
                    .padding(5.dp),
                colorFilter = ColorFilter.tint(color = Color.Blue),
                contentDescription = "",
            )
        }
    }
}