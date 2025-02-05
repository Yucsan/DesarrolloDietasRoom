package com.yucsan.dietasroomfer.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yucsan.dietasroomfer.CompoEdit.FormuEditaGrupo

import com.yucsan.dietasroomfer.modeloRoom.CDModelView

import com.yucsan.dietasroomfer.modeloRoom.ComponenteDieta
import com.yucsan.dietasroomfer.modeloRoom.Ingrediente


@Composable
fun ListadoIngredientes(
   modeloVista: CDModelView,
   componente: ComponenteDieta,
   context: Context,
   muestra: MutableState<Boolean>,
) {
   // Lista de componentes dieta observada desde el ViewModel
   val componentesView by modeloVista.componentes.collectAsState(initial = emptyList())


   val ingredientesLista = remember { mutableStateListOf(*componente.ingredientes.toTypedArray()) }

   val isCheckedList = remember(componentesView) { mutableStateListOf(*Array(componentesView.size) { false }) }

   val muestraCantidad = remember(componentesView) {
      mutableStateListOf(*Array(componentesView.size) { " " }) // array de cantidad string x formulario
   }
   val nuevaCantidad = remember(componentesView) {
      mutableStateListOf(*Array(componentesView.size) { 0.0 }) // array de cantidad double x componenteDieta del dataClass
   }

   Box(modifier = Modifier.fillMaxSize()) {
      Column {
         FormuEditaGrupo(muestra, componente, modeloVista, context)

         Text(text = "Agregar Ingredientes")

         // Lógica para mostrar los ingredientes actuales
         LazyColumn {
            if (ingredientesLista.isNotEmpty()) {
               itemsIndexed(ingredientesLista) { index, ingrediente ->
                  Row {
                     Text(text = "${ingrediente.cd.nombre} (${ingrediente.cantidad})")
                     Spacer(modifier = Modifier.width(8.dp))
                     Button(onClick = {

                        ingredientesLista.removeAt(index)
                        modeloVista.borrarIngrediente(ingrediente.id)
                        // Elimina el ingrediente de la lista directamente

                     }) { Text("Eliminar") }
                  }
               }
            } else {
               item {
                  Text(
                     modifier = Modifier.padding(5.dp), fontSize = 20.sp,
                     text = "No hay Ingredientes"
                  )
               }
            }
         }

         // Mostrar listado de componentes dieta disponibles para agregar como ingredientes
         LazyColumn {
            if (componentesView.isNotEmpty()) {
               itemsIndexed(componentesView) { index, compo ->
                  Row(
                     modifier = Modifier.fillMaxWidth(), // La fila ocupa todo el ancho disponible
                     verticalAlignment = Alignment.CenterVertically // Centra los elementos verticalmente
                  ) {
                     // Checkbox para seleccionar ingredientes
                     Checkbox(
                        checked = isCheckedList[index],
                        onCheckedChange = { isChecked ->
                           isCheckedList[index] = isChecked
                           if (isChecked) {
                              val cantidadIngresada = nuevaCantidad[index].toDouble()
                              if (cantidadIngresada > 0.0) {

                                 val nuevoIngrediente = Ingrediente(
                                    componenteId = componente.id,
                                    cantidad = cantidadIngresada,
                                 )
                                 nuevoIngrediente.cd = compo
                                 ingredientesLista.add(nuevoIngrediente)
                                 modeloVista.insertarIngrediente(nuevoIngrediente)


                              } else {
                                 Toast.makeText(
                                    context,
                                    "Debe ingresar una cantidad válida",
                                    Toast.LENGTH_SHORT
                                 ).show()
                              }
                           }
                        }
                     )

                     // Nombre del componente (con espacio proporcional)
                     Text(
                        text = componentesView[index].nombre,
                        maxLines = 1,
                        modifier = Modifier
                           .weight(1f) // Ocupa el espacio disponible entre el Checkbox y el TextField
                           .padding(start = 8.dp) // Espaciado entre el Checkbox y el texto
                     )

                     // TextField alineado al final de la fila
                     TextField(
                        value = muestraCantidad[index],
                        onValueChange = { newText ->
                           nuevaCantidad[index] = newText.toDoubleOrNull() ?: 0.0
                           muestraCantidad[index] = newText
                        },
                        label = { Text("0") },
                        placeholder = { Text("0") },
                        textStyle = TextStyle(
                           textAlign = TextAlign.End // Alinea el texto del TextField a la derecha
                        ),
                        singleLine = true,
                        modifier = Modifier
                           .padding(horizontal = 25.dp)
                           .width(56.dp) // Ancho fijo para el TextField
                           .align(Alignment.CenterVertically) // Alinea verticalmente el TextField con los demás elementos
                     )
                  }
               }
            }
         }


      }
   }
}



