package com.example.quiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.example.quiz.ui.theme.QuizTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuizTheme {
                val quizViewModel: QuizViewModel by viewModels()
                TelaQuiz(quizViewModel)
            }
        }
    }
}

// ViewModel para gerenciar o estado do quiz
class QuizViewModel : ViewModel() {
    val questionText = "De qual país é esta bandeira?"

    // Lista de bandeiras e respostas associadas
    private val flagsAndAnswers = listOf(
        Pair(R.drawable.bandeira_brasil, "Brasil"),
        Pair(R.drawable.bandeira_cazaquistao, "Cazaquistão"),
        Pair(R.drawable.bandeira_albania, "Albânia"),
        Pair(R.drawable.bandeira_jamaica, "Jamaica")
    )

    var currentFlag by mutableStateOf(0)
    var correctAnswer by mutableStateOf("")
    var shuffledAnswers by mutableStateOf(listOf<String>())
    var correctCount by mutableStateOf(0)
    var errorCount by mutableStateOf(0)
    var showErrorDialog by mutableStateOf(false)

    init {
        nextQuestion()
    }

    // Verifica a resposta escolhida
    fun checkAnswer(selectedAnswer: String) {
        if (selectedAnswer == correctAnswer) {
            correctCount++
            nextQuestion()
        } else {
            errorCount++
            showErrorDialog = true
        }
    }

    // Atualiza a pergunta e embaralha as respostas
    fun nextQuestion() {
        if (correctCount + errorCount >= 4) {
            resetGame()
        } else {
            val (flag, answer) = flagsAndAnswers.random()
            currentFlag = flag
            correctAnswer = answer
            shuffledAnswers = (flagsAndAnswers.map { it.second } - answer).shuffled().take(3) + answer
            shuffledAnswers = shuffledAnswers.shuffled()
        }
    }

    // Reseta o jogo após 4 perguntas
    fun resetGame() {
        correctCount = 0
        errorCount = 0
        nextQuestion()
    }
}

@Composable
fun TelaQuiz(viewModel: QuizViewModel) {
    if (viewModel.showErrorDialog) {
        ErrorDialog(onDismiss = { viewModel.showErrorDialog = false; viewModel.resetGame() })
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "GEOQUIZ",
            color = Color(0xFF5566FF),
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            letterSpacing = 1.sp
        )
        Divider(
            thickness = 1.dp,
            color = Color(0xFF5566FF),
            modifier = Modifier.padding(vertical = 18.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text("Erros: ${viewModel.errorCount}", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.Red)
            Text("Acertos: ${viewModel.correctCount}", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.Green)
        }

        Image(
            painter = painterResource(id = viewModel.currentFlag),
            contentDescription = "Bandeira",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 10.dp)
                .clip(RoundedCornerShape(25.dp))
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = viewModel.questionText,  // Pergunta fixa
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 15.dp)
        )

        viewModel.shuffledAnswers.forEach { answer ->
            Button(
                onClick = { viewModel.checkAnswer(answer) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color(0xFF5566FF)
                ),
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 10.dp)
                    .fillMaxWidth()
                    .border(1.dp, Color(0xFF5566FF), RoundedCornerShape(8.dp))
            ) {
                Text(answer, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }
        }
    }
}

@Composable
fun ErrorDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("Reiniciar")
            }
        },
        text = {
            Text("Você errou!")
        }
    )
}

@Preview
@Composable
fun TelaQuizPreview() {
    val quizViewModel = QuizViewModel()
    TelaQuiz(viewModel = quizViewModel)
}
