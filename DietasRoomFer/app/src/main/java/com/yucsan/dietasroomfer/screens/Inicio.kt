package com.yucsan.dietasroomfer.screens

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf

import androidx.compose.runtime.remember

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import modelo.CDModelView
import modelo.ComponenteDieta

import modelo.TipoComponente


// RAMA MASTER PROYECTO DIETAS FERNANDO

@Composable
fun Inicio( viewModel: CDModelView, context: Context ) {

   viewModel.cargarDatos(context)

   val componentesDietas by viewModel.componentes.observeAsState(emptyList())

   val componentesVista = remember { mutableStateListOf(*componentesDietas.toTypedArray()) }

   Column( modifier = Modifier.fillMaxWidth() ) {
      Row(horizontalArrangement = Arrangement.SpaceAround,
         modifier=Modifier.fillMaxWidth()
      ) {
         Button(
            onClick = { viewModel.guardarDatos(context)
            viewModel.guardarDatos(context) } )

         { Text(text = "Guardar archivo") }

         Button(
            onClick = {
               componentesVista.clear()
               viewModel.borrarTodo()
               viewModel.borrarDatos(context) } )
         { Text(text = "Borrar archivo") }
      }

      LazyColumn {
         items(componentesVista) { componente ->
            ComponenteDietaItem(componente)
         }
      }

   }

}


@Composable
fun ComponenteDietaItem(componente: ComponenteDieta) {
// ************* SOLO EN RAMA FERNANDO *************************************************************
   val ingredientes = componente.ingredientes
   // Crear el diseño para un solo elemento de la lista
   Column( modifier = Modifier.fillMaxWidth().padding(5.dp)) {

      Text(text = "> "+componente.nombre)
      Text(text = "Tipo: ${componente.tipo}")

      if(componente.tipo == TipoComponente.SIMPLE)
         Text(text = "Calorías: ${componente.Kcal} kcal")

      Text( color= Color.Gray, fontSize = 15.sp,
         text = "HC: ${componente.grHC}, Lip: ${componente.grLip}, Pro: ${componente.grPro}  ")

      if (ingredientes.isNotEmpty() && componente.tipo == TipoComponente.PROCESADO) {
         ingredientes.forEachIndexed() { i, ing ->
            Text(
               color= Color.Gray, fontSize = 15.sp
               ,text="*:  ${i}: ${ing.cd.nombre} ${ing.cantidad} ca HC: ${ing.cd.grHC}, Lip: ${ing.cd.grLip}, Pro: ${ing.cd.grPro}")
         }

      } else if (ingredientes.isEmpty() && componente.tipo == TipoComponente.PROCESADO) {
         Text(color= Color.Red, fontSize = 15.sp, text="No hay Ingredientes")
      }

   }
}















