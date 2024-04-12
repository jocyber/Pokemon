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
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent.inject
import pokemongame.animations.PokemonAnimation
import pokemongame.koin.HEALTH_BAR_TEXTURE
import pokemongame.scene.battle.BattleEntity

class HealthBar(
    currentHealth: Int,
    val battleEntity: BattleEntity,
    val totalHealth: Int,
) {
    val animationPlayer = AnimationPlayer<TextureSlice>()
    var currentFrame = currentFrameFromPercent(currentHealth, totalHealth)
        private set

    private var animation = getAnimation(listOf(currentFrame), battleEntity)

    init {
        require(currentHealth in 0..totalHealth)
        animationPlayer.play(animation)
    }

    fun updateHealth(updatedHealth: Int): PokemonAnimation {
        val lastFrame = currentFrame
        currentFrame = currentFrameFromPercent(updatedHealth, totalHealth)

        val frameIndices =
            (if (currentFrame < lastFrame) (lastFrame downTo currentFrame)
                else (lastFrame..currentFrame))
                .toList()

        animation = getAnimation(frameIndices, battleEntity)
        animationPlayer.play(animation)

        return object : PokemonAnimation {
            override fun update(dt: Duration) = animationPlayer.update(dt)

            override fun isDone() =
                animationPlayer.totalFramesPlayed == abs(lastFrame - currentFrame)
        }
    }

    private fun currentFrameFromPercent(updatedHealth: Int, maxHealth: Int): Int {
        val healthPercent = updatedHealth.toFloat() / maxHealth.toFloat()
        val roundedFrame = round(MAX_FRAME_INDEX * healthPercent).toInt()

        return if (updatedHealth > 0 && roundedFrame == 0) 1 else roundedFrame
    }

    companion object {
        const val MAX_FRAME_INDEX = 48f
        private const val SWITCH_TIME = 0.03f

        private const val WIDTH_PLAYER = 128
        private const val HEIGHT_PLAYER = 42

        private const val WIDTH_ENEMY = 122
        private const val HEIGHT_ENEMY = 35

        private val FRAME_RANGE = (0..MAX_FRAME_INDEX.toInt())
        private val FRAME_TIMES = FRAME_RANGE.map { SWITCH_TIME.seconds }

        private val TEXTURE: Texture by
            inject(Texture::class.java, qualifier = named(HEALTH_BAR_TEXTURE))

        private val SLICES_BY_ENTITY =
            mapOf(
                BattleEntity.PLAYER to
                    FRAME_RANGE.map {
                        TextureSlice(
                            TEXTURE,
                            x = WIDTH_PLAYER * it,
                            y = HEIGHT_ENEMY,
                            width = WIDTH_PLAYER,
                            height = HEIGHT_PLAYER
                        )
                    },
                BattleEntity.ENEMY to TEXTURE.slice(WIDTH_ENEMY, HEIGHT_ENEMY).flatten().toList()
            )

        private fun getAnimation(indices: List<Int>, battleEntity: BattleEntity) =
            Animation(
                frames = SLICES_BY_ENTITY[battleEntity]!!,
                frameIndices = indices,
                frameTimes = FRAME_TIMES,
            )
    }
}
