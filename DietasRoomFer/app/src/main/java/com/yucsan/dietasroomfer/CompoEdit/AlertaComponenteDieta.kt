package com.yucsan.dietasroomfer.CompoEdit


import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.yucsan.dietasroomfer.componentes.AlertaIngredientes
import com.yucsan.dietasroomfer.componentes.MiRadioButton
import modelo.CDModelView
import modelo.ComponenteDieta
import modelo.TipoComponente

@Composable
fun AlertaComponente(
   componenteDieta: ComponenteDieta,
   opcionesRadio: List<TipoComponente>,
   viewModel: CDModelView,
   context: Context,
   onGuardar: (ComponenteDieta) -> Unit,
   onBorrar: () -> Unit,
   onDismiss: () -> Unit,
) {

   //usamos los valores de alimento asignamos sus valores a nuestras variables
   var nombre = remember { mutableStateOf(componenteDieta.nombre) }
   var grProt = remember { mutableStateOf(componenteDieta.grPro.toString()) }
   var grHC = remember { mutableStateOf(componenteDieta.grHC.toString()) }
   var grLip = remember { mutableStateOf(componenteDieta.grLip.toString()) }
   var seleccion = remember { mutableStateOf<TipoComponente>(componenteDieta.tipo) } //Seleccionamos el q tiene

   var esSimple = remember { mutableStateOf(seleccion.value == TipoComponente.SIMPLE) }
   var mostrarEditarDieta by remember { mutableStateOf(false) }

   val ingredientesLista = remember { mutableStateListOf(*componenteDieta.ingredientes.toTypedArray()) }

   AlertDialog(
      onDismissRequest = { onDismiss() },
      title = { Text(text = "Editar ${componenteDieta.nombre}") },
      text = {

         Column {
            MiRadioButton(opcionesRadio, seleccion, esSimple)
            OutlinedTextField(
               value = nombre.value,
               onValueChange = { nombre.value = it },
               label = { Text(text = "Nombre") }
            )

            if (componenteDieta.tipo == TipoComponente.SIMPLE) {
               OutlinedTextField(
                  value = grProt.value,
                  onValueChange = { grProt.value = it },
                  label = { Text(text = "grProt") },
                  keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
               )
               OutlinedTextField(
                  value = grHC.value,
                  onValueChange = { grHC.value = it },
                  label = { Text(text = "grHC") },
                  keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
               )
               OutlinedTextField(
                  value = grLip.value,
                  onValueChange = { grLip.value = it },
                  label = { Text(text = "grLip") },
                  keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
               )
            }
      //****************************** BOTON AGREGAR ************************************************
            if (componenteDieta.tipo == TipoComponente.PROCESADO) {
               Button( onClick = { mostrarEditarDieta = true } ) {
                  Text("Agregar Ingredientes")
               }

               AlertaIngredientes(
                  componente = componenteDieta,
                  modelView = viewModel,
                  onSave = { nuevoComponente ->
                     // Aquí se guardan los cambios en el componente actualizado
                     viewModel.actualizarComponente( componenteDieta.id, nuevoComponente )
                     viewModel.guardarDatos(context)
                     mostrarEditarDieta = false
                  },
                  onCancel = {
                     mostrarEditarDieta = false  // Cierra el diálogo
                  },
                  mostrarEditarDieta = mostrarEditarDieta,
                  context = context,
                  onDismiss = {
                     mostrarEditarDieta = false
                  }
               )

            }
         }
      },
      confirmButton = {
         Row {
            Button(
               onClick = {
                  val componenteDietaActualizado = componenteDieta.copy(
                     tipo = seleccion.value,
                     nombre = nombre.value,
                     grHC_ini = grHC.value.toDoubleOrNull() ?: componenteDieta.grHC,
                     grLip_ini = grLip.value.toDoubleOrNull() ?: componenteDieta.grLip,
                     grPro_ini = grProt.value.toDoubleOrNull() ?: componenteDieta.grPro,
                  )

                  onGuardar(componenteDietaActualizado)
                  onDismiss()
               }
            ) {
               Text(text = "Guardar *******")
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
               onClick = {
                  onBorrar()
                  onDismiss()
               }
            ) {
               Text(text = "Borrar")
            }
         }
      },
      dismissButton = {
         Button(onClick = onDismiss) {
            Text(text = "Cancelar")
         }
      }
   )

}