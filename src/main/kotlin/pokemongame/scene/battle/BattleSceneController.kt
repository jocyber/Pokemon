package pokemongame.scene.battle

import com.lehaine.littlekt.graphics.g2d.Animation
import com.lehaine.littlekt.graphics.g2d.AnimationPlayer
import com.lehaine.littlekt.graphics.g2d.SpriteBatch
import com.lehaine.littlekt.graphics.g2d.TextureSlice
import com.lehaine.littlekt.graphics.g2d.use
import com.lehaine.littlekt.graphics.slice
import com.lehaine.littlekt.util.milliseconds
import kotlin.time.Duration
import kotlinx.coroutines.runBlocking
import pokemongame.scene.SCREEN_WIDTH

class BattleSceneController(private val battleScene: BattleScene) {
    private val batch: SpriteBatch = SpriteBatch(battleScene.context)

    private val animationPlayer: AnimationPlayer<TextureSlice>
    private val enemyAnimation: Animation<TextureSlice>

    init {
        val enemyPokemon = battleScene.enemyPokemon

        // move animations to battle scene

        val enemySlices = runBlocking {
            enemyPokemon
                .getTexture(battleScene.context)
                .slice(
                    sliceWidth = enemyPokemon.textureWidth,
                    sliceHeight = enemyPokemon.textureHeight
                )
                .flatten()
        }
        this.animationPlayer = AnimationPlayer()
        this.enemyAnimation =
            Animation(
                frames = enemySlices,
                frameIndices = enemySlices.indices.toList(),
                frameTimes = enemySlices.map { IDLE.milliseconds }
            )

        animationPlayer.playLooped(enemyAnimation)
    }

    fun controlLogic(dt: Duration) {
        animationPlayer.update(dt)

        draw()
    }

    private fun draw() {
        batch.use {
            it.draw(
                battleScene.backgroundTexture,
                x = 0f,
                y = -150f,
                width = SCREEN_WIDTH,
                scaleY = 4f,
                flipY = true
            )

            // draw the enemies shadow
            it.draw(
                animationPlayer.currentKeyFrame!!,
                x = 640f,
                y = 40f,
                flipY = true,
                scaleX = 3f,
                colorBits = 210f
            )

            it.draw(
                animationPlayer.currentKeyFrame!!,
                x = 650f,
                y = 30f,
                flipY = true,
                scaleX = 3f,
                scaleY = 3f,
            )
        }
    }

    companion object {
        const val IDLE = 3500f / 60f
    }
}
