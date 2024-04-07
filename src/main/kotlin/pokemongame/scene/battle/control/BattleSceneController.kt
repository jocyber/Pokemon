package pokemongame.scene.battle.control

import com.lehaine.littlekt.graphics.Color
import com.lehaine.littlekt.graphics.g2d.Animation
import com.lehaine.littlekt.graphics.g2d.AnimationPlayer
import com.lehaine.littlekt.graphics.g2d.SpriteBatch
import com.lehaine.littlekt.graphics.g2d.TextureSlice
import com.lehaine.littlekt.graphics.g2d.use
import com.lehaine.littlekt.graphics.slice
import com.lehaine.littlekt.graphics.toFloatBits
import com.lehaine.littlekt.input.Key
import com.lehaine.littlekt.util.milliseconds
import kotlin.time.Duration
import pokemongame.scene.SCREEN_WIDTH
import pokemongame.scene.battle.BattleScene

class BattleSceneController(private val battleScene: BattleScene) {
    private val batch: SpriteBatch = SpriteBatch(battleScene.context)

    // have a move selector and bag handler
    private var turnExecutor: TurnExecutor? = null

    private val enemyAnimationPlayer: AnimationPlayer<TextureSlice>
    private val playerAnimationPlayer: AnimationPlayer<TextureSlice>

    private val enemyAnimation: Animation<TextureSlice>
    private val playerAnimation: Animation<TextureSlice>

    // migrate move animation execution to the turn executor
    init {
        val enemyPokemon = battleScene.enemyStats
        val playerPokemon = battleScene.playerStats

        // move standing animations to BattleSceneDrawer

        val enemySlices =
            battleScene.enemyPokemonTexture
                .slice(
                    sliceWidth = enemyPokemon.pokemon.frontTextureWidth,
                    sliceHeight = enemyPokemon.pokemon.frontTextureHeight
                )
                .flatten()

        // move slicing strategy to pokemon with default implementation
        val playerSlices =
            (0..playerPokemon.pokemon.backFrames).map {
                TextureSlice(
                    battleScene.playerPokemonTexture,
                    x = playerPokemon.pokemon.backTextureWidth * it + 1,
                    y = 0,
                    width = playerPokemon.pokemon.backTextureWidth,
                    height = playerPokemon.pokemon.backTextureHeight,
                )
            }
        battleScene.playerPokemonTexture

        this.enemyAnimationPlayer = AnimationPlayer()
        this.enemyAnimation =
            Animation(
                frames = enemySlices,
                frameIndices = enemySlices.indices.toList(),
                frameTimes = enemySlices.map { IDLE.milliseconds }
            )
        enemyAnimationPlayer.playLooped(enemyAnimation)

        this.playerAnimationPlayer = AnimationPlayer()
        this.playerAnimation =
            Animation(
                frames = playerSlices,
                frameIndices = playerSlices.indices.toList(),
                frameTimes = playerSlices.map { IDLE.milliseconds }
            )
        playerAnimationPlayer.playLooped(playerAnimation)
    }

    fun controlLogic(dt: Duration) {
        enemyAnimationPlayer.update(dt)
        playerAnimationPlayer.update(dt)

        if (battleScene.context.input.isKeyPressed(Key.E) && turnExecutor == null) {
            turnExecutor = TurnExecutor(battleScene)
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

        drawPokemon(dt)

        batch.use {
            it.draw(
                battleScene.sceneState.playerState.healthBar?.animationPlayer?.currentKeyFrame!!,
                x = 576f,
                y = -120f,
                scaleX = 3f,
                scaleY = 2.75f,
                flipY = true,
            )

            it.draw(
                battleScene.sceneState.enemyState.healthBar?.animationPlayer?.currentKeyFrame!!,
                x = 0f,
                y = 140f,
                scaleX = 3f,
                scaleY = 2.75f,
                flipY = true,
            )
        }

        turnExecutor?.updateTurn(dt)
        //        if (turnExecutor?.updateTurn(dt) == true) {
        //            turnExecutor = null
        //        }

        batch.use {
            it.draw(
                battleScene.moveSelectBackground,
                x = 0f,
                y = -287f,
                width = SCREEN_WIDTH,
                scaleY = 2.978f,
                flipY = true,
            )
        }
    }

    private fun drawPokemon(dt: Duration) {
        batch.use {
            // draw the enemies shadow
            it.draw(
                enemyAnimationPlayer.currentKeyFrame!!,
                x = battleScene.sceneState.enemyState.position.x - 10,
                y = battleScene.sceneState.enemyState.position.y + 10,
                flipY = true,
                scaleX = 3f,
                colorBits = SHADOW_COLOR,
            )

            it.draw(
                enemyAnimationPlayer.currentKeyFrame!!,
                x = battleScene.sceneState.enemyState.position.x,
                y = battleScene.sceneState.enemyState.position.y,
                flipY = true,
                scaleX = 3f,
                scaleY = 3f,
                colorBits = battleScene.sceneState.enemyState.colorBits
            )

            it.draw(
                playerAnimationPlayer.currentKeyFrame!!,
                x = battleScene.sceneState.playerState.position.x,
                y = battleScene.sceneState.playerState.position.y,
                flipY = true,
                scaleX = 4f,
                scaleY = 4f,
                colorBits = battleScene.sceneState.playerState.colorBits
            )
        }
    }

    private companion object {
        const val IDLE = 3500f / 60f

        val SHADOW_COLOR = Color.DARK_GRAY.withAlpha(0.4f).toFloatBits()
    }
}
