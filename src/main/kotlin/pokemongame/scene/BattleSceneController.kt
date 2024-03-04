package pokemongame.scene

import com.lehaine.littlekt.graphics.g2d.*
import com.lehaine.littlekt.graphics.slice
import com.lehaine.littlekt.util.milliseconds
import kotlin.time.Duration
import kotlinx.coroutines.runBlocking

class BattleSceneController(battleScene: BattleScene) {
    private val batch: SpriteBatch
    private val battleScene: BattleScene

    private val animationPlayer: AnimationPlayer<TextureSlice>
    private val enemyAnimation: Animation<TextureSlice>

    init {
        this.batch = SpriteBatch(battleScene.context)
        this.battleScene = battleScene

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
                frameIndices = (0 ..< enemySlices.size).toList(),
                frameTimes = (0 ..< enemySlices.size).map { idleMillis.milliseconds }
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
        const val idleMillis = 3500f / 60f
    }
}
