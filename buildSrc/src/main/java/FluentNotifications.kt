import studio.forface.easygradle.dsl.android.Version
import studio.forface.easygradle.dsl.android.Version.Channel.Alpha

/**
 * An object containing params for the Library
 *
 * @author Davide Giuseppe Farella
 */
object FluentNotifications {

    val version = Version(0, 2, Alpha, 5, 0)
    const val targetSdk = 28
    const val minSdk = 16
}

object Module {
    const val fluentNotifications = ":fluentnotifications"
}
