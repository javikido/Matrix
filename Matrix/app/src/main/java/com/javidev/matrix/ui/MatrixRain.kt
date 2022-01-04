package com.javidev.matrix.ui

import android.util.Log
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.javidev.matrix.model.charters
import com.javidev.matrix.model.charters2
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun MatrixRain(rowRepeat: Int= 20) {
    
    Row {
       repeat(rowRepeat){
           MatrixColum(
               crawlSpeed = (100L..500L).random(),
               starDelay = (100L..2000L).random()
           )
       }
    }

}

@Composable
fun RowScope.MatrixColum(crawlSpeed: Long,starDelay: Long) {
    // como el char cambia con el alpha esto hace que el composable
    // se reconponga para evitarlo uso un remember
    var newChar = remember { mutableListOf(charters2.reversed(),charters, charters2,charters2.reversed()).random()}
    var letrasToDrawer by remember { mutableStateOf(0)}

    Column(Modifier.weight(1f)) {
        repeat(letrasToDrawer){
            MatrixChar(
                char = newChar[it],
                crawlSpeed = crawlSpeed,
                // si la columna llega al 70% reinicio la columna a 0 y empieza a bajar otra vez
                finishListener = { if (it >= newChar.size * 0.6) letrasToDrawer = 0}
            )
        }
    }

    LaunchedEffect(Unit){
        // este delay es para que tarde un tiempo en pintar al inicio de la letra
        delay(starDelay)
        while (true){
            if (letrasToDrawer < newChar.size) letrasToDrawer += 1
            // if para cambiar las letras antes de desaparecer
            if (letrasToDrawer > newChar.size * 0.6){
                Log.i("lista","la lista es: ${newChar.joinToString()}")
                //newChar[Random.nextInt(letrasToDrawer)] = charters2.random()
            }
            delay(crawlSpeed)

        }
    }

}

@Composable
fun MatrixChar(char: String,crawlSpeed: Long,finishListener: ()-> Unit) {
    var texColor by remember{ mutableStateOf(Color(0xffcefbe4))}
    var starFade by remember { mutableStateOf(false)}
    val alpha by animateFloatAsState(
        targetValue = if (starFade) 0f else 1f, // cambio el color
        animationSpec = tween( durationMillis = 5000, easing = LinearEasing),
        // este parametro nos dice cuando la animacion llego al final
        // yo lo uso para que al llegar al 70% reinicio la letra
        finishedListener = {finishListener()}
    )

    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(30.dp)) {
        Text(
            text = char,
            color = texColor.copy(alpha = alpha),
            fontSize = 20.sp,
        )
    }

    // para lanzar la animacion cambio alpha y lo hago solo una vez
    // y lo consigo con Unit tambien cambio el color y le doy un delay para
    // que primero salga el color por defecto que es el blanco
    LaunchedEffect(Unit){
        delay(crawlSpeed)
        texColor = Color(0xff43c748)
        starFade = true
    }
}