package au.com.shiftyjelly.pocketcasts.repositories.endofyear.stories

import androidx.compose.ui.graphics.Color
import au.com.shiftyjelly.pocketcasts.utils.seconds

abstract class Story {
    abstract val identifier: String
    open val storyLength: Long = 5.seconds()
    open val backgroundColor: Color = Color.Black
    val tintColor: Color = Color.White
    open val shareable: Boolean = true
}
