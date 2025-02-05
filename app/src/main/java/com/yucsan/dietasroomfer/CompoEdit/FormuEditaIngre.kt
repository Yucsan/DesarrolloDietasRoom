package com.yucsan.dietasroomfer.CompoEdit

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yucsan.dietasroomfer.modeloRoom.CDModelView
import com.yucsan.dietasroomfer.modeloRoom.ComponenteDieta
import com.yucsan.dietasroomfer.modeloRoom.TipoComponente


@Composable
fun FormuEditaGrupo(
   muestra: MutableState<Boolean>,
   componente: ComponenteDieta,
   viewModel: CDModelView,
   context: Context,
) {
   var nombre = remember { mutableStateOf(componente.nombre) }

   Box( modifier = Modifier.fillMaxWidth() ) {
      Column() {
         Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
         ){
            Text(fontSize = 20.sp, color= Color.Magenta,
               text = componente.nombre )

            Button( onClick = { muestra.value = false }){
               Text(text="X")
            }
         }
         TextField(
            value = nombre.value,
            onValueChange = { newText ->
               componente.nombre = newText
               nombre.value = newText
            },
            label = { Text("Escribe Nuevo nombre") },
            placeholder = { Text("Introduce nombre aquí") },
            modifier = Modifier.fillMaxWidth()
         )
         Row(horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth().
            padding(5.dp)
            ) {
            Button(
               modifier = Modifier.padding(),
               onClick = {
               /*
                  val ingredientesAct = componente.leerIngredientes()
                  // Hacemos nuevoData Class con valores actualizados
                  val nuevaCompo = ComponenteDieta(
                     nombre = nombre.value,
                     tipo = TipoComponente.PROCESADO
                  )
                  nuevaCompo.actualizarIngredientes(ingredientesAct) // le insertamos el Grupo
                  viewModel.actualizarComponente(
                     componente.id,
                     nuevaCompo
                  ) // grabamos en el viewmodel

                  viewModel.guardarDatos(context) // persistimos
                  nombre.value = ""
                  */
               }
            ) {
               Text(
                  modifier = Modifier.padding(),
                  text = "Guardar Cambios"
               )
            }

            Button(onClick = {
               /*
                  viewModel.eliminarComponente(componente.id)
                  viewModel.guardarDatos(context)
               */
                  muestra.value = false // ejecuto Lambda ****** al borrar el grupo se tiene que cerrar esta ventana

               },
               colors = ButtonDefaults.buttonColors(
               containerColor = Color.Red, // Fondo del botón
               contentColor = Color.Black // Color del texto o ícono
               )
            ) {
               Icon(
                  imageVector = Icons.Default.Delete, // Ícono de la papelera
                  contentDescription = "Eliminar Componente", // Descripción accesible
                  //tint = Color.White // Color del ícono
               )
            }
         }


      }
   }


}