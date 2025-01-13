package pokemongame.scene.battle

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.equals.shouldBeEqual
import pokemongame.pokemon.PokemonMove

open class MoveExecutionSharedTests(val moveUnderTest: PokemonMove) : FreeSpec({
    "move misses when the opponent is in the air" {
        // moveUnderTest.execute() shouldBeEqual MoveExecutionResult.Missed
    }
})
