package com.example.examenjpc_1.screens


import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import com.yucsan.proyectodieta_ver2.Compo_Edit.AlertaComponente
import com.yucsan.proyectodieta_ver2.Componentes2.MiCarta
import modelo.CDModelView
import modelo.ComponenteDieta
import modelo.TipoComponente

@Composable
fun ListadoDetalle(
   navController: NavController,
   opcionesRadio: List<TipoComponente>,
   modeloVista: CDModelView,
   context: Context
) {

   var refComponente by remember { mutableStateOf<ComponenteDieta?>(null) }
   val componentesView by modeloVista.componentes.observeAsState(emptyList())
   var muestraIngredientes = remember { mutableStateOf(false) }
   var compoSeleccionado by remember { mutableStateOf(ComponenteDieta(nombre = "Predeterminado")) }

   Column {
      if (!muestraIngredientes.value) {
         // Mostrar listado de componentes dieta
         Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn {
               itemsIndexed(componentesView) { i, componenteDieta ->
                  MiCarta(componenteDieta, i) {
                     if (componenteDieta.tipo == TipoComponente.PROCESADO) {
                        // Seleccionar componente para agregar ingredientes
                        compoSeleccionado = componenteDieta
                        muestraIngredientes.value = true
                     } else {
                        // Mostrar alerta para editar el componente
                        refComponente = it
                     }
                  }
               }
            }
         }
      } else {
         // Mostrar vista para agregar ingredientes al componente seleccionado
         Box(modifier = Modifier.fillMaxSize()) {
            ListadoIngredientes(modeloVista, compoSeleccionado, context, muestraIngredientes)
         }
      }

      // Mostrar alerta si hay un componente seleccionado para edición
      refComponente?.let { componente ->
         AlertaComponente(
            componenteDieta = componente,
            opcionesRadio = opcionesRadio,
            viewModel = modeloVista,
            onGuardar = { nuevoComponente ->
               modeloVista.actualizarComponente(componente.id, nuevoComponente)
               modeloVista.guardarDatos(context)
            },
            onBorrar = { modeloVista.eliminarComponente(componente.id)
                         modeloVista.guardarDatos(context) },
            onDismiss = { refComponente = null },
            context = context
         )
      }
   }
}



