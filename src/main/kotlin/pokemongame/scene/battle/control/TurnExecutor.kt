package pokemongame.scene.battle.control

import kotlin.time.Duration
import pokemongame.animations.PokemonAnimationPlayer
import pokemongame.moves.Tackle
import pokemongame.scene.battle.BattleEntity
import pokemongame.scene.battle.BattleScene
import pokemongame.scene.battle.PokemonBattleState

/**
 * A helper class for executing the attacking phase of a battle. It takes in details of the moves
 * that will be executed this round, determines who will go first, and executes them. This class
 * will handle most of the logic associated with all the variables that goes into attacking a
 * Pokemon.
 */
class TurnExecutor(
    private var turn: BattleEntity,
    private val battleScene: BattleScene,
) {
    private enum class State {
        PREPARING_TO_ATTACK,
        ATTACKING,
        ENDING_TURN,
        UPDATING_HEALTH,
        ENDING_ROUND,
    }

    private var pokemonAnimationPlayer: PokemonAnimationPlayer? = null
    private var currentState: State = State.PREPARING_TO_ATTACK
    private var healthUpdater: PokemonAnimationPlayer? = null

    fun updateTurn(dt: Duration): Boolean {
        return when (currentState) {
            State.PREPARING_TO_ATTACK -> preparingToAttack()
            State.ATTACKING -> attacking(dt)
            State.UPDATING_HEALTH -> updatingHealth(dt)
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
            val moveAnimation = Tackle

            // take into account multi-hit moves and multi-turn moves
            pokemonAnimationPlayer = moveAnimation.attackAnimation(battleScene.sceneState)
            currentState = State.ATTACKING
        } else {
            println("But it missed")
            currentState = State.ENDING_TURN
            target.turnPassed = true
        }

        return false
    }

    private fun attacking(dt: Duration): Boolean {
        if (pokemonAnimationPlayer?.playAnimation(dt) == false) {
            return false
        }

        currentState = State.UPDATING_HEALTH
        healthUpdater =
            PokemonAnimationPlayer(
                arrayOf(
                    arrayOf(
                        battleScene.playerHealthBar.updateHealth(
                            battleScene.sceneState.playerState.currentHealth
                        )
                    )
                )
            )
        // check for recoil

        // if fainted, set state to fainting
        // currentState = State.ENDING_TURN
        return false
    }

    private fun updatingHealth(dt: Duration): Boolean {
        if (healthUpdater?.playAnimation(dt) == false) {
            return false
        }

        return false
    }

    private fun endingTurn(): Boolean {
        val (target, opposing) = getTargetsFromTurn(turn)

        if (target.turnPassed && opposing.turnPassed) {
            battleScene.sceneState.enemyState.turnPassed = false
            battleScene.sceneState.playerState.turnPassed = false

            currentState = State.ENDING_ROUND
            return true
        }

        turn = if (turn == BattleEntity.PLAYER) BattleEntity.ENEMY else BattleEntity.PLAYER
        currentState = State.PREPARING_TO_ATTACK

        return false
    }

    private fun endingRound(): Boolean {
        // run post round checks like weather, berries, abilities, status, etc
        return true
    }

    private fun getTargetsFromTurn(
        turn: BattleEntity
    ): Pair<PokemonBattleState, PokemonBattleState> {
        return if (turn == BattleEntity.PLAYER)
            Pair(battleScene.sceneState.playerState, battleScene.sceneState.enemyState)
        else Pair(battleScene.sceneState.enemyState, battleScene.sceneState.playerState)
    }
}
