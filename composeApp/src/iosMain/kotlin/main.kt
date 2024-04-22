import androidx.compose.ui.window.ComposeUIViewController
import com.pascal.recipes_kmp.App
import com.pascal.recipes_kmp.di.appModule
import org.koin.core.context.startKoin
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController {
    initKoin()
    App()
}

fun initKoin(){
    startKoin {
        modules(appModule)
    }
}
