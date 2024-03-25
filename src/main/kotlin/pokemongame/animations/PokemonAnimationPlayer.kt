package pokemongame.animations

import kotlin.time.Duration

private typealias MoveUpdates = Array<Array<PokemonAnimation>>

/** Class for playing an animation that involves movement. */
class PokemonAnimationPlayer(private val moveUpdates: MoveUpdates) {
    private var index = 0

    /**
     * @param dt The deltaTime for the animation frame.
     * @return Boolean Returns false on successful update, returns true when the animation is
     *   finished.
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
