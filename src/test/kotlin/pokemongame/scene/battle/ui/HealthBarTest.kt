package pokemongame.scene.battle.ui

import com.lehaine.littlekt.Context
import com.lehaine.littlekt.graphics.Texture
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkClass
import net.jqwik.api.*
import net.jqwik.api.Combinators.combine
import net.jqwik.api.lifecycle.AfterProperty
import net.jqwik.api.lifecycle.BeforeProperty
import org.assertj.core.api.Assertions.assertThat
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.qualifier.named
import org.koin.test.KoinTest
import org.koin.test.mock.MockProvider
import org.koin.test.mock.declareMock
import pokemongame.koin.HEALTH_BAR_TEXTURE
import pokemongame.koin.battleSceneModule
import pokemongame.scene.battle.BattleEntity

class HealthBarTest : KoinTest {
    data class HealthValues(val totalHealth: Int, val startHealth: Int, val updatedHealth: Int)

    private lateinit var mockContext: Context
    private lateinit var mockTexture: Texture

    @BeforeProperty
    fun init() {
        MockProvider.register { clazz -> mockkClass(clazz) }
        mockContext = mockk<Context>()

        startKoin { modules(battleSceneModule(mockContext)) }

        mockTexture = declareMock<Texture>(named(HEALTH_BAR_TEXTURE))

        every { mockTexture.width } returns 6_272
        every { mockTexture.height } returns 77
    }

    @AfterProperty fun destroy() = stopKoin()

    @Provide fun battleEntities() = Arbitraries.of(BattleEntity::class.java)

    @Provide
    fun healthValueArbitraries() =
        combine(
                Arbitraries.integers().greaterOrEqual(1),
                Arbitraries.integers(),
                Arbitraries.integers(),
            )
            .filter { totalHealth, startingHealth, currentHealth ->
                startingHealth in 0..totalHealth && currentHealth in 0..totalHealth
            }
            .`as`(::HealthValues)

    @Property(tries = 500, seed = "1335665846044844485")
    fun `current frame never 0 when current health is greater than 0`(
        @ForAll("battleEntities") battleEntity: BattleEntity,
        @ForAll("healthValueArbitraries") healthValues: HealthValues,
    ) {
        Assume.that(with(healthValues) { startHealth > 0 && updatedHealth > 0 })

        val healthBar =
            with(healthValues) {
                HealthBar(
                    startHealth,
                    battleEntity,
                    totalHealth,
                )
            }

        healthBar.updateHealth(healthValues.updatedHealth)
        assertThat(healthBar.currentFrame).isNotEqualTo(0)
    }

    @Property(tries = 500, seed = "-768161245481325847")
    fun inverse(
        @ForAll("battleEntities") battleEntity: BattleEntity,
        @ForAll("healthValueArbitraries") healthValues: HealthValues,
    ) {
        val healthBar =
            with(healthValues) {
                HealthBar(
                    startHealth,
                    battleEntity,
                    totalHealth,
                )
            }

        val healthBeforeUpdate = healthBar.currentHealth
        val frameBeforeUpdate = healthBar.currentFrame

        healthBar.updateHealth(healthValues.updatedHealth)

        val healthDifference = healthBeforeUpdate - healthBar.currentHealth
        healthBar.updateHealth(healthBar.currentHealth + healthDifference)

        assertThat(frameBeforeUpdate).isEqualTo(healthBar.currentFrame)
    }

    @Property(tries = 500, seed = "-4813740741812647548")
    fun `lower updatedHealth yields a lower, or equal, frame index`(
        @ForAll("battleEntities") battleEntity: BattleEntity,
        @ForAll("healthValueArbitraries") healthValues: HealthValues,
    ) {
        Assume.that(with(healthValues) { updatedHealth < startHealth })

        val healthBar =
            with(healthValues) {
                HealthBar(
                    startHealth,
                    battleEntity,
                    totalHealth,
                )
            }

        val previousFrameIndex = healthBar.currentFrame
        healthBar.updateHealth(healthValues.updatedHealth)

        assertThat(healthBar.currentFrame).isLessThanOrEqualTo(previousFrameIndex)
    }

    @Property(tries = 500, seed = "6552245492343956169")
    fun `greater updatedHealth yields a higher, or equal, frame index`(
        @ForAll("battleEntities") battleEntity: BattleEntity,
        @ForAll("healthValueArbitraries") healthValues: HealthValues,
    ) {
        Assume.that(with(healthValues) { updatedHealth > startHealth })

        val healthBar =
            with(healthValues) {
                HealthBar(
                    startHealth,
                    battleEntity,
                    totalHealth,
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
        val healthBar = HealthBar(100, battleEntity, 100)
        healthBar.updateHealth(0)

        assertThat(healthBar.currentFrame).isEqualTo(0)
    }

    @Property
    fun `zero health plus total health yields total health`(
        @ForAll("battleEntities") battleEntity: BattleEntity
    ) {
        val healthBar = HealthBar(0, battleEntity, 100)
        healthBar.updateHealth(100)

        assertThat(healthBar.currentFrame).isEqualTo(48)
    }
}
