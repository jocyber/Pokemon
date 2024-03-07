package pokemongame.moves

import kotlin.time.Duration
import pokemongame.animations.AnimationUpdate
import pokemongame.scene.battle.BattleSceneState
import pokemongame.types.PokemonType

sealed interface PokemonMove {
    val basePower: Int
    val totalPowerPoints: Int
    val accuracy: Int
    val priority: Int
    val isContactMove: Boolean
    val type: PokemonType

    fun attackAnimation(battleSceneState: BattleSceneState, dt: Duration): MoveAnimation
}

typealias MoveAnimation = List<List<AnimationUpdate>>
