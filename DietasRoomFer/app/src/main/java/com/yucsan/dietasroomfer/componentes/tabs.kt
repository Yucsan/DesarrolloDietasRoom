package com.yucsan.dietasroomfer.componentes

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.yucsan.dietasroomfer.ui.theme.DietasRoomFerTheme
import com.yucsan.proyectodieta_ver2.modelo.TabData
import com.yucsan.proyectodieta_ver2.screens.Ruta

// REVIZAR SI ES QUE NO LA BORRAMOS


@Composable
fun MisTabsMain(navController: NavController, selectedTab: Int) {
    DietasRoomFerTheme {

        val tabs = listOf(
            TabData("Home", Icons.Filled.Home),
            TabData("Formulario", Icons.Filled.Edit),
            TabData("Listado", Icons.Filled.Info)
        )

        Column(
            modifier = Modifier.fillMaxWidth().border(2.dp,color= Color.Cyan)
        ) {
            TabRow(selectedTabIndex = selectedTab) {
                tabs.forEachIndexed { index, tab ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = {
                            when (index){
                                0-> navController.navigate(Ruta.Pantalla1.ruta)
                                1-> navController.navigate(Ruta.Pantalla2.ruta)
                                2-> navController.navigate(Ruta.Pantalla3.ruta)
                            }

                        },
                        text = { Text(text = tab.title) },
                        icon = {
                            Icon(
                                imageVector = tab.icon,
                                contentDescription = null // Provide a content description if needed
                            )
                        }
                    )
                }
            }
        }
    }
}




