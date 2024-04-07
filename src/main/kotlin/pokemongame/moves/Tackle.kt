package pokemongame.moves

import com.lehaine.littlekt.audio.AudioClip
import com.lehaine.littlekt.graphics.Texture
import com.lehaine.littlekt.graphics.g2d.Animation
import com.lehaine.littlekt.graphics.g2d.AnimationPlayer
import com.lehaine.littlekt.graphics.g2d.SpriteBatch
import com.lehaine.littlekt.graphics.g2d.TextureSlice
import com.lehaine.littlekt.graphics.g2d.use
import com.lehaine.littlekt.graphics.slice
import com.lehaine.littlekt.math.Vec2f
import com.lehaine.littlekt.util.seconds
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent.inject
import pokemongame.animations.PokemonAnimationPlayer
import pokemongame.animations.base.Glide
import pokemongame.animations.base.SpriteAnimation
import pokemongame.animations.base.Wait
import pokemongame.koin.HIT_EFFECT
import pokemongame.koin.TACKLE_SOUND
import pokemongame.scene.battle.BattleEntity
import pokemongame.scene.battle.BattleSceneState
import pokemongame.types.PokemonType

data object Tackle : PokemonMove {
    private val hitEffect: Texture by inject(Texture::class.java, named(HIT_EFFECT))
    private val tackleClip: AudioClip by inject(AudioClip::class.java, named(TACKLE_SOUND))
    private val spriteBatch: SpriteBatch by inject(SpriteBatch::class.java)

    private val animationPlayer = AnimationPlayer<TextureSlice>()
    private val sparksAnimation =
        Animation(
            frames = listOf(hitEffect.slice()),
            frameIndices = listOf(0),
            frameTimes = listOf(0.125f.seconds),
        )

    private const val DISTANCE = 65f
    private const val TIME = 0.215f
    private const val SPARKS_DURATION = 0.105f

    override val basePower = 40
    override val totalPowerPoints = 25
    override val accuracy = 100
    override val priority = 0
    override val isContactMove = true
    override val type = PokemonType.NORMAL

    override fun attackAnimation(battleSceneState: BattleSceneState) =
        with(battleSceneState) {
            val startingPoint = currentTarget.position
            val endingPoint =
                Vec2f(
                    startingPoint.x + if (turn == BattleEntity.ENEMY) -DISTANCE else DISTANCE,
                    startingPoint.y
                )

            val glideForward = Glide(startingPoint, endingPoint, seconds = TIME, currentTarget)
            val glideBack = Glide(endingPoint, startingPoint, seconds = TIME, currentTarget)

            opposingTarget.wasHit = true
            animationPlayer.play(sparksAnimation)

            val sparksPosition =
                if (turn == BattleEntity.ENEMY) Vec2f(200f, -120f) else Vec2f(675f, 50f)

            PokemonAnimationPlayer(
                moveUpdates =
                    arrayOf(
                        arrayOf(glideForward),
                        arrayOf(
                            SpriteAnimation(
                                updateStrategy = { tackleClip.play(volume = .5f) },
                                isDoneStrategy = { true }
                            )
                        ),
                        arrayOf(
                            SpriteAnimation(
                                updateStrategy = {
                                    spriteBatch.use {
                                        it.draw(
                                            slice = animationPlayer.currentKeyFrame!!,
                                            x = sparksPosition.x,
                                            y = sparksPosition.y,
                                            scaleX = 2.5f,
                                            scaleY = 2.5f,
                                            flipY = true,
                                        )
                                    }
                                },
                                isDoneStrategy = { it >= SPARKS_DURATION },
                            ),
                            glideBack
                        ),
                        arrayOf(Wait(.5f)),
                    )
            )
        }
}
