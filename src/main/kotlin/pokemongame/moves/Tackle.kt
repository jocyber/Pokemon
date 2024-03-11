package pokemongame.moves

import com.lehaine.littlekt.math.Vec2f
import pokemongame.animations.MoveAnimationPlayer
import pokemongame.animations.movements.Glide
import pokemongame.scene.battle.BattleSceneState
import pokemongame.types.PokemonType

data object Tackle : PokemonMove {
    // use Dagger to inject used textures

    override val basePower = 40
    override val totalPowerPoints = 25
    override val accuracy = 100
    override val priority = 0
    override val isContactMove = true
    override val type = PokemonType.NORMAL

    private const val DISTANCE = 65f
    private const val TIME = 0.215f

    override fun attackAnimation(battleSceneState: BattleSceneState): MoveAnimationPlayer {
        val startingPoint = battleSceneState.enemyState.position.toVec2()
        val endingPoint = Vec2f(startingPoint.x - DISTANCE, startingPoint.y)

        val glideForward =
            Glide(startingPoint, endingPoint, seconds = TIME, battleSceneState.enemyState)
        val glideBack =
            Glide(endingPoint, startingPoint, seconds = TIME, battleSceneState.enemyState)

        return MoveAnimationPlayer(
            moveUpdates =
                arrayOf(
                    arrayOf(glideForward),
                    arrayOf(glideBack),
                )
        )
    }
}
