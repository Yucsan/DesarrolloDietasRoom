package com.yucsan.proyectodieta_ver2.Compo_Edit

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MiCheckBox(opciones:List<String>, isCheckedList:SnapshotStateList<Boolean>) {

    Column() {
        Text("Selecciona otra vez esta opciones opciones:")
        for (i in isCheckedList.indices) {
            Row ( verticalAlignment = Alignment.CenterVertically){
                Checkbox(
                    checked = isCheckedList[i],
                    onCheckedChange = {isCheckedList[i]=it }
                )
                Text(text = opciones[i])
            }
        }
    }
}





