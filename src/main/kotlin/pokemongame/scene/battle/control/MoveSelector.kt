package pokemongame.scene.battle.control

import com.lehaine.littlekt.graphics.Texture
import com.lehaine.littlekt.graphics.g2d.use
import com.lehaine.littlekt.math.Vec2f
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent.inject
import pokemongame.koin.DISPLAY_BOX
import pokemongame.scene.battle.SCREEN_WIDTH
import kotlin.time.Duration
import pokemongame.scene.SceneDrawer
import pokemongame.scene.battle.BattleSceneState
import pokemongame.scene.battle.INPUT_HEIGHT
import pokemongame.types.PokemonType
import pokemongame.types.PokemonType.Companion.BUTTONS_BY_TYPE
import pokemongame.types.PokemonType.Companion.BUTTON_HEIGHT
import pokemongame.types.PokemonType.Companion.BUTTON_WIDTH

class MoveSelector(private val sceneState: BattleSceneState) : SceneDrawer() {
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
                    width = MOVE_BUTTON_WIDTH,
                    height = MOVE_BUTTON_HEIGHT,
                    flipY = true,
                )
            }

            it.draw(
                DISPLAY_BOX_TEXTURE,
                x = DISPLAY_BOX_START_X,
                y = -319f,
                width = INFO_BOX_WIDTH,
                height = INPUT_HEIGHT,
                flipY = true,
            )
        }
    }

    companion object {
        private val DISPLAY_BOX_TEXTURE: Texture by inject(Texture::class.java, named(DISPLAY_BOX))

        private const val HORIZONTAL_SPACING = 10f
        private const val INFO_BOX_WIDTH = 250f

        private const val MOVE_BUTTON_WIDTH = (SCREEN_WIDTH - HORIZONTAL_SPACING * 3 - INFO_BOX_WIDTH) / 2f
        private val MOVE_BUTTON_HEIGHT = INPUT_HEIGHT / 2f

        private const val LEFT_MOST_X = HORIZONTAL_SPACING
        private const val TOP_MOST_Y = -244f

        private const val DISPLAY_BOX_START_X = HORIZONTAL_SPACING * 2 + MOVE_BUTTON_WIDTH * 2

        private val TOP_LEFT = Vec2f(LEFT_MOST_X, TOP_MOST_Y)
        private val TOP_RIGHT = Vec2f(LEFT_MOST_X + MOVE_BUTTON_WIDTH, TOP_MOST_Y)
        private val BOTTOM_LEFT = Vec2f(LEFT_MOST_X, TOP_MOST_Y - MOVE_BUTTON_HEIGHT)
        private val BOTTOM_RIGHT = Vec2f(
            LEFT_MOST_X + MOVE_BUTTON_WIDTH,
            TOP_MOST_Y - MOVE_BUTTON_HEIGHT
        )

        private val POSITIONS = listOf(TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT)
    }
}
