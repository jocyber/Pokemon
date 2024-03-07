package pokemongame.animations

import com.lehaine.littlekt.graphics.g2d.TextureSlice
import com.lehaine.littlekt.math.Vec2f
import pokemongame.scene.battle.Turn

data class AnimationUpdate(
    val moveAnimationResponse: MoveAnimationResponse,
    val animationTurn: Turn,
    val effectFrame: TextureSlice? = null,
)

data class MoveAnimationResponse(
    val position: Vec2f,
    val scaleX: Float = 1f,
    val scaleY: Float = 1f,
)
