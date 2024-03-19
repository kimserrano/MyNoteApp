package serrano.kimberly.mynoteapp.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteScreen(
    state: NoteState,
    navController: NavController,
    onEvent: (NotesEvent) -> Unit
) {
    // para checar el msj
    var showError by remember { mutableStateOf(false) }

    // validamos campos q:
    fun areFieldsValid(): Boolean {
        val isValid = state.title.value.isNotBlank() && state.description.value.isNotBlank()
        // si no pasa la validacion
        showError = !isValid
        return isValid
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    // chacamos la validacion
                    if (areFieldsValid()) {
                        onEvent(NotesEvent.SaveNote(
                            title = state.title.value,
                            description = state.description.value
                        ))
                        navController.popBackStack()
                    }
                },
            ) {
                Icon(
                    imageVector = Icons.Rounded.Check,
                    contentDescription = "Save Note"
                )
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                value = state.title.value,
                onValueChange = {
                    state.title.value = it
                    // se quita el error para modificar el campo
                    showError = false
                },
                textStyle = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 17.sp
                ),
                placeholder = {
                    Text(text = "Title")
                }

            )

            // error si el titulo es vacio
            if (showError && state.title.value.isBlank()) {
                Text(
                    text = "Title cannot be empty",
                    style = TextStyle(color = Color.Red),
                    modifier = Modifier.padding(start = 16.dp)
                )
            }

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                value = state.description.value,
                onValueChange = {
                    state.description.value = it
                    showError = false
                },
                placeholder = {
                    Text(text = "Description")
                }

            )

            if (showError && state.description.value.isBlank()) {
                Text(
                    text = "Description cannot be empty",
                    style = TextStyle(color = Color.Red),
                    modifier = Modifier.padding(start = 16.dp)
                )
            }

        }

    }

}
