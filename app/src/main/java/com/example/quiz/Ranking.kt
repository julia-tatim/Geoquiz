package com.example.quiz

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import com.example.quiz.ui.theme.QuizTheme
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Ranking : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuizTheme {
                val nomeUsuario = intent.getStringExtra("USER_NAME") ?: "Desconhecido"
                val pontuacao = intent.getIntExtra("SCORE", 0)


                val rankings = listOf(
                    Pair("Jogador 1", 15),
                    Pair("Jogador 2", 10),
                    Pair("Jogador 3", 8),
                    Pair(nomeUsuario, pontuacao)
                ).sortedByDescending { it.second }

                TelaRanking(rankings, nomeUsuario, pontuacao)
            }
        }
    }
}


@Composable
fun TelaRanking(rankings: List<Pair<String, Int>>, nomeUsuario: String, pontuacao: Int) {
    val context = LocalContext.current

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
            thickness = 1.dp,
            color = Color(0xFF5566FF),
            modifier = Modifier.padding(vertical = 18.dp)
        )
        Text(
            text = "Ranking ",
            fontWeight = FontWeight.Bold,
            color = Color(0xFF5566FF),
            fontSize = 22.sp
        )

        rankings.forEachIndexed { index, ranking ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
                    .border(
                        width = 1.dp, color = Color(0xFF5566FF),
                        shape = RoundedCornerShape(8.dp)
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = ranking.first,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF5566FF),
                    fontSize = 16.sp,
                    modifier = Modifier.padding(start = 10.dp)
                )
                Text(
                    text = "Pontuação: ${ranking.second}",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF5566FF),
                    fontSize = 14.sp,
                    modifier = Modifier.padding(end = 10.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))


        Button(
            onClick = {

                val intent = Intent(context, NomeActivity::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Voltar ao Início")
        }
    }
}