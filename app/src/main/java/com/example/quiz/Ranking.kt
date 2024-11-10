package com.example.quiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quiz.ui.theme.QuizTheme

class Ranking : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuizTheme {
                TelaRanking()
                }
            }
        }
    }

@Composable
fun TelaRanking(){
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
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp, horizontal = 20.dp)
                .border(
                    width = 1.dp, // Largura da borda
                    color = Color(0xFF5566FF), // Cor da borda
                    shape = RoundedCornerShape(8.dp) // Forma arredondada
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.person),
                contentDescription = "Icon",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .padding(start = 8.dp)
            )
            Column {
                Text(
                    text = "User",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF5566FF),
                    fontSize = 16.sp,
                    modifier = Modifier
                        .padding(bottom = 5.dp)
                )
                Row {
                    Text(
                        text = "Pontuação: ",
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        fontSize = 12.sp
                    )
                    Text(
                        text = "00",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF5566FF),
                        fontSize = 12.sp
                    )
                }
            }
            Text(
                text = "1°",
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontSize = 22.sp,
                modifier = Modifier
                    .padding(end = 10.dp)
            )
        }
    }
}

@Preview
@Composable
fun TelaRankingPreview(){
    TelaRanking()
}
