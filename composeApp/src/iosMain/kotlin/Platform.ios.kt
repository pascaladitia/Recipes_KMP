import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.recipes.db.RecipesDatabase
import org.koin.core.annotation.Single
import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

actual class DriverFactory actual constructor(){
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(RecipesDatabase.Schema,"RecipesDatabase.db")
    }
}