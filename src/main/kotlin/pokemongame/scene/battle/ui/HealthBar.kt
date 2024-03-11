package pokemongame.scene.battle.ui

import com.google.inject.Inject
import com.google.inject.name.Named
import com.lehaine.littlekt.graphics.Texture
import com.lehaine.littlekt.graphics.g2d.Animation
import com.lehaine.littlekt.graphics.g2d.AnimationPlayer
import com.lehaine.littlekt.graphics.g2d.TextureSlice
import com.lehaine.littlekt.graphics.slice
import com.lehaine.littlekt.util.seconds
import kotlin.math.round
import kotlin.time.Duration
import pokemongame.guice.HEALTH_BAR_TEXTURE

class HealthBar(
    currentHealth: Int,
    isPlayer: Boolean,
    private val totalHealth: Int,
) {
    @Inject @Named(HEALTH_BAR_TEXTURE)
    private lateinit var healthBarTexture: Texture

    private var currentFrame: Int
    private val slices: List<TextureSlice>
    private val animation: Animation<TextureSlice>

    val animationPlayer = AnimationPlayer<TextureSlice>()

    init {
        this.currentFrame = currentFrameFromPercent(currentHealth, totalHealth)

        slices = if (isPlayer) {
            healthBarTexture.slice(WIDTH_PLAYER, HEIGHT_PLAYER)[1].toList()
        } else {
            healthBarTexture.slice(WIDTH_ENEMY, HEIGHT_ENEMY)[0].toList()
        }

        val frameIndices = (FRAMES.toInt() downTo 0).toList()

        this.animation = Animation(
            frames = slices,
            frameIndices,
            frameTimes = frameIndices.map { SWITCH_TIME.seconds },
        )
        animationPlayer.play(animation)
    }

    fun updateHealth(updatedHealth: Int): (dt: Duration) -> Boolean {
        val lastFrame = currentFrame
        currentFrame = currentFrameFromPercent(updatedHealth, totalHealth)

        val updates = lastFrame - currentFrame

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
