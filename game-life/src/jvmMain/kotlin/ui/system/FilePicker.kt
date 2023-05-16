package ui.system

import androidx.compose.runtime.*
import com.darkrockstudios.libraries.mpfilepicker.FilePicker

class FilePicker {
    private var showFilePicker = mutableStateOf(false)

    @Composable
    operator fun invoke(onSelect: (String) -> Unit) {
        val fileTypes = listOf("json")
        FilePicker(showFilePicker.value, fileExtensions = fileTypes) { path ->
            showFilePicker.value = false
            if (path != null) {
                onSelect(path.path)
            }
        }
    }

    fun open() {
        showFilePicker.value = true
    }
}