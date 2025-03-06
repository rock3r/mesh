package des.c5inco.mesh.ijplugin

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAwareAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.util.ui.JBUI
import des.c5inco.mesh.App
import org.jetbrains.jewel.bridge.JewelComposePanel
import org.jetbrains.jewel.foundation.ExperimentalJewelApi
import org.jetbrains.jewel.foundation.enableNewSwingCompositing
import java.awt.Component
import java.awt.Dimension
import javax.swing.Action
import javax.swing.JComponent

class MeshWindowAction : DumbAwareAction() {
    override fun actionPerformed(event: AnActionEvent) {
        MeshDialogWrapper(event.project).show()
    }
}

internal class MeshDialogWrapper(
    project: Project?,
    parent: Component? = null,
    private val minimumSize: Dimension = DEFAULT_MIN_SIZE,
    private val preferredSize: Dimension = DEFAULT_PREFERRED_SIZE,
) : DialogWrapper(project, parent, true, IdeModalityType.IDE) {

    init {
        title = "Mesh"
        init()
    }

    override fun createActions(): Array<Action> = arrayOf()

    // Don't include the default border; our banners need to span the entire width
    override fun createContentPaneBorder() = null

    // Don't include the bottom panel; we'll make buttons ourselves
    override fun createSouthPanel(): JComponent? = null

    @OptIn(ExperimentalJewelApi::class)
    override fun createCenterPanel(): JComponent {
        enableNewSwingCompositing()

        val component = JewelComposePanel {
            Box(Modifier.fillMaxSize()) {
                App()
            }
        }
        component.preferredSize = preferredSize
        component.minimumSize = minimumSize

        return component
    }
}

private val DEFAULT_PREFERRED_SIZE: Dimension = JBUI.size(900, 650)
private val DEFAULT_MIN_SIZE: Dimension = JBUI.size(600, 350)
