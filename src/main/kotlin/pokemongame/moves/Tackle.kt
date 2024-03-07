package pokemongame.moves

import com.lehaine.littlekt.math.Vec2f
import kotlin.time.Duration
import pokemongame.animations.AnimationUpdate
import pokemongame.animations.MoveAnimationResponse
import pokemongame.animations.movements.Glide
import pokemongame.scene.battle.BattleSceneState
import pokemongame.scene.battle.Turn
import pokemongame.types.PokemonType

data object Tackle : PokemonMove {
    override val basePower = 40
    override val totalPowerPoints = 25
    override val accuracy = 100
    override val priority = 0
    override val isContactMove = true
    override val type = PokemonType.NORMAL

    private const val DISTANCE = 50f

    override fun attackAnimation(battleSceneState: BattleSceneState, dt: Duration): MoveAnimation {
        return listOf(glide(battleSceneState, dt, -DISTANCE), glide(battleSceneState, dt, DISTANCE))
    }

    private fun glide(
        sceneState: BattleSceneState,
        dt: Duration,
        distance: Float
    ): List<AnimationUpdate> {
        val animations = mutableListOf<MoveAnimationResponse>()
        val position = sceneState.enemyState.position

        val glide =
            Glide(
                startingPoint = sceneState.enemyState.position,
                endingPoint = Vec2f(position.x + distance, position.y),
                seconds = .4f
            )

        do {
            animations.add(glide.update(dt))
        } while (!glide.isDone())

        return animations.map {
            AnimationUpdate(
                moveAnimationResponse = it,
                animationTurn = Turn.ENEMY,
            )
        }
    }
}
