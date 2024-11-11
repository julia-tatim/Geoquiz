package com.example.quiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quiz.ui.theme.QuizTheme
import androidx.compose.runtime.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

class Menu : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuizTheme {
                TelaMenu()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaMenu(){
    var user by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "GEOQUIZ",
            color = Color(0xFF5566FF),
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            letterSpacing = 1.sp,
            modifier = Modifier
                .padding(bottom = 50.dp)
        )
        Column (modifier = Modifier
            .background(Color.White)
            .padding(20.dp)){
            Text(
                text = "Insira o seu nome:",
                modifier = Modifier
                    .padding(start = 5.dp, bottom = 2.dp)
            )
            TextField(
                value = user,
                onValueChange = { newText -> user = newText },
                placeholder = { Text("User") },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        BorderStroke(1.dp, Color(0xFF5566FF)),
                        shape = RoundedCornerShape(8.dp)
                    ),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White, // Use containerColor para definir a cor de fundo
                    focusedIndicatorColor = Color.Transparent, // Remove o indicador padrão
                    unfocusedIndicatorColor = Color.Transparent, // Remove o indicador padrão
                    cursorColor = Color(0xFF5566FF)
                )
            )
        }
        Button(
            onClick = {},
            modifier = Modifier
                .padding(top = 20.dp, start = 40.dp, end = 40.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF5566FF),
                contentColor = Color.White
            )) {
            Text(
                text = "Começar"
            )
        }
        Image(
            painter = painterResource(id = R.drawable.trophy),
            contentDescription = "Icon",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .padding(top = 50.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TelaMenuPreview() {
    QuizTheme {
        TelaMenu()
    }
}