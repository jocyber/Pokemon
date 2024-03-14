package pokemongame.animations.movements

import com.lehaine.littlekt.math.Vec2f
import com.lehaine.littlekt.util.seconds
import kotlin.time.Duration
import pokemongame.scene.battle.PokemonBattleState

/** A utility class for gliding a Pokemon during a battle scene. */
class Glide(
    private val startingPoint: Vec2f,
    private val endingPoint: Vec2f,
    seconds: Float,
    // TODO: Change this to a positionable super type
    private val pokemonBattleState: PokemonBattleState,
) : MoveAnimation {
    private val deltaX: Float
    private val deltaY: Float
    private val currentPoint = startingPoint.toMutableVec2()
    private val totalDistance = startingPoint.distance(endingPoint)

    private var distanceTraveled = 0f

    init {
        if (seconds == 0f) {
            throw IllegalArgumentException("Seconds for a glide animation cannot be zero")
        }

        deltaX = (endingPoint.x - startingPoint.x) / seconds
        deltaY = (endingPoint.y - startingPoint.y) / seconds
    }

    /**
     * A function that updates the position of the Pokemon. This function mutates the original
     * Pokemon state.
     *
     * @param dt The deltaTime since the last frame.
     */
    override fun update(dt: Duration) {
        currentPoint.x += deltaX * dt.seconds
        currentPoint.y += deltaY * dt.seconds

        distanceTraveled = startingPoint.distance(currentPoint)

        pokemonBattleState.position = if (!isDone()) currentPoint else endingPoint
    }

    override fun isDone(): Boolean {
        return distanceTraveled >= totalDistance
    }
}
