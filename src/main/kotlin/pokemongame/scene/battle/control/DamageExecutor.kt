package pokemongame.scene.battle.control

import kotlin.time.Duration
import pokemongame.animations.PokemonAnimationPlayer
import pokemongame.animations.base.Flicker
import pokemongame.scene.battle.BattleSceneState
import pokemongame.scene.battle.PokemonBattleState

/**
 * A class for playing all animations related to taking damage and fainting. It can handle cases
 * that involve a Pokemon hitting another (flicker animation will be played) or updating health
 * without hitting like poison, recoil, etc.
 *
 * By default, the damage dealt will be calculated using the standard damage calculator, but clients
 * may override this for moves that deal a set amount of damage or just calculate it in a different
 * way.
 */
class DamageExecutor(
    private val battleSceneState: BattleSceneState,
    private val target: PokemonBattleState,
    private val damage: Int? = null,
) {
    private val damageAnimationPlayer = getDamageAnimation()

    fun updateDamage(dt: Duration): Boolean {
        return damageAnimationPlayer.playAnimation(dt)
    }

    private fun getDamageAnimation() =
        with(target) {
            stats.apply { currentHealth -= damage ?: calculateDamage() }

            PokemonAnimationPlayer(
                arrayOf(
                    if (wasHit) arrayOf(Flicker(this)) else arrayOf(),
                    arrayOf(healthBar.updateHealth(stats.currentHealth))
                )
            )
        }

    // take in the move that's being used
    // should really be tested on its own, so move to a new class
    private fun calculateDamage() =
        with(battleSceneState) {
            val level = currentTarget.stats.level
            val basePower = 10
            val stab = 1f
            val attack = 40
            val opposingDefense = 20
            val burn = if (currentTarget.isBurned) 0.5f else 1f
            val weather = 1f
            val screen = 1f
            val item = 1f
            val typeEffectiveness = 1f
            val superEffectiveness = 1f

            val x = attack / opposingDefense
            val y = 2f * level / 5
            val z = (y + 2) * basePower * x / 50

            (z * burn * screen * weather +
                    2f * item * stab * typeEffectiveness * superEffectiveness)
                .toInt()
        }
}
