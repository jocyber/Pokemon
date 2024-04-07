package pokemongame.scene.battle.control

import kotlin.time.Duration
import pokemongame.animations.PokemonAnimationPlayer
import pokemongame.moves.Tackle
import pokemongame.scene.battle.BattleEntity
import pokemongame.scene.battle.BattleScene

/**
 * A helper class for executing the attacking phase of a battle. It takes in details of the moves
 * that will be executed this round, determines who will go first, and executes them.
 */
class TurnExecutor(private val battleScene: BattleScene) {
    private enum class State {
        PREPARING_TO_ATTACK,
        ATTACKING,
        TAKING_DAMAGE,
        ENDING_TURN,
        ENDING_ROUND,
    }

    private var pokemonAnimationPlayer: PokemonAnimationPlayer? = null
    private var currentState = State.PREPARING_TO_ATTACK
    private var turn = BattleEntity.PLAYER
    private var damageExecutor: DamageExecutor? = null

    init {
        val (target, opposing) = getTargetsFromTurn(turn)

        battleScene.sceneState.apply {
            currentTarget = target
            opposingTarget = opposing
            turn = this@TurnExecutor.turn
        }
    }

    fun updateTurn(dt: Duration): Boolean {
        when (currentState) {
            State.PREPARING_TO_ATTACK -> return preparingToAttack()
            State.ATTACKING -> attacking(dt)
            State.TAKING_DAMAGE -> return takingDamage(dt)
            State.ENDING_TURN -> return endingTurn()
            State.ENDING_ROUND ->
                return true // run post round checks like weather, berries, abilities, status, etc
        }

        return false
    }

    private fun preparingToAttack(): Boolean {
        // TODO: check for status like sleep and paralysis
        with(battleScene.sceneState) {
            if (opposingTarget.isFainted || opposingTarget.isInvulnerable) {
                // if flinched, display "but it flinched"
                // else display "but it missed"
                println("But it missed")
                currentState = State.ENDING_TURN

                return@preparingToAttack false
            }
        }

        // check if it hits
        val doesHit = true

        if (doesHit) {
            val moveAnimation = Tackle

            // TODO: take into account multi-hit moves and multi-turn moves
            pokemonAnimationPlayer = moveAnimation.attackAnimation(battleScene.sceneState)
            currentState = State.ATTACKING
        } else {
            println("But it missed")
            currentState = State.ENDING_TURN
        }

        return false
    }

    private fun attacking(dt: Duration) {
        if (pokemonAnimationPlayer?.playAnimation(dt) == false) {
            return
        }

        damageExecutor =
            DamageExecutor(battleScene.sceneState, target = battleScene.sceneState.opposingTarget)
        currentState = State.TAKING_DAMAGE
        // check for recoil
        // if fainted, set state to fainting
    }

    private fun takingDamage(dt: Duration): Boolean {
        if (damageExecutor?.updateDamage(dt) == false) {
            return false
        }

        currentState = State.ENDING_TURN
        return true
    }

    private fun endingTurn(): Boolean {
        val (target, opposing) = getTargetsFromTurn(turn)

        target.turnPassed = true
        turn = if (turn == BattleEntity.PLAYER) BattleEntity.ENEMY else BattleEntity.PLAYER

        battleScene.sceneState.apply {
            currentTarget = opposing
            opposingTarget = target
            turn = this@TurnExecutor.turn
        }

        if (target.turnPassed && opposing.turnPassed) {
            battleScene.sceneState.apply {
                enemyState.turnPassed = false
                playerState.turnPassed = false
            }

            currentState = State.ENDING_ROUND
            return false
        }

        currentState = State.PREPARING_TO_ATTACK
        return false
    }

    private fun getTargetsFromTurn(turn: BattleEntity) =
        with(battleScene.sceneState) {
            if (turn == BattleEntity.PLAYER) Pair(playerState, enemyState)
            else Pair(enemyState, playerState)
        }
}
