package pokemongame.scene.battle.control

import com.lehaine.littlekt.graphics.Color
import com.lehaine.littlekt.graphics.g2d.Animation
import com.lehaine.littlekt.graphics.g2d.AnimationPlayer
import com.lehaine.littlekt.graphics.g2d.TextureSlice
import com.lehaine.littlekt.graphics.g2d.use
import com.lehaine.littlekt.graphics.slice
import com.lehaine.littlekt.graphics.toFloatBits
import com.lehaine.littlekt.util.milliseconds
import kotlin.time.Duration
import pokemongame.scene.SCREEN_WIDTH
import pokemongame.scene.SceneDrawer
import pokemongame.scene.battle.BattleScene

class BattleSceneDrawer(private val battleScene: BattleScene) : SceneDrawer() {
    private val enemyAnimationPlayer: AnimationPlayer<TextureSlice>
    private val playerAnimationPlayer: AnimationPlayer<TextureSlice>

    private val enemyAnimation: Animation<TextureSlice>
    private val playerAnimation: Animation<TextureSlice>

    init {
        val enemyPokemon = battleScene.enemyStats
        val playerPokemon = battleScene.playerStats

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

    @Suppress("LongMethod")
    override fun draw(dt: Duration) {
        enemyAnimationPlayer.update(dt)
        playerAnimationPlayer.update(dt)

        SPRITE_BATCH.use {
            with(battleScene.sceneState) {
                // draw background
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
                    enemyAnimationPlayer.currentKeyFrame!!,
                    x = enemyState.position.x - 10,
                    y = enemyState.position.y + 10,
                    flipY = true,
                    scaleX = 3f,
                    colorBits = SHADOW_COLOR,
                )

                // draw the enemy
                it.draw(
                    enemyAnimationPlayer.currentKeyFrame!!,
                    x = enemyState.position.x,
                    y = enemyState.position.y,
                    flipY = true,
                    scaleX = 3f,
                    scaleY = 3f,
                    colorBits = enemyState.colorBits
                )

                // draw the player
                it.draw(
                    playerAnimationPlayer.currentKeyFrame!!,
                    x = playerState.position.x,
                    y = playerState.position.y,
                    flipY = true,
                    scaleX = 4f,
                    scaleY = 4f,
                    colorBits = playerState.colorBits
                )

                if (!(enemyState.isAttacking || playerState.isAttacking)) {
                    // draw the player health bar
                    it.draw(
                        playerState.healthBar.animationPlayer.currentKeyFrame!!,
                        x = 576f,
                        y = -120f,
                        scaleX = 3f,
                        scaleY = 2.75f,
                        flipY = true
                    )

                    // draw the enemy health bar
                    it.draw(
                        enemyState.healthBar.animationPlayer.currentKeyFrame!!,
                        x = 0f,
                        y = 140f,
                        scaleX = 3f,
                        scaleY = 2.75f,
                        flipY = true,
                    )
                }

                // draw the move select background
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
    }

    private companion object {
        const val IDLE = 3500f / 60f

        val SHADOW_COLOR = Color.DARK_GRAY.withAlpha(0.4f).toFloatBits()
    }
}
