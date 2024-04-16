package pokemongame.scene.battle.control

import com.lehaine.littlekt.graphics.Texture
import com.lehaine.littlekt.graphics.g2d.use
import com.lehaine.littlekt.math.Vec2f
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent.inject
import pokemongame.koin.DIALOG_BOX
import pokemongame.koin.DISPLAY_BOX
import pokemongame.scene.SCREEN_WIDTH
import kotlin.time.Duration
import pokemongame.scene.SceneDrawer
import pokemongame.scene.battle.BattleSceneState
import pokemongame.types.PokemonType
import pokemongame.types.PokemonType.Companion.BUTTONS_BY_TYPE
import pokemongame.types.PokemonType.Companion.BUTTON_HEIGHT
import pokemongame.types.PokemonType.Companion.BUTTON_WIDTH

class MoveSelector(
    private val sceneState: BattleSceneState,
) : SceneDrawer() {
    private var selectedMove = "Tackle"

    override fun draw(dt: Duration) {
        SPRITE_BATCH.use {
            for (indexedMove in sceneState.playerState.stats.moves.withIndex()) {
                val buttonPair = BUTTONS_BY_TYPE[indexedMove.value?.type ?: PokemonType.UNKNOWN]!!
                val position = POSITIONS[indexedMove.index]

                it.draw(
                    slice = if (indexedMove.value.toString() == selectedMove) buttonPair.second else buttonPair.first,
                    x = position.x,
                    y = position.y,
                    width = BUTTON_WIDTH.toFloat(),
                    height = BUTTON_HEIGHT.toFloat(),
                    scaleX = BUTTON_SCALE_WIDTH,
                    scaleY = BUTTON_SCALE_HEIGHT,
                    flipY = true,
                )
            }

            it.draw(
                DISPLAY_BOX_TEXTURE,
                x = DISPLAY_BOX_START_X,
                y = -286f,
                width = SCREEN_WIDTH - DISPLAY_BOX_START_X - LEFT_MOST_X * 2 + LEFT_MOST_X,
                height = BUTTON_HEIGHT * BUTTON_SCALE_HEIGHT * 2,
                flipY = true,
            )
        }
    }

    companion object {
        private val DISPLAY_BOX_TEXTURE: Texture by inject(Texture::class.java, named(DISPLAY_BOX))

        private const val BUTTON_SCALE_WIDTH = 1.80f
        private const val BUTTON_SCALE_HEIGHT = 1.40f

        private const val LEFT_MOST_X = 20f
        private const val TOP_MOST_Y = -222f

        private const val DISPLAY_BOX_START_X = LEFT_MOST_X + BUTTON_WIDTH * BUTTON_SCALE_WIDTH * 2 + LEFT_MOST_X

        private val TOP_LEFT = Vec2f(LEFT_MOST_X, TOP_MOST_Y)
        private val TOP_RIGHT = Vec2f(LEFT_MOST_X + BUTTON_WIDTH * BUTTON_SCALE_WIDTH, TOP_MOST_Y)
        private val BOTTOM_LEFT = Vec2f(LEFT_MOST_X, TOP_MOST_Y - BUTTON_HEIGHT * BUTTON_SCALE_HEIGHT)
        private val BOTTOM_RIGHT = Vec2f(
            LEFT_MOST_X + BUTTON_WIDTH * BUTTON_SCALE_WIDTH,
            TOP_MOST_Y - BUTTON_HEIGHT * BUTTON_SCALE_HEIGHT
        )

        private val POSITIONS = listOf(TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT)
    }
}
