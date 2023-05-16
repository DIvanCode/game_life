package ui.system

import androidx.compose.runtime.*
import com.darkrockstudios.libraries.mpfilepicker.DirectoryPicker

class DirectoryPicker {
    private var showDirectoryPicker = mutableStateOf(false)

    @Composable
    operator fun invoke(onSelect: (String) -> Unit) {
        DirectoryPicker(showDirectoryPicker.value) { path ->
            showDirectoryPicker.value = false
            if (path != null) {
                onSelect(path)
            }
        }
    }

    fun open() {
        showDirectoryPicker.value = true
    }

    companion object {
        const val DEFAULT_DIRECTORY_PATH = "/"
    }
}