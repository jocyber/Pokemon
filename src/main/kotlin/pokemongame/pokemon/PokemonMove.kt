package pokemongame.pokemon

import pokemongame.pokemon.PokemonType.FIGHTING
import pokemongame.pokemon.PokemonType.NORMAL
import pokemongame.scene.battle.BattleSceneState
import pokemongame.scene.battle.MoveExecutionResult
import pokemongame.scene.battle.visitExecuteMove

enum class Category {
    PHYSICAL,
    SPECIAL,
    STATUS,
}

/**
 * An interface containing data about a move that a Pokemon could potentially learn.
 * <ul>
 * <li> The base power of the move, which determines how strong the move is.
 * <li> The amount of power points, which indicates how many times the move can execute before it
 *   can no longer be used.
 * <li> The accuracy of the move, which determines how likely it is to hit the target.
 * <li> The priority of the move, meaning it executes in battle before other moves of lower
 *   priority.
 * <li> Whether the move makes contact with the target. This is useful for other moves to know and
 *   alter their behavior, accordingly.
 * <li> The type of the move, which is used for determining STAB and whether it affects the
 *   opponent.
 * </ul>
 *
 * @author Jordan Harman
 */
sealed interface PokemonMove {
    val basePower: Int?
    val totalPowerPoints: Int
    val accuracy: Int
    val isContactMove: Boolean
    val type: PokemonType
    val category: Category
    val priority: Int

    fun getTurns(): Int = 1

    val execute: (BattleSceneState) -> MoveExecutionResult?
}

data object SuckerPunch : PokemonMove {
    override val basePower = 80
    override val totalPowerPoints = 5
    override val accuracy = 100
    override val isContactMove = true
    override val type = FIGHTING
    override val category = Category.PHYSICAL
    override val priority = 1

    override val execute = ::visitExecuteMove
}

data object Tackle : PokemonMove {
    override val basePower = 40
    override val totalPowerPoints = 25
    override val accuracy = 100
    override val isContactMove = true
    override val type = NORMAL
    override val category = Category.PHYSICAL
    override val priority = 0

    override val execute = ::visitExecuteMove
}
