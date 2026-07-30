package com.example.bok.ui.book


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.bok.data.enum_classes.Genre
import com.example.bok.data.enum_classes.Language
import com.example.bok.data.enum_classes.ReadingStatus
import com.example.bok.ui.AppViewModelProvider
import kotlinx.coroutines.launch
import com.example.bok.R
import com.example.bok.ui.BookBottomBar
import com.example.bok.ui.BookTopBar
import kotlinx.coroutines.Dispatchers
import java.util.Calendar


/*
PUT THE NAVIGATION STUFF HERE
 */


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookEntryScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: BookEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
){
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            BookTopBar("BookEntryScreen", true)
        },
        bottomBar = {
            BookBottomBar(navController = navController, destination = "BookEntryScreen")
        }

    ) { innerPadding ->
        BookEntryBody(
            bookUiState = viewModel.bookUiState,
            onBookValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch(Dispatchers.IO){
                    viewModel.saveBook()
                    viewModel.resetUiState()
                }
            },
            modifier = modifier
                .padding(
                    innerPadding
                ),
                //.verticalScroll(rememberScrollState())
                //.fillMaxWidth()
            onNavigateToBookEntryScreen = { navController.navigate("BookEntryScreen") },
            onNavigateToBookDetailsScreen = { navController.navigate("BookDetailsScreen") }
        )
    }
}





@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookEntryBody(
    bookUiState: BookUiState,
    onBookValueChange: (BookInfo) -> Unit,
    onSaveClick: () -> Unit,
    onNavigateToBookDetailsScreen: () -> Unit,
    onNavigateToBookEntryScreen: () -> Unit,
    modifier: Modifier = Modifier
){
    var bookSaved by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier.fillMaxSize()
        // Some padding here
    ){
        item{
            BookInputForm(
                bookInfo = bookUiState.bookInfo,
                onValueChange = onBookValueChange
            )

        }

        item{

            Button(
                onClick = {onSaveClick()
                          bookSaved = true},
                enabled = bookUiState.isBookEntryValid,
                //shape
                modifier = modifier.fillMaxWidth(1f)
            ){
                Text(text = "Lagre bok")
            }}

    }

    if (bookSaved){
        BasicAlertDialog(
            onDismissRequest = {}, //
            modifier = modifier,

            ){
            Surface(modifier = modifier.fillMaxSize()){
                Column() {
                    Text("Boka er lagret! Vil du legge til en ny eller navigere til startskjerm?")
                }
                Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.End) {
                    Button(onClick =  {onNavigateToBookEntryScreen()
                    bookSaved = false} ) {
                        Text("Legg til ny")
                    }

                    Button(onClick = {onNavigateToBookDetailsScreen()
                        bookSaved = false}){
                        Text("Startskjerm")
                    }

                }


            }


        }
    }
}

@Composable
fun BookInputForm(
    bookInfo: BookInfo,
    modifier: Modifier = Modifier,
    onValueChange: (BookInfo) -> Unit = {},
    enabled: Boolean = true
) {

    Column {
        Text(
            text = "Legg til en ny bok",
            fontSize = 35.sp
        )


        // TITTEL
        Column(
            modifier.fillMaxWidth(1f)
        ) {
            Text(
                text = "Tittel: ",
                modifier = modifier.padding(dimensionResource(id = R.dimen.padding_small)),
            )
            OutlinedTextField(
                value = bookInfo.title,
                onValueChange = { onValueChange(bookInfo.copy(title = it)) },
                //label ?, colors, modifier, leadingIcon
                enabled = enabled,
                singleLine = true,
                modifier = modifier.fillMaxWidth(1f).padding(dimensionResource(id = R.dimen.padding_small)),
            )
        }

        // FORFATTERE
        Column(
            modifier.fillMaxWidth(1f)
        ) {
            Text(
                text = "Forfatter(e): ",
                modifier = modifier.padding(dimensionResource(id = R.dimen.padding_small)),
            )
            OutlinedTextField(
                value = bookInfo.authors,
                onValueChange = { onValueChange(bookInfo.copy(authors = it)) },
                //label ?, colors, modifier, leadingIcon
                enabled = enabled,
                singleLine = true,
                modifier = modifier.fillMaxWidth(1f).padding(dimensionResource(id = R.dimen.padding_small)),
            )
        }

        // ISBN
        Column(
            modifier.fillMaxWidth(1f)
        ) {
            Text(
                text = "ISBN: ",
                modifier = modifier.padding(dimensionResource(id = R.dimen.padding_small))
            )
            OutlinedTextField(
                value = bookInfo.isbn,
                onValueChange = { onValueChange(bookInfo.copy(isbn = it)) },
                modifier = modifier.fillMaxWidth(1f).padding(dimensionResource(id = R.dimen.padding_small)),
                //label ?, colors, modifier, leadingIcon
                enabled = enabled,
                singleLine = true
            )
        }

        //Num_pages

        Column(
            modifier.fillMaxWidth(1f)
        ) {
            Text(
                text = "Antall sider: ${bookInfo.numPages} ",
                modifier = modifier.padding(dimensionResource(id = R.dimen.padding_small)),
            )

            Slider(
                value = bookInfo.numPages.toFloat(),
                onValueChange = { onValueChange(bookInfo.copy(numPages = it.toInt()))},
                valueRange = 0f..3000f,
                steps = 3000,
                modifier = modifier.fillMaxWidth(1f).padding(dimensionResource(id = R.dimen.padding_medium))

            )

            Row(
                modifier = modifier.fillMaxWidth(1f),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("0")
                Text("3000")
            }
            /*
            OutlinedTextField(
                value = bookInfo.numPages,
                onValueChange = { onValueChange(bookInfo.copy(numPages = it.filter { i -> i.isDigit() })) },
                //label ?, colors, modifier, leadingIcon,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                enabled = enabled,
                singleLine = true
            )

             */
        }

        // Pub year
        Column(
            modifier.fillMaxWidth(1f)
        ) {
            Text(
                text = "År: ${bookInfo.pubYear}",
                modifier = modifier.padding(dimensionResource(id = R.dimen.padding_small)),
            )

            Slider(
                value = bookInfo.pubYear.toFloat(),
                onValueChange = { onValueChange(bookInfo.copy(pubYear = it.toInt()))},
                valueRange = 1800f..Calendar.getInstance().get(Calendar.YEAR).toFloat(),
                steps = (Calendar.getInstance().get(Calendar.YEAR) - 1800) - 1,
                modifier = modifier.fillMaxWidth(1f).padding(dimensionResource(id = R.dimen.padding_medium))

            )

            Row(
                modifier = modifier.fillMaxWidth(1f),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("1800")
                Text("${Calendar.getInstance().get(Calendar.YEAR)}")
            }

            /*
            OutlinedTextField(
                value = bookInfo.pubYear.toString(),
                onValueChange = { onValueChange(bookInfo.copy(pubYear = it.toInt())) },
                //label ?, colors, modifier, leadingIcon,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                enabled = enabled,
                singleLine = true
            )

             */
        }

        DropdownMenuComponent(
            "Sjanger",
            Genre.entries,
            chosenEnum = "genre",
            bookInfo = bookInfo,
            onValueChange = onValueChange
        )
        DropdownMenuComponent(
            "Språk",
            Language.entries,
            chosenEnum = "language",
            bookInfo = bookInfo,
            onValueChange = onValueChange
        )
        DropdownMenuComponent(
            "Status",
            ReadingStatus.entries,
            chosenEnum = "reading status",
            bookInfo = bookInfo,
            onValueChange = onValueChange
        )

        //Component for doPossess-property
        DoPossessComponent(bookInfo = bookInfo, onValueChange = onValueChange)


    }
}

@Composable
fun DoPossessComponent(
    modifier: Modifier = Modifier,
    bookInfo: BookInfo,
    onValueChange: (BookInfo) -> Unit

){
    //var doPossessedChecked by remember {mutableStateOf(false)}

    Row (
        modifier = modifier.fillMaxWidth(1f),
        //horizontalArrangement = Arrangement.SpaceEvenly
    ){
        Text(
            text = "Eier du boka? ",
            modifier = modifier.weight(1f).
                    padding(dimensionResource(id = R.dimen.padding_small))

        )

        Switch(
            checked = bookInfo.doPossess,
            onCheckedChange = {
                onValueChange(bookInfo.copy(doPossess = it))
            }

        )
    }

}


@Composable
fun <T: Enum <T>> DropdownMenuComponent(type: String,
                                        iterator: Iterable<T>,
                                        chosenEnum: String,
                                        bookInfo: BookInfo,
                                        onValueChange: (BookInfo) -> Unit,
                                        modifier: Modifier = Modifier
){

    var dropdownStatus by remember { mutableStateOf(false) }
    Column (
        modifier = modifier.fillMaxWidth(1f)
    ) {
        Text(
            text = "$type",
            modifier = modifier.
                    padding(dimensionResource(id = R.dimen.padding_small))

        )

        Row(
            modifier = modifier.fillMaxWidth(1f),
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            when (chosenEnum){
                "genre" -> Text(text = "${bookInfo.genre}")
                "language" -> Text(text = "${bookInfo.language}")
                "reading status" -> Text(text = "${bookInfo.readingStatus}")
            }

            // This might be something else, an icon or something
            Button(onClick = {dropdownStatus  = !dropdownStatus}) {
                Text("Alternativer")

            }


            DropdownMenu(
                expanded = dropdownStatus,
                onDismissRequest = {dropdownStatus = false},
            ) {
                iterator.forEach { entry ->
                    DropdownMenuItem(
                        text = {Text(entry.name)},
                        onClick = {
                            dropdownStatus = false
                            when (chosenEnum){
                                "genre" -> onValueChange(bookInfo.copy(genre = (entry as Genre)))
                                "language" -> onValueChange(bookInfo.copy(language = entry as Language))
                                "reading status" -> onValueChange(bookInfo.copy(readingStatus = entry as ReadingStatus))
                            }
                        })
                }

            }

        }



    }
}



@Preview(showBackground = true)
@Composable
private fun BookEntryScreen (){
    BookEntryBody(bookUiState = BookUiState(
        BookInfo(
            title = "11 %",
            authors = "Maren Uthaug",
            numPages = 300,
            language = Language.NORSK,
            doPossess = false,
            readingStatus = ReadingStatus.LEST,
            isbn = "11111111",
            pubYear = 2020,
            genre = Genre.SCIENCE_FICTION

        )
    ), onBookValueChange = {}, onSaveClick = {}, modifier = Modifier, onNavigateToBookDetailsScreen = {}, onNavigateToBookEntryScreen = {}
        )

}






