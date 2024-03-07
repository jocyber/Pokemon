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
import pokemongame.moves.MoveAnimation
import pokemongame.moves.Tackle
import pokemongame.scene.SCREEN_WIDTH
import pokemongame.scene.Weather

class BattleSceneController(private val battleScene: BattleScene) {
    private val batch: SpriteBatch = SpriteBatch(battleScene.context)

    private val animationPlayer: AnimationPlayer<TextureSlice>
    private val enemyAnimation: Animation<TextureSlice>

    // introduce move animation player
    private var moveAnimation: MoveAnimation? = null
    private var animationIndices = Pair(0, 0)

    // migrate move animation execution to the turn executor
    private val sceneState =
        BattleSceneState(
            playerState = PokemonState(position = Vec2f(ENEMY_X, ENEMY_Y)),
            enemyState = PokemonState(position = Vec2f(ENEMY_X, ENEMY_Y)),
            weather = Weather.SUN,
            currentTurn = Turn.ENEMY
        )

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
    }

    private fun drawEnemy(dt: Duration) {
        if (battleScene.context.input.isKeyPressed(Key.E) && animationIndices.first == 0) {
            moveAnimation = Tackle.attackAnimation(sceneState, dt)
        }

        val position =
            if (moveAnimation != null) {
                val moveFrame = moveAnimation!![animationIndices.first][animationIndices.second]

                // this logic is dumb and crashes, but it shows that the movement works
                // should move this to the turn executor
                animationIndices =
                    if (
                        animationIndices.second + 1 == moveAnimation!![animationIndices.first].size
                    ) {
                        Pair(animationIndices.first + 1, 0)
                    } else {
                        Pair(animationIndices.first, animationIndices.second + 1)
                    }

                moveFrame.moveAnimationResponse.position
            } else {
                Vec2f(ENEMY_X, ENEMY_Y)
            }

        batch.use {
            // draw the shadow
            it.draw(
                animationPlayer.currentKeyFrame!!,
                x = position.x - 10,
                y = position.y + 10,
                flipY = true,
                scaleX = 3f,
                colorBits = 210f
            )

            it.draw(
                animationPlayer.currentKeyFrame!!,
                x = position.x,
                y = position.y,
                flipY = true,
                scaleX = 3f,
                scaleY = 3f,
            )
        }
    }

    companion object {
        const val IDLE = 3500f / 60f
        const val ENEMY_X = 650f
        const val ENEMY_Y = 30f
    }
}
