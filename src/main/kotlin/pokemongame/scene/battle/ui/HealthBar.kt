package pokemongame.scene.battle.ui

import com.lehaine.littlekt.graphics.Texture
import com.lehaine.littlekt.graphics.g2d.Animation
import com.lehaine.littlekt.graphics.g2d.AnimationPlayer
import com.lehaine.littlekt.graphics.g2d.TextureSlice
import com.lehaine.littlekt.graphics.slice
import com.lehaine.littlekt.util.seconds
import kotlin.math.abs
import kotlin.math.round
import kotlin.time.Duration

class HealthBar(
    currentHealth: Int,
    isPlayer: Boolean,
    private val totalHealth: Int,
    texture: Texture,
) {
    private var animation: Animation<TextureSlice>
    private var currentFrame: Int

    private val slices: List<TextureSlice> =
        if (isPlayer) {
            (0 ..< FRAMES.toInt()).map {
                TextureSlice(
                    texture,
                    x = WIDTH_PLAYER * it,
                    y = HEIGHT_ENEMY,
                    width = WIDTH_PLAYER,
                    height = HEIGHT_PLAYER
                )
            }
        } else {
            texture.slice(WIDTH_ENEMY, HEIGHT_ENEMY).flatten().toList()
        }

    val animationPlayer = AnimationPlayer<TextureSlice>()

    init {
        currentFrame = currentFrameFromPercent(currentHealth, totalHealth)
        animation =
            Animation(
                frames = slices,
                frameIndices = listOf(currentFrame),
                frameTimes = (0..currentFrame).map { SWITCH_TIME.seconds },
            )

        animationPlayer.play(animation)
    }

    fun updateHealth(updatedHealth: Int): (dt: Duration) -> Boolean {
        val lastFrame = currentFrame
        currentFrame = currentFrameFromPercent(updatedHealth, totalHealth)

        val updates = abs(lastFrame - currentFrame)
        val framesIndices = (lastFrame downTo currentFrame).toList()

        animation =
            Animation(
                frames = slices,
                frameIndices = framesIndices,
                frameTimes = framesIndices.map { SWITCH_TIME.seconds },
            )

        animationPlayer.play(animation)

        return { dt ->
            animationPlayer.update(dt)
            animationPlayer.totalFramesPlayed == updates
        }
    }

    private fun currentFrameFromPercent(updatedHealth: Int, maxHealth: Int): Int {
        val healthPercent = updatedHealth.toFloat() / maxHealth.toFloat()
        return round(FRAMES * healthPercent).toInt()
    }

    private companion object {
        const val FRAMES = 48f
        const val SWITCH_TIME = 0.03f

        const val WIDTH_PLAYER = 128
        const val HEIGHT_PLAYER = 42

        const val WIDTH_ENEMY = 122
        const val HEIGHT_ENEMY = 35
    }
}
