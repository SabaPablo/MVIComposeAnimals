package com.doce.cactus.saba.nvianimalscompose

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import coil.compose.rememberImagePainter
import com.doce.cactus.saba.nvianimalscompose.api.AnimalService
import com.doce.cactus.saba.nvianimalscompose.model.Animal
import com.doce.cactus.saba.nvianimalscompose.ui.theme.NVIAnimalsComposeTheme
import com.doce.cactus.saba.nvianimalscompose.view.MainIntent
import com.doce.cactus.saba.nvianimalscompose.view.MainState
import com.doce.cactus.saba.nvianimalscompose.view.MainViewModel
import com.doce.cactus.saba.nvianimalscompose.view.ViewModelFactory
import kotlinx.coroutines.launch

class MainActivity : FragmentActivity() {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProviders
            .of(this,ViewModelFactory(AnimalService.api))
            .get(MainViewModel::class.java)

        val onButtonClick: () -> Unit ={
            lifecycleScope.launch {
                mainViewModel.userIntent.send(MainIntent.FetchAnimals)
            }
        }

        setContent {
            NVIAnimalsComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    MainScreen(vm = mainViewModel, onButtonClick)
                }
            }
        }
    }
}


@Composable
fun MainScreen(vm: MainViewModel, onButtonClick : () -> Unit){
    when(val state = vm.state.value){
        is MainState.Idle -> IdleScreen(onButtonClick)
        is MainState.Animals -> AnimalsList(state.animals)
        is MainState.Error -> {
            IdleScreen(onButtonClick)
            Toast.makeText(LocalContext.current, state.error, Toast.LENGTH_SHORT).show()
        }
        MainState.Loading -> LoadingScreen()
    }
}

@Composable
fun IdleScreen(onButtonClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Button(onClick =  onButtonClick ) {
            Text(text = "Fetch Animals")
        }
    }
}

@Composable
fun AnimalsList(animals: List<Animal>) {
    LazyColumn{
        items(items= animals){
            AnimalItem(animal = it)
            Divider(color= Color.LightGray, modifier = Modifier.padding(top = 4.dp, bottom = 4.dp))
        }
    }
}

@Composable
fun AnimalItem(animal: Animal) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(100.dp)) {
        val url = AnimalService.BASE_URL + animal.image
        val painter = rememberImagePainter(data = url)
        Image(painter= painter,
        contentDescription = null,
        modifier = Modifier.size(100.dp),
        contentScale = ContentScale.FillHeight)
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(start = 4.dp)){
            Text(text = animal.name, fontWeight = FontWeight.Bold)
            Text(text = animal.location)
            
        }
    }
}


@Composable
fun LoadingScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()

    }

}
