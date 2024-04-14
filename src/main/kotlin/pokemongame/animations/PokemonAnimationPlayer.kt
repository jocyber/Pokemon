package pokemongame.animations

import kotlin.time.Duration

private typealias MoveUpdates = Array<Array<PokemonAnimation>>

/**
 * A class for playing a sequence of {@link PokemonAnimation}'s.
 *
 * @author Jordan Harman
 */
class PokemonAnimationPlayer(private val moveUpdates: MoveUpdates) {
    private var index = 0

    /**
     * A function that plays all the Pokemon animations in the order which they were specified. Upon
     * each call, the function will update each animation of the inner list it is processing at the
     * moment. If all animations are done, it will move to the next animation sequence.
     *
     * @param dt the time since the last frame
     * @return true when the animation is finished, false otherwise
     * @see PokemonAnimation
     */
    fun playAnimation(dt: Duration): Boolean {
        if (index == moveUpdates.size) {
            return true
        }

        val areDone =
            moveUpdates[index]
                .map {
                    it.update(dt)
                    it.isDone()
                }
                .reduce { acc, isDone -> acc && isDone }

        if (areDone) {
            index++
        }

        return false
    }
}
