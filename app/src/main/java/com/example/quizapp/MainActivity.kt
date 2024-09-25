package com.example.quizapp

import android.os.Bundle
import android.view.Surface
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quizapp.ui.theme.QuizAppTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuizAppTheme {
                Surface (
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Green
                ){
                    Quiz()
                }
            }
        }
    }
}

@Composable
fun Quiz() {
    val facts = listOf(
        "Which animal was the first to be cloned?",
        "How long can giraffe tongues get in inches?",
        "Which country chose the unicorn as their national animal?",
        "True or false: fish can cough.",
        "How many states can you see from the Willis Tower in Chicago?",
        "Which country is wider than the moon?",
        "Will a person die first from a lack of sleep or starvation?",
        "What is a baby kangaroo called?",
        "What is a group of cats called?",
        "Where is the smallest street?",
        "How many continents are there?",
        "Where can you find the most crooked street (which city)?"
    )

    val answers = listOf(
        "Sheep",
        "20 inches",
        "Scotland",
        "True",
        "4",
        "Australia",
        "Lack of sleep",
        "Joey",
        "Clowder",
        "Scotland",
        "7",
        "San Francisco"
    )

    var input by remember { mutableStateOf("") }
    var currentIndex by remember { mutableStateOf(0) }
    var isQuizCompleted by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (!isQuizCompleted) {
                    Text(text = facts[currentIndex], style = MaterialTheme.typography.labelLarge)

                    TextField(
                        value = input,
                        onValueChange = { input = it },
                        label = { Text("Your Answer") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Button(onClick = {

                        if (input.equals(answers[currentIndex], ignoreCase = true)) {

                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Correct!")
                            }
                            if (currentIndex < facts.lastIndex) {
                                currentIndex++
                                input = ""
                            } else {
                                isQuizCompleted = true
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("Quiz completed! Would you like to restart?")
                                }
                            }
                        } else {

                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Incorrect! Try again.")
                            }
                        }
                    }) {
                        Text("Submit")
                    }
                } else {
                    Text(text = "Congratulations! You've completed the quiz.", style = MaterialTheme.typography.labelLarge)
                    Button(onClick = {

                        currentIndex = 0
                        isQuizCompleted = false
                        input = ""
                    }) {
                        Text("Restart Quiz")
                    }
                }
            }
        }
    )
}
