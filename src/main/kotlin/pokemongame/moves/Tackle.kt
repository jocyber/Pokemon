package pokemongame.moves

import com.google.inject.Inject
import com.google.inject.name.Named
import com.lehaine.littlekt.graphics.g2d.Animation
import com.lehaine.littlekt.graphics.g2d.AnimationPlayer
import com.lehaine.littlekt.graphics.g2d.SpriteBatch
import com.lehaine.littlekt.graphics.g2d.TextureSlice
import com.lehaine.littlekt.graphics.g2d.use
import com.lehaine.littlekt.math.Vec2f
import pokemongame.animations.MoveAnimationPlayer
import pokemongame.animations.movements.Glide
import pokemongame.animations.movements.MoveAnimation
import pokemongame.animations.movements.SpriteAnimation
import pokemongame.guice.TACKLE_HIT
import pokemongame.scene.battle.BattleSceneState
import pokemongame.types.PokemonType

class Tackle @Inject constructor(
    @Named(TACKLE_HIT) private val sparksAnimation: Animation<TextureSlice>,
    private val spriteBatch: SpriteBatch,
) : PokemonMove {
    override val basePower = 40
    override val totalPowerPoints = 25
    override val accuracy = 100
    override val priority = 0
    override val isContactMove = true
    override val type = PokemonType.NORMAL

    override fun attackAnimation(battleSceneState: BattleSceneState): MoveAnimationPlayer {
        val startingPoint = battleSceneState.enemyState.position.toVec2()
        val endingPoint = Vec2f(startingPoint.x - DISTANCE, startingPoint.y)

        val glideForward =
            Glide(startingPoint, endingPoint, seconds = TIME, battleSceneState.enemyState)
        val glideBack =
            Glide(endingPoint, startingPoint, seconds = TIME, battleSceneState.enemyState)
        val hitEffect = constructHitEffect(battleSceneState)

        return MoveAnimationPlayer(
            moveUpdates =
                arrayOf(
                    arrayOf(glideForward),
                    arrayOf(hitEffect, glideBack),
                )
        )
    }

    private fun constructHitEffect(battleSceneState: BattleSceneState): MoveAnimation {
        val animationPlayer = AnimationPlayer<TextureSlice>()
        animationPlayer.play(sparksAnimation)

        return SpriteAnimation(
            updateStrategy = {
                spriteBatch.use {
                    it.draw(
                        slice = animationPlayer.currentKeyFrame!!,
                        x = 40f,
                        y = -10f,
                        scaleX = 2.5f,
                        scaleY = 2.5f,
                        flipY = true,
                    )
                }
            },
            isDoneStrategy = { it >= SPARKS_DURATION },
        )
    }

    private companion object {
        private const val DISTANCE = 65f
        private const val TIME = 0.215f
        private const val SPARKS_DURATION = 0.125f
    }
}
