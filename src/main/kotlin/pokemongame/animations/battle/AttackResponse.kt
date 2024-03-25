package pokemongame.animations.battle

import pokemongame.animations.PokemonAnimation
import pokemongame.animations.base.Flicker
import pokemongame.scene.battle.BattleSceneState
import pokemongame.scene.battle.PokemonBattleState

fun attackResponse(
    target: PokemonBattleState,
    battleSceneState: BattleSceneState
): Array<PokemonAnimation> {
    val damage = 10
    target.currentHealth =
        if (target.currentHealth - damage >= 0) target.currentHealth - damage else 0

    return arrayOf(Flicker(target))
}

fun calculateDamage(
    level: Int,
    basePower: Int,
    stab: Float,
    targetAttack: Int,
    opposingDefense: Int,
    burn: Int,
    weather: Float,
    screen: Float,
    item: Float,
    typeEffectiveness: Float,
    superEffectiveness: Float,
): Int {
    val x = targetAttack.toFloat() / opposingDefense.toFloat()
    val y = 2f * level.toFloat() / 5f
    val z = (y + 2f) * basePower.toFloat() * x / 50f

    return ((z * burn * screen * weather + 2f) *
            item *
            stab *
            typeEffectiveness *
            superEffectiveness)
        .toInt()
}
