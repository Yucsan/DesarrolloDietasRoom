package com.yucsan.proyectodieta_ver2.Componentes2

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import modelo.CDModelView
import modelo.ComponenteDieta

@Composable
fun CartaIngredientes(
   viewModel: CDModelView,
   componente: ComponenteDieta) {


   OutlinedCard(
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
      border = BorderStroke(0.4.dp, Color.Cyan),
      modifier = Modifier
         .fillMaxWidth()
         .padding(3.dp)
   ) {
      Column(
         modifier = Modifier
            .padding(horizontal = 4.dp)
            .padding(2.dp)
      ) {
         // Información básica del componente dieta
         Row {
            Text(
               text = "Nombre CD: ${componente.nombre}",
               fontSize = 22.sp,
               modifier = Modifier.padding(2.dp),
               textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
               text = "T: ${componente.tipo}",
               fontSize = 15.sp,
               modifier = Modifier.padding(2.dp),
               textAlign = TextAlign.Center
            )
         }




         // Mostrar lista de ingredientes
         if (componente.ingredientes.isNotEmpty()) {
            componente.ingredientes.forEachIndexed { i, ing ->
               Text("Ingrediente ${i + 1}: ${ing.cd.nombre} (${ing.cantidad}gr)")
            }
         } else {
            Text(color=Color.Magenta,
               fontSize = 10.sp,
               text="No hay Ingredientes..")
         }
      }
   }
}
