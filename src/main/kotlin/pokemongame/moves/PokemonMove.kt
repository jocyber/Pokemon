package pokemongame.moves

import com.lehaine.littlekt.graphics.g2d.Animation
import pokemongame.scene.battle.BattleSceneState
import pokemongame.types.PokemonType

sealed interface PokemonMove {
    val basePower: Int
    val totalPowerPoints: Int
    val accuracy: Int
    val priority: Int
    val isContactMove: Boolean
    val type: PokemonType

    fun attackAnimation(battleSceneState: BattleSceneState): Animation<*>
}