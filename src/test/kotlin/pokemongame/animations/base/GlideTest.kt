package pokemongame.animations.base

import com.lehaine.littlekt.math.Vec2f
import com.lehaine.littlekt.util.seconds
import net.jqwik.api.Arbitraries
import net.jqwik.api.Arbitrary
import net.jqwik.api.Assume
import net.jqwik.api.Combinators.combine
import net.jqwik.api.EdgeCasesMode
import net.jqwik.api.ForAll
import net.jqwik.api.Property
import net.jqwik.api.PropertyDefaults
import net.jqwik.api.Provide
import net.jqwik.api.lifecycle.AfterProperty
import net.jqwik.api.lifecycle.BeforeProperty
import org.assertj.core.api.Assertions.assertThat
import pokemongame.pokemon.Primeape
import pokemongame.pokemon.state.PokemonStats
import pokemongame.scene.battle.PokemonBattleState
import pokemongame.utils.deinitKoin
import pokemongame.utils.initKoin

@PropertyDefaults(edgeCases = EdgeCasesMode.FIRST)
class GlideTest {
    @Provide
    fun points(): Arbitrary<Vec2f> =
        combine(Arbitraries.floats(), Arbitraries.floats()).`as`(::Vec2f)

    @Provide
    fun battleStates(): Arbitrary<PokemonBattleState> =
        points().map { position ->
            PokemonBattleState(
                position = position,
                stats = PokemonStats(level = 1, currentHealth = 1, pokemon = Primeape)
            )
        }

    @BeforeProperty fun init() = initKoin()

    @AfterProperty fun deinit() = deinitKoin()

    @Property(tries = 1500, seed = "-6218027441402253276")
    fun `actual end position is equal to expected end position`(
        @ForAll("points") endingPoint: Vec2f,
        @ForAll("battleStates") battleState: PokemonBattleState,
    ) {
        val glide = Glide(battleState.position, endingPoint, seconds = 1f, battleState)

        while (!glide.isDone()) {
            glide.update(0.1f.seconds)
        }

        assertThat(battleState.position).isEqualTo(endingPoint)
    }

    @Property(seed = "-1775495748590307115")
    fun inverse(
        @ForAll("points") endingPoint: Vec2f,
        @ForAll("battleStates") battleState: PokemonBattleState,
    ) {
        val startingPoint = battleState.position.toVec2()

        val glide = Glide(startingPoint, endingPoint, seconds = 1f, battleState)

        while (!glide.isDone()) {
            glide.update(0.1f.seconds)
        }

        assertThat(battleState.position).isEqualTo(endingPoint)

        val glide2 = Glide(battleState.position, startingPoint, seconds = 1f, battleState)

        while (!glide2.isDone()) {
            glide2.update(0.1f.seconds)
        }

        assertThat(battleState.position).isEqualTo(startingPoint)
    }

    @Property(seed = "-1752989200589077294")
    fun `distance from end position always decreases`(
        @ForAll("points") endingPoint: Vec2f,
        @ForAll("battleStates") battleState: PokemonBattleState,
    ) {
        Assume.that(battleState.position != endingPoint)

        val glide = Glide(battleState.position, endingPoint, seconds = 1f, battleState)

        while (!glide.isDone()) {
            val lastPoint = battleState.position.toVec2()
            glide.update(0.1f.seconds)

            assertThat(battleState.position.distance(endingPoint))
                .isLessThan(lastPoint.distance(endingPoint))
        }
    }
}
