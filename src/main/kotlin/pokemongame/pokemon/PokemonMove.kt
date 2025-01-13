package pokemongame.pokemon

import pokemongame.pokemon.PokemonType.*
import pokemongame.scene.battle.BattleSceneState
import pokemongame.scene.battle.MoveExecutionResult
import pokemongame.scene.battle.visitExecuteMove

enum class Category {
    PHYSICAL,
    SPECIAL,
    STATUS,
}

interface PokemonMove {
    val basePower: BasePower?
    val totalPowerPoints: PowerPoints
    val accuracy: Accuracy?
    val isContactMove: Boolean
    val type: PokemonType
    val category: Category
    val priority: Priority

    val turns: Int
        get() = 1

    val execute: (BattleSceneState) -> MoveExecutionResult
}

data object DefenseCurl : PokemonMove {
    override val basePower = null
    override val totalPowerPoints = PowerPoints(40)
    override val accuracy = null
    override val isContactMove = false
    override val type = NORMAL
    override val category = Category.STATUS
    override val priority = Priority(0)

    override val execute = ::visitExecuteMove
}

data object SuckerPunch : PokemonMove {
    override val basePower = BasePower(80)
    override val totalPowerPoints = PowerPoints(5)
    override val accuracy = Accuracy(100)
    override val isContactMove = true
    override val type = DARK
    override val category = Category.PHYSICAL
    override val priority = Priority(1)

    override val execute = ::visitExecuteMove
}

data object Tackle : PokemonMove {
    override val basePower = BasePower(40)
    override val totalPowerPoints = PowerPoints(25)
    override val accuracy = Accuracy(100)
    override val isContactMove = true
    override val type = NORMAL
    override val category = Category.PHYSICAL
    override val priority = Priority(0)

    override val execute = ::visitExecuteMove
}
