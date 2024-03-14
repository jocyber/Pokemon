package pokemongame.scene.battle.control

import com.google.inject.Injector
import kotlin.time.Duration
import pokemongame.animations.MoveAnimationPlayer
import pokemongame.moves.Tackle
import pokemongame.scene.battle.BattleSceneState
import pokemongame.scene.battle.PokemonBattleState
import pokemongame.scene.battle.Turn

/**
 * A helper class for executing the attacking phase of a battle. It takes in details of the moves
 * that will be executed this round, determines who will go first, and executes them. This class
 * will handle most of the logic associated with all the variables that goes into attacking a
 * Pokemon.
 */
class TurnExecutor(
    private var turn: Turn,
    private val battleState: BattleSceneState,
    private val injector: Injector,
) {
    private enum class State {
        PREPARING_TO_ATTACK,
        ATTACKING,
        ENDING_TURN,
        ENDING_ROUND,
    }

    private var moveAnimationPlayer: MoveAnimationPlayer? = null
    private var currentState: State = State.PREPARING_TO_ATTACK

    fun updateTurn(dt: Duration): Boolean {
        return when (currentState) {
            State.PREPARING_TO_ATTACK -> preparingToAttack()
            State.ATTACKING -> attacking(dt)
            State.ENDING_TURN -> endingTurn()
            State.ENDING_ROUND -> endingRound()
        }
    }

    private fun preparingToAttack(): Boolean {
        // check for status like sleep and paralysis
        val (target, opposing) = getTargetsFromTurn(turn)

        if (opposing.isFainted || opposing.isInvulnerable) {
            // if flinched, display "but it flinched"
            // else display "but it missed"
            println("But it missed")
            target.turnPassed = true
            currentState = State.ENDING_TURN

            return false
        }

        // check if it hits
        val doesHit = true

        if (doesHit) {
            val moveAnimation = injector.getInstance(Tackle::class.java)

            // take into account multi-hit moves and multi-turn moves
            moveAnimationPlayer = moveAnimation.attackAnimation(battleState)
            currentState = State.ATTACKING
        } else {
            println("But it missed")
            currentState = State.ENDING_TURN
            target.turnPassed = true
        }

        return false
    }

    private fun attacking(dt: Duration): Boolean {
        if (moveAnimationPlayer?.playAnimation(dt) == false) {
            return false
        }

        // check for recoil

        // if fainted, set state to fainting
        return false
    }

    private fun endingTurn(): Boolean {
        val (target, opposing) = getTargetsFromTurn(turn)

        if (target.turnPassed && opposing.turnPassed) {
            battleState.enemyState.turnPassed = false
            battleState.playerState.turnPassed = false

            currentState = State.ENDING_ROUND
            return true
        }

        turn = if (turn == Turn.PLAYER) Turn.ENEMY else Turn.PLAYER
        currentState = State.PREPARING_TO_ATTACK

        return false
    }

    private fun endingRound(): Boolean {
        // run post round checks like weather, berries, abilities, status, etc
        return true
    }

    private fun getTargetsFromTurn(turn: Turn): Pair<PokemonBattleState, PokemonBattleState> {
        return if (turn == Turn.PLAYER) Pair(battleState.playerState, battleState.enemyState)
        else Pair(battleState.enemyState, battleState.playerState)
    }
}
