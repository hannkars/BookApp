package com.example.bok.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.bok.ui.book.BookDetailsScreen
import com.example.bok.ui.book.BookEntryScreen

@Composable
fun BookNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
){

    NavHost(
        navController = navController,
        startDestination = "BookDetailsScreen",
        modifier = modifier)
    {

        composable(route =  "BookDetailsScreen"){
            //MAKE THIS
            BookDetailsScreen(navController = navController)
        }

        composable (route = "BookEntryScreen" ){
            BookEntryScreen(navController = navController)
        }


    }



}