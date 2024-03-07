package pokemongame.animations.movements

import com.lehaine.littlekt.math.Vec2f
import com.lehaine.littlekt.util.seconds
import kotlin.time.Duration
import pokemongame.animations.MoveAnimationResponse

data class Glide(
    private val startingPoint: Vec2f,
    private val endingPoint: Vec2f,
    private val seconds: Float,
) {
    private val deltaX = (endingPoint.x - startingPoint.x) / seconds
    private val deltaY = (endingPoint.y - startingPoint.y) / seconds
    private val currentPoint = startingPoint.toMutableVec2()
    private val totalDistance = startingPoint.distance(endingPoint)

    private var distanceTraveled = 0f

    fun update(dt: Duration): MoveAnimationResponse {
        currentPoint.x += deltaX * dt.seconds
        currentPoint.y += deltaY * dt.seconds

        distanceTraveled = startingPoint.distance(currentPoint)

        return MoveAnimationResponse(
            position = if (!isDone()) currentPoint.toVec2() else endingPoint,
        )
    }

    fun isDone(): Boolean {
        return distanceTraveled >= totalDistance
    }
}
