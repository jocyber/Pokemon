package pokemongame.moves

import com.lehaine.littlekt.audio.AudioClip
import com.lehaine.littlekt.graphics.g2d.Animation
import com.lehaine.littlekt.graphics.g2d.AnimationPlayer
import com.lehaine.littlekt.graphics.g2d.SpriteBatch
import com.lehaine.littlekt.graphics.g2d.TextureSlice
import com.lehaine.littlekt.graphics.g2d.use
import com.lehaine.littlekt.math.Vec2f
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent.inject
import pokemongame.animations.PokemonAnimation
import pokemongame.animations.PokemonAnimationPlayer
import pokemongame.animations.base.Flicker
import pokemongame.animations.base.Glide
import pokemongame.animations.base.SpriteAnimation
import pokemongame.animations.base.Wait
import pokemongame.koin.TACKLE_HIT
import pokemongame.koin.TACKLE_SOUND
import pokemongame.scene.battle.BattleSceneState
import pokemongame.types.PokemonType

data object Tackle : PokemonMove {
    private val sparksAnimation: Animation<TextureSlice> by
        inject(Animation::class.java, named(TACKLE_HIT))
    private val tackleClip: AudioClip by inject(AudioClip::class.java, named(TACKLE_SOUND))
    private val spriteBatch: SpriteBatch by inject(SpriteBatch::class.java)

    private const val DISTANCE = 65f
    private const val TIME = 0.215f
    private const val SPARKS_DURATION = 0.105f

    override val basePower = 40
    override val totalPowerPoints = 25
    override val accuracy = 100
    override val priority = 0
    override val isContactMove = true
    override val type = PokemonType.NORMAL

    override fun attackAnimation(battleSceneState: BattleSceneState): PokemonAnimationPlayer {
        val startingPoint = battleSceneState.enemyState.position.toVec2()
        val endingPoint = Vec2f(startingPoint.x - DISTANCE, startingPoint.y)

        val glideForward =
            Glide(startingPoint, endingPoint, seconds = TIME, battleSceneState.enemyState)
        val glideBack =
            Glide(endingPoint, startingPoint, seconds = TIME, battleSceneState.enemyState)

        val hitEffect = constructHitEffect(battleSceneState)

        battleSceneState.playerState.currentHealth -= 1

        return PokemonAnimationPlayer(
            moveUpdates =
                arrayOf(
                    arrayOf(glideForward),
                    arrayOf(
                        SpriteAnimation(
                            updateStrategy = { tackleClip.play(volume = .5f) },
                            isDoneStrategy = { true }
                        )
                    ),
                    arrayOf(hitEffect, glideBack),
                    arrayOf(Wait(.5f)),
                    arrayOf(Flicker(battleSceneState.playerState)),
                )
        )
    }

    private fun constructHitEffect(battleSceneState: BattleSceneState): PokemonAnimation {
        val animationPlayer = AnimationPlayer<TextureSlice>()
        animationPlayer.play(sparksAnimation)

        return SpriteAnimation(
            updateStrategy = {
                spriteBatch.use {
                    it.draw(
                        slice = animationPlayer.currentKeyFrame!!,
                        x = 200f,
                        y = -120f,
                        scaleX = 2.5f,
                        scaleY = 2.5f,
                        flipY = true,
                    )
                }
            },
            isDoneStrategy = { it >= SPARKS_DURATION },
        )
    }
}
