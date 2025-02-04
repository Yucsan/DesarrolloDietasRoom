package com.yucsan.dietasroomfer.Componentes2

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import modelo.ComponenteDieta
import modelo.TipoComponente

@Composable
fun MiCarta(cd: ComponenteDieta, inx: Int, onClickItem: (ComponenteDieta) -> Unit) {

   val ingredientes = cd.ingredientes

   OutlinedCard(
      colors = CardDefaults.cardColors(
         containerColor = MaterialTheme.colorScheme.surface,
      ),
      border = BorderStroke(1.dp, Color.White),
      modifier = Modifier
         .fillMaxWidth()
         .padding(3.dp)
         .clickable { onClickItem(cd) })
   {
      Column(
         modifier = Modifier
            .padding(horizontal = 4.dp)
            .padding(2.dp)
      ) {
         Row() {
            Text(fontSize = 20.sp,
               text = "${inx} ${cd.nombre}",
               modifier = Modifier.padding(2.dp),
               textAlign = TextAlign.Center,
            )

            Text(
               text = ": ${cd.Kcal} kcal",
               modifier = Modifier.padding(2.dp),
               textAlign = TextAlign.Center,
            )
            Log.i("DATA YUCSAN", "kcal: ${cd.Kcal}  nombre: ${cd.nombre} grHC: ${cd.grHC}, grHC: ${cd.grLip}")
            Log.i("DATA YUCSAN", "grHC_ini: ${cd.grHC_ini}   CANTIDAD: ${cd.cantidadTotal} *** ---- //// **** ")

         }
         Text(color=Color.Cyan,
            text = "${cd.tipo}",
            fontSize = 16.sp,
            modifier = Modifier.padding(2.dp),
            textAlign = TextAlign.Center,
         )

         if (ingredientes.isNotEmpty() ) {
            ingredientes.forEachIndexed { i, ing ->
               Text("ingrediente ${i}: ${ing.cd.nombre} ${ing.cantidad}gr")
            }
         }else if(cd.tipo == TipoComponente.PROCESADO){
            Text(color=Color.Magenta,
               fontSize = 15.sp,
               text="No hay Ingredientes..")
         }

      }
   }

}