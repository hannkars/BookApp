package com.example.bok

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.bok.ui.BookApp
import com.example.bok.ui.theme.BokTheme
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        //val db = Room.databaseBuilder(applicationContext, BookDatabase::class.java, "database-name").build()
        //val bookDao = db.bookDao()
        //testingDatabase(bookDao)
        setContent {
            BokTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize()
                    ){
                        //BookApp()
                        //Greeting(name = "Hallo")
                        //BookEntryScreen()
                        //BookDetailsScreen()
                        ScannerTest()

                    }
            }
        }
    }

}


// https://developers.google.com/ml-kit/vision/barcode-scanning/code-scanner#kotlin
// og chatGPT, bla. context og scanner remember, og layouten
@Composable
fun ScannerTest(){

    val context = LocalContext.current

    val options = GmsBarcodeScannerOptions.Builder()
        .setBarcodeFormats(Barcode.FORMAT_EAN_13)
        //.enableAutoZoom()
        .build()

    val scanner = remember {GmsBarcodeScanning.getClient(context, options)}
    var outputText by remember { mutableStateOf("Ingen ISBN scannet") }

    Column(){

        Text(
            text = "Scann ISBN til ønsket bok"
        )
        Text(outputText)

        Button(onClick ={
            scanner.startScan()
                .addOnSuccessListener {
                        barcode ->
                    val ISBN: String? = barcode.rawValue

                    outputText = "ISBN ${ISBN}"

                }
                .addOnCanceledListener {
                    outputText = "Scanning avbrutt"

                }
                .addOnFailureListener { e ->
                    outputText = "Scanning mislyktes"

                }
        } ){

        }

        scanner.startScan()
            .addOnSuccessListener {
                    barcode ->
                val rawValue: String? = barcode.rawValue

            }
            .addOnCanceledListener {

            }
            .addOnFailureListener { e ->

            }



    }





}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BokTheme {
        Greeting("Android")
    }
}