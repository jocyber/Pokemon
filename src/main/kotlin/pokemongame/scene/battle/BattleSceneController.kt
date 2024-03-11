package pokemongame.scene.battle

import com.lehaine.littlekt.graphics.g2d.Animation
import com.lehaine.littlekt.graphics.g2d.AnimationPlayer
import com.lehaine.littlekt.graphics.g2d.SpriteBatch
import com.lehaine.littlekt.graphics.g2d.TextureSlice
import com.lehaine.littlekt.graphics.g2d.use
import com.lehaine.littlekt.graphics.slice
import com.lehaine.littlekt.input.Key
import com.lehaine.littlekt.math.Vec2f
import com.lehaine.littlekt.util.milliseconds
import kotlin.time.Duration
import kotlinx.coroutines.runBlocking
import pokemongame.scene.SCREEN_WIDTH
import pokemongame.scene.Weather

class BattleSceneController(private val battleScene: BattleScene) {
    private val batch: SpriteBatch = SpriteBatch(battleScene.context)

    // have a move selector and bag handler
    private var turnExecutor: TurnExecutor? = null

    private val animationPlayer: AnimationPlayer<TextureSlice>
    private val enemyAnimation: Animation<TextureSlice>

    // migrate move animation execution to the turn executor
    private val sceneState =
        BattleSceneState(
            playerState = PokemonBattleState(position = ENEMY_POS),
            enemyState = PokemonBattleState(position = ENEMY_POS),
            weather = Weather.SUN,
        )

    init {
        val enemyPokemon = battleScene.enemyPokemon

        // standing animations to battle scene

        val enemySlices = runBlocking {
            enemyPokemon.pokemon
                .getTexture(battleScene.context)
                .slice(
                    sliceWidth = enemyPokemon.pokemon.textureWidth,
                    sliceHeight = enemyPokemon.pokemon.textureHeight
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

        if (battleScene.context.input.isKeyPressed(Key.E) && turnExecutor == null) {
            turnExecutor = TurnExecutor(Turn.ENEMY, sceneState)
        }

        if (turnExecutor?.updateTurn(dt) == true) {
            turnExecutor = null
        }

        batch.use {
            it.draw(
                battleScene.backgroundTexture,
                x = 0f,
                y = -150f,
                width = SCREEN_WIDTH,
                scaleY = 4f,
                flipY = true
            )
        }

        drawEnemy(dt)

        batch.use {
            it.draw(
                battleScene.playerHealthBar?.animationPlayer!!.currentKeyFrame!!,
                x = 576f,
                y = 320f,
                scaleX = 3f,
                scaleY = 2.75f,
            )

            it.draw(
                battleScene.enemyHealthBar?.animationPlayer!!.currentKeyFrame!!,
                x = 0f,
                y = 40f,
                scaleX = 3f,
                scaleY = 2.75f,
            )
        }
    }

    private fun drawEnemy(dt: Duration) {
        batch.use {
            // draw the shadow
            it.draw(
                animationPlayer.currentKeyFrame!!,
                x = sceneState.enemyState.position.x - 10,
                y = sceneState.enemyState.position.y + 10,
                flipY = true,
                scaleX = 3f,
                colorBits = 210f
            )

            it.draw(
                animationPlayer.currentKeyFrame!!,
                x = sceneState.enemyState.position.x,
                y = sceneState.enemyState.position.y,
                flipY = true,
                scaleX = 3f,
                scaleY = 3f,
            )
        }
    }

    private companion object {
        const val IDLE = 3500f / 60f
        val ENEMY_POS = Vec2f(650f, 30f)
    }
}
