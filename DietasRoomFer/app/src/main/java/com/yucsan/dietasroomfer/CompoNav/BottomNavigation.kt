package com.yucsan.proyectodieta_ver2.CompoNav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.yucsan.proyectodieta_ver2.screens.Ruta

@Composable
fun NavigationBar(
    navController: NavHostController
) {

    //val currentRoute by navController.currentBackStackEntryAsState().map { it?.destination?.route } // Extraemos la ruta actual como un String */
    val currentRoute = navController.currentBackStackEntryAsState()?.value?.destination?.route

    NavigationBar(
        windowInsets = NavigationBarDefaults.windowInsets
    ) {
        NavigationBarItem(
            selected = currentRoute == Ruta.Pantalla1.ruta, // Comparamos con la ruta de Pantalla1
            onClick = {
                if (currentRoute != Ruta.Pantalla1.ruta) { // Evitamos navegar si ya estamos en la pantalla
                    navController.navigate(Ruta.Pantalla1.ruta)
                    /* { popUpTo(navController.graph.startDestinationId)
                        { saveState = true }
                        launchSingleTop = true
                        restoreState = true } */
                }
            },
            icon = { Icon(Icons.Filled.Home, contentDescription = "Inicio") },
            label = { Text(text = "Inicio") }
        )

        NavigationBarItem(
            selected = currentRoute == Ruta.Pantalla2.ruta, // Comparamos con la ruta de Pantalla2
            onClick = {
                if (currentRoute != Ruta.Pantalla2.ruta) {
                    navController.navigate(Ruta.Pantalla2.ruta)
                }
            },
            icon = { Icon(Icons.Filled.Menu, contentDescription = "Pantalla2") },
            label = { Text(text = "Pantalla 2") }
        )
        NavigationBarItem(
            selected = currentRoute == Ruta.Pantalla3.ruta, // Comparamos con la ruta de Pantalla2
            onClick = {
                if (currentRoute != Ruta.Pantalla3.ruta) {
                    navController.navigate(Ruta.Pantalla3.ruta)
                }
            },
            icon = { Icon(Icons.Filled.DateRange, contentDescription = "Pantalla3") },
            label = { Text(text = "Pantalla 3") }
        )


    }
}


