@file:Suppress("unused")

package studio.forface.fluentnotifications

import android.app.Notification
import android.content.Context
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import studio.forface.fluentnotifications.builder.CoreParams
import studio.forface.fluentnotifications.builder.NotificationCoreBlock
import studio.forface.fluentnotifications.builder.NotificationCoreBuilder
import studio.forface.fluentnotifications.utils.Android
import studio.forface.fluentnotifications.utils.notificationManager

/**
 * A reference to the [Context.getPackageName] of the calling app.
 * This will be needed for resolve some element from resource.
 */
internal var appPackageName = String()

/**
 * Create a Notification with Dsl from a [Context]
 * @param idRes REQUIRED [IntegerRes] id for create the Notification
 * @param tagRes OPTIONAL [StringRes] tag for create the Notification. If `null` if will be ignored
 * Default is `null`
 *
 * @return [Notification]
 *
 * @see NotificationDsl as [DslMarker]
 */
@NotificationDsl
fun Context.createNotification(
    @IntegerRes idRes: Int,
    @StringRes tagRes: Int? = null,
    block: NotificationCoreBlock
) {
    createNotification(resources.getInteger(idRes), tagRes?.let(::getString), block)
}

/**
 * Create a Notification with Dsl from a [Context]
 * @param id REQUIRED [Int] id for create the Notification
 * @param tag OPTIONAL [CharSequence] tag for create the Notification. If `null` if will be ignored
 * Default is `null`
 *
 * @return [Notification]
 *
 * @see NotificationDsl as [DslMarker]
 */
@NotificationDsl
fun Context.createNotification(
    id: Int,
    tag: CharSequence? = null,
    block: NotificationCoreBlock
): Notification {
    appPackageName = packageName
    val coreParams = CoreParams(this, id, tag.toString())

    return with(notificationManager) {
        with(NotificationCoreBuilder(coreParams).apply(block)) {

            // Create Channel
            if (Android.OREO) createNotificationChannel(buildChannel())

            // Show Group, if any
            buildNotificationGroup()?.let { notificationGroup ->
                notify(notificationGroupTag.toString(), notificationGroupId, notificationGroup)
            }

            // Build Notification
            buildNotification()
        }
    }
}


/**
 * Show a Notification created with Dsl from a [Context]
 * @param idRes REQUIRED [IntegerRes] id for create the Notification
 * @param tagRes OPTIONAL [StringRes] tag for create the Notification. If `null` if will be ignored
 * Default is `null`
 *
 * @see NotificationDsl as [DslMarker]
 */
@NotificationDsl
fun Context.showNotification(
    @IntegerRes idRes: Int,
    @StringRes tagRes: Int? = null,
    block: NotificationCoreBlock
) {
    showNotification(resources.getInteger(idRes), tagRes?.let(::getString), block)
}

/**
 * Show a Notification created with Dsl from a [Context]
 * @param id REQUIRED [Int] id for create the Notification
 * @param tag OPTIONAL [CharSequence] tag for create the Notification. If `null` if will be ignored
 * Default is `null`
 *
 * @see NotificationDsl as [DslMarker]
 */
@NotificationDsl
fun Context.showNotification(
    id: Int,
    tag: CharSequence? = null,
    block: NotificationCoreBlock
) {
    notificationManager.notify(tag.toString(), id, createNotification(id, tag, block))
}


/**
 * Cancel a Notification form a [Context]
 * @param idRes REQUIRED [IntegerRes] id for find the Notification to cancel
 * @param tagRes OPTIONAL [StringRes] tag for find the Notification. If `null` if will be ignored
 * Default is `null`
 */
fun Context.cancelNotification(@IntegerRes idRes: Int, @StringRes tagRes: Int? = null) {
    cancelNotification(resources.getInteger(idRes), tagRes?.let(::getString))
}

/**
 * Cancel a Notification form a [Context]
 * @param id REQUIRED id for find the Notification to cancel
 * @param tag OPTIONAL tag for find the Notification. If `null` if will be ignored
 * Default is `null`
 */
fun Context.cancelNotification(id: Int, tag: String? = null) {
    notificationManager.cancel(tag, id)
}
