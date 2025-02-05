package com.yucsan.dietasroomfer.componentes

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.yucsan.dietasroomfer.modeloRoom.TipoComponente


@Composable
fun MiRadioButton(
   opciones: List<TipoComponente>,  // radio button con Tipo Enum TipoGenero
   seleccion: MutableState<TipoComponente>,
   esSimple: MutableState<Boolean>
) {
   Column(
      modifier = Modifier
         .fillMaxWidth()
         .border(1.dp, color = Color.Cyan)
         .padding(3.dp)
   ) {
      Row(
         horizontalArrangement = Arrangement.spacedBy(16.dp), // Espacio entre botones
         modifier = Modifier
            .fillMaxWidth()
            .padding(top = 3.dp) // Separación del texto inicial) {
      ){
         for (opcion in opciones) {
            Row(
               verticalAlignment = Alignment.CenterVertically,
               modifier = Modifier.clickable {
                     seleccion.value = opcion  // Cambia al hacer clic en el texto o el RadioButton
                  }
            ) {
               RadioButton(
                  selected = seleccion.value == opcion,
                  onClick = {
                     seleccion.value = opcion
                     esSimple.value = !esSimple.value
                  }
               )
               Text(text = opcion.name,
                  modifier = Modifier.padding(start = 4.dp)
               )
            }
         }

      }

   }

}