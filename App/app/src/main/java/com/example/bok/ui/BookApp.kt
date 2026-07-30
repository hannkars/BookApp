package com.example.bok.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.bok.ui.navigation.BookNavHost




@Composable
fun BookApp(navController: NavHostController = rememberNavController()){
    BookNavHost(navController = navController)

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookTopBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    //scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateUp: () -> Unit = {}
    )
{
    CenterAlignedTopAppBar(
        title = {Text(title)},
        modifier = modifier,
        //scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (canNavigateBack){
                // Remove this, and use IconButton instead
                Button(onClick = navigateUp){
                    Text("Back")

                }
                /*
                IconButton(onClick = navigateUp) {
                    //Icon(imageVector = Filled.ArrowBack),
                    //contentDescription = ...
                }

                 */
            }
        }
    )
}

@Composable
fun BookBottomBar(
    navController: NavController,
    destination: String,
    modifier: Modifier = Modifier
){
    BottomAppBar(
        modifier = modifier.fillMaxWidth(1f)
    ) {
        Row() {
            //Change to IconButton instead
            Button(onClick = {
                if (destination != "BookDetailsScreen"){
                   navController.navigate("BookDetailsScreen") }
            }) {
                Text("Details")
            }

            Button(onClick = {
                if (destination != "BookEntryScreen"){
                    navController.navigate("BookEntryScreen") }
            }) {
                Text("Add")
            }
        }
    }

    }



