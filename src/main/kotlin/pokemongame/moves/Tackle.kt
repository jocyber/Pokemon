package pokemongame.moves

import com.lehaine.littlekt.graphics.g2d.Animation
import pokemongame.scene.battle.BattleSceneState
import pokemongame.types.PokemonType

data object Tackle : PokemonMove {
    override val basePower = 40
    override val totalPowerPoints = 25
    override val accuracy = 100
    override val priority = 0
    override val isContactMove = true
    override val type = PokemonType.NORMAL

    override fun attackAnimation(battleSceneState: BattleSceneState): Animation<*> {

    }
}