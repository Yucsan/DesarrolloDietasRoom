package com.yucsan.proyectodieta_ver2.componentes

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import modelo.CDModelView
import modelo.ComponenteDieta
import modelo.Ingrediente

@Composable
fun AlertaIngredientes(
   componente: ComponenteDieta,
   modelView: CDModelView,
   context: Context,
   onSave: (ComponenteDieta) -> Unit,
   onCancel: () -> Unit,
   mostrarEditarDieta: Boolean,  // Estado para mostrar/ocultar el AlertDialog
   onDismiss: () -> Unit          // Función para ocultar el dialogo
) {
   var nombre by remember { mutableStateOf(componente.nombre) }
   var tipo by remember { mutableStateOf(componente.tipo) }
   var grHC_ini by remember { mutableStateOf(componente.grHC_ini.toString()) }
   var grLip_ini by remember { mutableStateOf(componente.grLip_ini.toString()) }
   var grPro_ini by remember { mutableStateOf(componente.grPro_ini.toString()) }

   // Gestión de ingredientes
   val ingredientesLista = remember { mutableStateListOf(*componente.ingredientes.toTypedArray()) }
   var nuevoIngredienteCantidad by remember { mutableStateOf("100") }
   val componentesView by modelView.componentes.observeAsState(emptyList())

   // Mostrar el AlertDialog si 'mostrarEditarDieta' es verdadero
   if (mostrarEditarDieta) {
      AlertDialog(
         onDismissRequest = { onDismiss() },
         title = { Text("Editar Ingredientes para ${componente.nombre}") },
         text = {
            Column(
               modifier = Modifier.fillMaxSize().padding(10.dp),
               //verticalArrangement = Arrangement.SpaceBetween
            ) {
               // Nombre del componente
               TextField(
                  value = nombre,
                  onValueChange = { nombre = it },
                  label = { Text("Nombre") },
                  modifier = Modifier.fillMaxWidth()
               )

               // Sección de ingredientes
               Text(text = "Ingredientes del Componente")
               LazyColumn {
                  if(ingredientesLista.isNotEmpty()){

                     itemsIndexed(ingredientesLista) { index, ingrediente ->
                        Row {
                           Text(text = "${ingrediente.cd.nombre} (${ingrediente.cantidad})")
                           Spacer(modifier = Modifier.width(8.dp))
                           Button(onClick = {
                              val nuevaLista = ingredientesLista.toMutableList()
                              nuevaLista.removeAt(index)
                              ingredientesLista.clear()
                              ingredientesLista.addAll(nuevaLista)

                           }) {
                              Text("Eliminar")
                           }
                        }
                     }
                  }else{
                     item{
                        Text("No hay Ingredientes")
                     }
                  }
               }

               OutlinedTextField(
                  value = nuevoIngredienteCantidad,
                  onValueChange = { nuevoIngredienteCantidad = it },
                  label = { Text("Cantidad") },
                  keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
               )

               //Editar para agregar componentes
               Text(text = "Agregar Ingrediente")
               LazyColumn {
                  if (componentesView.isNotEmpty()) {

                     itemsIndexed(componentesView) { index, compo ->
                        Row {
                           Text(text = "${compo.nombre}")
                           Spacer(modifier = Modifier.width(2.dp))
                           Button(onClick = {
                              // Añadir ingrediente con la cantidad
                              val nuevoIngrediente = Ingrediente(compo, 100.0)

                              if ( componente.addIngrediente(nuevoIngrediente) ) {
                                 ingredientesLista.add(nuevoIngrediente)

                                 modelView.guardarDatos(context)
                              }
                           }) {
                              Text("Agregar")
                           }
                        }
                     }
                  }
               }
               // Botones de acción
               Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween
               ) {
                  Button(onClick = { onCancel() }) {
                     Text("Cancelar")
                  }
                  Button(onClick = {
                     val nuevoComponente = componente.copy(
                        nombre = nombre,
                        tipo = tipo,
                        grHC_ini = grHC_ini.toDoubleOrNull() ?: 0.0,
                        grLip_ini = grLip_ini.toDoubleOrNull() ?: 0.0,
                        grPro_ini = grPro_ini.toDoubleOrNull() ?: 0.0,

                     )
                     nuevoComponente.ingredientes.addAll(ingredientesLista)

                     modelView.guardarDatos(context)  // Actualizar en el ViewModel
                     onSave(nuevoComponente)  // Propagar cambios hacia la ventana superior

                  }) {
                     Text("Guardar")
                  }
               }
            }
         },
         confirmButton = {},
         dismissButton = {}
      )
   }
}



