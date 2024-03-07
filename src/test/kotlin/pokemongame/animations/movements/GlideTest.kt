package pokemongame.animations.movements

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
import org.assertj.core.api.Assertions.assertThat
import pokemongame.animations.MoveAnimationResponse

@PropertyDefaults(edgeCases = EdgeCasesMode.FIRST)
class GlideTest {
    @Provide
    fun points(): Arbitrary<Vec2f> =
        combine(Arbitraries.floats(), Arbitraries.floats()).`as`(::Vec2f)

    @Property(tries = 1500)
    fun `actual end position is equal to expected end position`(
        @ForAll("points") startingPoint: Vec2f,
        @ForAll("points") endingPoint: Vec2f,
    ) {
        val glideResponses = getGlideResponses(startingPoint, endingPoint)

        assertThat(glideResponses.last().position).isEqualTo(endingPoint)
    }

    @Property
    fun inverse(
        @ForAll("points") startingPoint: Vec2f,
        @ForAll("points") endingPoint: Vec2f,
    ) {
        val glideResponses = getGlideResponses(startingPoint, endingPoint)
        val inverseGlideResponses = getGlideResponses(endingPoint, startingPoint)

        assertThat(glideResponses.last().position).isEqualTo(endingPoint)
        assertThat(inverseGlideResponses.last().position).isEqualTo(startingPoint)
    }

    @Property
    fun `distance always increases`(
        @ForAll("points") startingPoint: Vec2f,
        @ForAll("points") endingPoint: Vec2f,
    ) {
        Assume.that(startingPoint != endingPoint)

        val glideResponses = getGlideResponses(startingPoint, endingPoint)

        for (i in 1 ..< glideResponses.size) {
            assertThat(glideResponses[i - 1].position.distance(glideResponses[i].position))
                .isGreaterThan(0f)
        }
    }

    // @Property fun `summed duration is equal to total seconds`() {}

    private fun getGlideResponses(start: Vec2f, end: Vec2f): List<MoveAnimationResponse> {
        val glide = Glide(start, end, seconds = 1f)
        val glideResponses = mutableListOf<MoveAnimationResponse>()

        do {
            glideResponses.add(glide.update(0.14f.seconds))
        } while (!glide.isDone())

        return glideResponses
    }
}
