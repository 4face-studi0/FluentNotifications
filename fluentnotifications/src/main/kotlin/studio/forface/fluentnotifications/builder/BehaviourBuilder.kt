@file:Suppress("MemberVisibilityCanBePrivate")

package studio.forface.fluentnotifications.builder

import android.app.NotificationChannel
import android.content.res.Resources
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.app.NotificationCompat
import studio.forface.fluentnotifications.NotificationDsl
import studio.forface.fluentnotifications.enum.DefaultBehaviour
import studio.forface.fluentnotifications.enum.NotificationImportance
import studio.forface.fluentnotifications.utils.Android
import studio.forface.fluentnotifications.utils.ResourcedBuilder
import studio.forface.fluentnotifications.utils.invoke
import studio.forface.fluentnotifications.utils.optional

/**
 * A Builder for define a behaviour for Notification / Channel
 * Inherit from [ResourcedBuilder] for provide [Resources
 *
 * @constructor is internal. Instances will be created from [NotificationCoreBuilder] via `Context.showNotification`
 * @param resources [Resources] required for [ResourcedBuilder] delegation
 *
 * @see NotificationDsl as [DslMarker]
 *
 *
 * @author Davide Giuseppe Farella`
 */
@NotificationDsl
class BehaviourBuilder internal constructor(
    resources: Resources
) : ResourcedBuilder by resources() {

    /**
     * A mutable [Set] of [DefaultBehaviour]
     * @see [plus]
     */
    internal val defaults = mutableSetOf<DefaultBehaviour>()

    /**
     * REQUIRED [NotificationImportance] importance for the [NotificationChannel] on [Android.OREO] or priority on for
     * [NotificationCompat] on pre-Oreo
     * Default is [NotificationImportance.DEFAULT]
     *
     * @see Behaviour.importance
     */
    var importance: NotificationImportance = NotificationImportance.DEFAULT

    /**
     * OPTIONAL [ColorInt] for the Light color of the Notification / Channel
     * Light will be automatically enabled / disabled whether this value is `null` or not
     *
     * Backed by [lightColorRes]
     *
     * @see Behaviour.lightColor
     */
    @get:ColorInt var lightColor: Int? by optional { lightColorRes }

    /**
     * OPTIONAL [ColorInt] for the Light color of the Notification / Channel
     * Light will be automatically enabled / disabled whether this value is `null` or not
     *
     * Backing value of [lightColor]
     *
     * @see Behaviour.lightColor
     */
    @ColorRes var lightColorRes: Int? = null

    /**
     * OPTIONAL [LongArray] for the pattern of the vibration of the Notification / Channel
     * Vibration will be automatically enabled / disable whether this [LongArray] is empty or not
     *
     * @see Behaviour.vibrationPattern
     */
    var vibrationPattern: LongArray = longArrayOf()

    /** @return [Behaviour] with the define params */
    internal fun build() = Behaviour(
        defaults =          defaults,
        lightColor =        lightColor,
        importance =        importance,
        vibrationPattern =  vibrationPattern
    )
}

/**
 * A set of behaviour params for Notification / Channel
 *
 * @property defaults [Set] of [DefaultBehaviour] for Notification / Channel
 * @see NotificationCompat.Builder.setDefaults
 * @see `NotificationChannel.setDefaults` extension
 *
 * @property lightColor [ColorInt] for the light of the Notification / Channel
 * @see NotificationCompat.Builder.setLights
 * @see NotificationChannel.setLightColor
 *
 * @property importance [NotificationBuilder] that contains priority for the Notification / Channel
 * @see NotificationCompat.Builder.setPriority
 * @see NotificationChannel.setImportance
 *
 * @property vibrationPattern [LongArray] of the pattern for the vibration of the Notification / Channel
 * @see NotificationCompat.Builder.setVibrate
 * @see NotificationChannel.setVibrationPattern
 */
internal class Behaviour(
    val defaults: Set<DefaultBehaviour>,
    @ColorInt val lightColor: Int?,
    val importance: NotificationImportance,
    val vibrationPattern: LongArray
)

/** Typealias for a lambda that takes [BehaviourBuilder] as receiver and returns [Unit] */
typealias BehaviourBlock = BehaviourBuilder.() -> Unit

/**
 * Plus operator for add the given [DefaultBehaviour] to [BehaviourBuilder.defaults]
 * @return [DefaultBehaviour]
 */
operator fun BehaviourBuilder.plus( default: DefaultBehaviour ) = apply {
    defaults += default
}

val BehaviourBuilder.defaultVibration get() = DefaultBehaviour.VIBRATION