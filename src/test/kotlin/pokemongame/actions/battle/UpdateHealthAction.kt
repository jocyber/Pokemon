package pokemongame.actions.battle

import net.jqwik.api.Arbitraries
import net.jqwik.api.Arbitrary
import net.jqwik.api.state.Action
import net.jqwik.api.state.Transformer
import org.assertj.core.api.Assertions.assertThat
import pokemongame.scene.battle.ui.HealthBar

class UpdateHealthAction : Action.Dependent<HealthBar> {
    override fun transformer(initialState: HealthBar): Arbitrary<Transformer<HealthBar>> {
        val arbitraryHealth =
            Arbitraries.integers().filter { health -> health in 0..(initialState.totalHealth) }

        return arbitraryHealth.map { health ->
            Transformer.mutate(
                "updateHealth($health)",
                { healthBar ->
                    healthBar.updateHealth(health)
                    assertThat(healthBar.currentFrame in 0..(HealthBar.MAX_FRAME_INDEX.toInt()))
                }
            )
        }
    }
}
