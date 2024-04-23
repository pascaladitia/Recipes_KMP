import androidx.compose.ui.window.ComposeUIViewController
import com.pascal.recipes_kmp.App
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController { App() }
