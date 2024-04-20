package pokemongame.moves

import pokemongame.animations.PokemonAnimationPlayer
import pokemongame.scene.battle.BattleSceneState
import pokemongame.types.PokemonType

data object KarateChop : PokemonMove {
    override val basePower = 50
    override val totalPowerPoints = 25
    override val accuracy = 100
    override val priority = 0
    override val isContactMove = true
    override val type = PokemonType.FIGHTING

    override fun attackAnimation(battleSceneState: BattleSceneState): PokemonAnimationPlayer {
        TODO("Not yet implemented")
    }
}