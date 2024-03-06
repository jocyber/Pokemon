package pokemongame.scene

import net.jqwik.api.*
import net.jqwik.api.Combinators.combine

class BattleSceneControllerTest {
    @Property(tries = 10) fun `some stuff is done`() {}

    @Provide
    fun stuff2(): Arbitrary<String> {
        val f = Arbitraries.strings()
        val g = Arbitraries.strings()

        return combine(listOf(f, g)).`as` { values: List<String> -> values[0] + values[1] }
    }
}
