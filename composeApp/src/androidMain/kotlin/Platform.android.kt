import android.content.Context
import android.os.Build
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.pascal.recipes_kmp.AndroidApp
import com.recipes.db.RecipesDatabase
import org.koin.core.annotation.Single

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

@Single
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class DriverFactory actual constructor() {
    private var context: Context = AndroidApp.INSTANCE.applicationContext
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(RecipesDatabase.Schema, context, "RecipesDatabase.db")
    }
}