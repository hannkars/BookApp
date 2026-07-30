package com.example.bok.ui.book

import android.app.AlertDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog

import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.bok.R
import com.example.bok.data.database.Book
import com.example.bok.data.enum_classes.Genre
import com.example.bok.data.enum_classes.Language
import com.example.bok.data.enum_classes.ReadingStatus
import com.example.bok.ui.AppViewModelProvider
import com.example.bok.ui.BookBottomBar
import com.example.bok.ui.BookTopBar
import com.example.bok.ui.book.BookDetailsComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun BookDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: BookDetailsViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navController: NavController
){
    val bookDetailsUiState by viewModel.bookDetailsUiState.collectAsState()
    Scaffold(
        topBar = { BookTopBar("Bøker", false) },
        bottomBar = { BookBottomBar(navController = navController, destination = "BookDetailsScreen") },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("BookEntryScreen") }) {
                Text ("+")
            }
        }

        //topBar and stuff
    ) { innerPadding ->
       BookDetailsBody(
           bookList = bookDetailsUiState.bookList,
           modifier = modifier.padding(innerPadding),
           viewModel = viewModel

           //contentPadding = innerPadding
       )

    }
}

@Composable
fun BookDetailsBody(
    bookList: List<Book>,
    modifier: Modifier = Modifier,
    viewModel: BookDetailsViewModel
    //contentPadding: PaddingValues = PaddingValues(0.dp)
    ){

    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        if (bookList.isEmpty()){
            item {
                Text(
                    text = "Her var det tomt"
                )

            }
        }
        else {
            for (book in bookList){
                item{
                    BookDetailsComponent(book,
                        onDelete = {
                           viewModel.deleteBook(book)
                        })
                }


            }

        }

    }

}

@Composable
fun BookDetailsComponent(
    book: Book,
    modifier: Modifier = Modifier,
    onDelete: () -> Unit,
){
    var expandedCard by remember{ mutableStateOf(false) }
    var deletionOfBookConfirmation by remember{mutableStateOf(false)}
    Card(
        onClick = {expandedCard = !expandedCard},
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = modifier.padding(dimensionResource(R.dimen.padding_medium)),
        ) {

            BookDetailsComponentProperty("Tittel", book.title)
            BookDetailsComponentProperty("Forfatter(e)", book.authors)
            BookDetailsComponentProperty("År", book.pubYear.toString())
            BookDetailsComponentProperty("Antall sider", book.numberOfPages.toString())


            if (expandedCard){

                BookDetailsComponentProperty("Sjanger", book.genre.name.lowercase())
                BookDetailsComponentProperty("Status", book.readingStatus.name.lowercase())

                Row(
                    //horizontalArrangement = Arrangement.SpaceEvenly
                ){
                    Text(
                        text = "I bokhylla?: "
                    )
                    if (book.doPossess){
                        Text(
                        text = "Ja")
                    }else{Text(text = "Nei")}}

                BookDetailsComponentProperty("Språk", book.language.name.lowercase() )


                Button(
                    onClick = {deletionOfBookConfirmation = true}
                ) {
                    Text(
                        text = "Slett bok"
                    )

                }
                if (deletionOfBookConfirmation){
                    DeleteConfirmationDialog(
                        onDeleteConfirm = {
                            deletionOfBookConfirmation = false
                            onDelete()

                        },
                        onDeleteCancel = {deletionOfBookConfirmation = false}

                    )
                }
            }



        }

        }
    Spacer(modifier = modifier.padding(dimensionResource(R.dimen.padding_small)))


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
){
    BasicAlertDialog(
        onDismissRequest = {}, //
        modifier = modifier,

    ){
        Surface(){
            Column() {
                Text("Er du sikker på at du vil slette boka?")
            }
            Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.End) {
                Button(onClick = onDeleteConfirm) {
                    Text("Bekreft")
                }

                Button(onClick = onDeleteCancel){
                    Text("Avbryt")
                }

            }


        }


    }
}


@Composable
fun BookDetailsComponentProperty(
    propertyName: String,
    propertyValue: String,
    modifier: Modifier = Modifier
){
    Row(
        //horizontalArrangement = Arrangement.SpaceBetween
        modifier = modifier.padding(dimensionResource(R.dimen.padding_small))
    ){
        Text(
            text = "${propertyName}: ",
        )
        Text(
            text = "${propertyValue}",

        )
    }

}


@Preview
@Composable
private fun BookDetailsComponentPreview(){
    BookDetailsComponent(book = Book(
        title = "11 %",
        authors = "Maren Uthaug",
        numberOfPages = 300,
        language = Language.NORSK,
        doPossess = false,
        readingStatus = ReadingStatus.LEST,
        isbn = "11111111",
        pubYear = 2020,
        genre = Genre.SCIENCE_FICTION

    ), modifier = Modifier, onDelete = {})
}