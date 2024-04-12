package pokemongame.moves

import pokemongame.animations.PokemonAnimationPlayer
import pokemongame.scene.battle.BattleEntity
import pokemongame.scene.battle.BattleSceneState
import pokemongame.types.PokemonType

/**
 * An interface for any move that a Pokemon could potentially learn.
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
    val basePower: Int
    val totalPowerPoints: Int
    val accuracy: Int
    val priority: Int
    val isContactMove: Boolean
    val type: PokemonType

    /**
     * A method that provides the caller with the animation that is to be executed.
     *
     * <p>
     * Moves will contain side effects which may include drawing content to the screen, updating the
     * state of the battle scene, or animating battle entities. For the latter case, the position
     * and size of the entity should always return to its original state before the move was
     * executed.
     *
     * @param battleSceneState the state of the battle upon calling this move, which can alter its
     *   behavior
     * @return PokemonAnimationPlayer an object that plays an animation that was built from a move
     * @see BattleEntity
     */
    fun attackAnimation(battleSceneState: BattleSceneState): PokemonAnimationPlayer
}
