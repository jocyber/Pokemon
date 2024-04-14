package pokemongame.scene.battle.ui

import com.lehaine.littlekt.graphics.Texture
import io.mockk.every
import net.jqwik.api.*
import net.jqwik.api.Combinators.combine
import net.jqwik.api.lifecycle.AfterProperty
import net.jqwik.api.lifecycle.BeforeProperty
import net.jqwik.api.state.ActionChain
import org.assertj.core.api.Assertions.assertThat
import org.koin.core.qualifier.named
import org.koin.test.KoinTest
import org.koin.test.mock.declareMock
import pokemongame.actions.battle.UpdateHealthAction
import pokemongame.koin.HEALTH_BAR_TEXTURE
import pokemongame.scene.battle.BattleEntity
import pokemongame.utils.deinitKoin
import pokemongame.utils.initKoin

class HealthBarTest : KoinTest {
    data class HealthValues(val totalHealth: Int, val startHealth: Int, val updatedHealth: Int)

    private lateinit var mockTexture: Texture

    @BeforeProperty
    fun init() {
        initKoin()

        mockTexture = declareMock<Texture>(named(HEALTH_BAR_TEXTURE))

        every { mockTexture.width } returns 6_272
        every { mockTexture.height } returns 77
    }

    @AfterProperty fun deinit() = deinitKoin()

    @Provide fun battleEntities() = Arbitraries.of(BattleEntity::class.java)

    @Provide
    fun healthValues() =
        combine(
                Arbitraries.integers().greaterOrEqual(1),
                Arbitraries.integers(),
                Arbitraries.integers(),
            )
            .filter { totalHealth, startingHealth, currentHealth ->
                startingHealth in 0..totalHealth && currentHealth in 0..totalHealth
            }
            .`as`(::HealthValues)

    @Provide
    fun healthBarUpdateActions(): Arbitrary<ActionChain<HealthBar>> {
        val arbitraryEntity = battleEntities()
        val arbitraryHealthValues = healthValues()

        return arbitraryEntity.flatMap { entity ->
            arbitraryHealthValues.flatMap { healthValues ->
                ActionChain.startWith {
                        with(healthValues) { HealthBar(startHealth, totalHealth, entity) }
                    }
                    .withAction(UpdateHealthAction())
            }
        }
    }

    @Property(seed = "-7416292700788290465")
    fun `updateHealth produces frame index between 0 and max frame index`(
        @ForAll("healthBarUpdateActions") healthUpdateChain: ActionChain<HealthBar>
    ) = healthUpdateChain.run()

    @Property(tries = 500, seed = "1335665846044844485")
    fun `current frame never 0 when current health is greater than 0`(
        @ForAll("battleEntities") battleEntity: BattleEntity,
        @ForAll("healthValues") healthValues: HealthValues,
    ) {
        Assume.that(with(healthValues) { startHealth > 0 && updatedHealth > 0 })

        val healthBar =
            with(healthValues) {
                HealthBar(
                    startHealth,
                    totalHealth,
                    battleEntity,
                )
            }

        healthBar.updateHealth(healthValues.updatedHealth)
        assertThat(healthBar.currentFrame).isNotEqualTo(0)
    }

    @Property(tries = 500, seed = "-4813740741812647548")
    fun `lower updatedHealth yields a lower, or equal, frame index`(
        @ForAll("battleEntities") battleEntity: BattleEntity,
        @ForAll("healthValues") healthValues: HealthValues,
    ) {
        Assume.that(with(healthValues) { updatedHealth < startHealth })

        val healthBar =
            with(healthValues) {
                HealthBar(
                    startHealth,
                    totalHealth,
                    battleEntity,
                )
            }

        val previousFrameIndex = healthBar.currentFrame
        healthBar.updateHealth(healthValues.updatedHealth)

        assertThat(healthBar.currentFrame).isLessThanOrEqualTo(previousFrameIndex)
    }

    @Property(tries = 500, seed = "6552245492343956169")
    fun `greater updatedHealth yields a higher, or equal, frame index`(
        @ForAll("battleEntities") battleEntity: BattleEntity,
        @ForAll("healthValues") healthValues: HealthValues,
    ) {
        Assume.that(with(healthValues) { updatedHealth > startHealth })

        val healthBar =
            with(healthValues) {
                HealthBar(
                    startHealth,
                    totalHealth,
                    battleEntity,
                )
            }

        val previousFrameIndex = healthBar.currentFrame
        healthBar.updateHealth(healthValues.updatedHealth)

        assertThat(healthBar.currentFrame).isGreaterThanOrEqualTo(previousFrameIndex)
    }

    @Property
    fun `total health minus itself yields zero health`(
        @ForAll("battleEntities") battleEntity: BattleEntity
    ) {
        val healthBar = HealthBar(100, 100, battleEntity)
        healthBar.updateHealth(0)

        assertThat(healthBar.currentFrame).isEqualTo(0)
    }

    @Property
    fun `zero health plus total health yields total health`(
        @ForAll("battleEntities") battleEntity: BattleEntity
    ) {
        val healthBar = HealthBar(0, 100, battleEntity)
        healthBar.updateHealth(100)

        assertThat(healthBar.currentFrame).isEqualTo(48)
    }
}
