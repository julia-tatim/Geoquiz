package com.example.quiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.animateColorAsState
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
import kotlinx.coroutines.delay

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

class QuizViewModel : ViewModel() {
    val questionText = "De qual país é esta bandeira?"

    private val flagsAndAnswers = listOf(
        Pair(R.drawable.bandeira_brasil, "Brasil"),
        Pair(R.drawable.bandeira_cazaquistao, "Cazaquistão"),
        Pair(R.drawable.bandeira_albania, "Albânia"),
        Pair(R.drawable.bandeira_jamaica, "Jamaica"),
        Pair(R.drawable.bandeira_eslovenia, "Eslovênia"),
        Pair(R.drawable.bandeira_mocambique, "Moçambique"),
        Pair(R.drawable.bandeira_nova_zelandia, "Nova Zelândia"),
        Pair(R.drawable.bandeira_panama, "Panamá"),
        Pair(R.drawable.bandeira_sri_lanka, "Sri Lanka"),
        Pair(R.drawable.bandeira_suecia, "Suécia"),
        Pair(R.drawable.bandeira_equador, "Equador"),
        Pair(R.drawable.bandeira_malasia, "Malásia")
    )

    private var availableFlags = flagsAndAnswers.toMutableList()
    var currentFlag by mutableStateOf(0)
    var correctAnswer by mutableStateOf("")
    var shuffledAnswers by mutableStateOf(listOf<String>())
    var correctCount by mutableStateOf(0)
    var errorCount by mutableStateOf(0)

    init {
        nextQuestion()
    }

    fun checkAnswer(selectedAnswer: String): Boolean {
        val isAnswerCorrect = selectedAnswer == correctAnswer
        if (isAnswerCorrect) {
            correctCount++
        } else {
            errorCount++
        }
        return isAnswerCorrect
    }

    fun nextQuestion() {
        if (availableFlags.isNotEmpty()) {
            val (flag, answer) = availableFlags.random()
            availableFlags.remove(Pair(flag, answer))
            currentFlag = flag
            correctAnswer = answer
            shuffledAnswers = (flagsAndAnswers.map { it.second } - answer).shuffled().take(3) + answer
            shuffledAnswers = shuffledAnswers.shuffled()
        }
    }

//    fun resetGame() {
//        availableFlags = flagsAndAnswers.toMutableList()
//        correctCount = 0
//        errorCount = 0
//        nextQuestion()
//    }
}

@Composable
fun TelaQuiz(viewModel: QuizViewModel) {
    var selectedAnswer by remember { mutableStateOf<String?>(null) }
    var isCorrect by remember { mutableStateOf<Boolean?>(null) }

    LaunchedEffect(viewModel.currentFlag) {
        selectedAnswer = null
        isCorrect = null
    }

    LaunchedEffect(selectedAnswer) {
        if (selectedAnswer != null) {
            delay(500)
            viewModel.nextQuestion()
        }
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
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 18.dp),
            thickness = 1.dp,
            color = Color(0xFF5566FF)
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
            text = viewModel.questionText,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 15.dp)
        )

        viewModel.shuffledAnswers.forEach { answer ->
            val backgroundColor by animateColorAsState(
                targetValue = when {
                    isCorrect == true && selectedAnswer == answer -> Color.Green
                    isCorrect == false && selectedAnswer == answer -> Color.Red
                    else -> Color.White
                }, label = "alterarCorBotão"
            )
            Button(
                onClick = {
                    selectedAnswer = answer
                    isCorrect = viewModel.checkAnswer(answer)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = backgroundColor,
                    contentColor = Color(0xFF5566FF)
                ),
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 10.dp)
                    .fillMaxWidth()
                    .border(1.dp, Color(0xFF5566FF), RoundedCornerShape(32.dp))
                    .clip(RoundedCornerShape(32.dp))
                    .heightIn(min = 56.dp)
            ) {
                Text(answer, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }
        }
    }
}

@Preview
@Composable
fun TelaQuizPreview() {
    val quizViewModel = QuizViewModel()
    TelaQuiz(viewModel = quizViewModel)
}