package pokemongame.scene.battle.control

import com.lehaine.littlekt.Context
import com.lehaine.littlekt.graphics.Texture
import com.lehaine.littlekt.graphics.g2d.use
import com.lehaine.littlekt.input.Key
import com.lehaine.littlekt.math.Vec2f
import kotlin.time.Duration
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent.inject
import pokemongame.koin.DIALOG_BOX
import pokemongame.scene.SceneDrawer
import pokemongame.scene.SceneState
import pokemongame.scene.battle.BattleAction
import pokemongame.scene.battle.BattleAction.Companion.BUTTONS_BY_LABEL
import pokemongame.scene.battle.INPUT_BACKGROUND_HEIGHT
import pokemongame.scene.battle.INPUT_HEIGHT
import pokemongame.scene.battle.SCREEN_WIDTH

class BattleActionSelector(
    private val sceneState: SceneState,
    private val context: Context,
) : SceneDrawer() {
    private val positionsByLabel: Map<BattleAction, Vec2f>

    init {
        require(sceneState.availableActions.size == 4)

        positionsByLabel = with(sceneState) {
            mapOf(
                availableActions[0] to TOP_LEFT,
                availableActions[1] to TOP_RIGHT,
                availableActions[2] to BOTTOM_LEFT,
                availableActions[3] to BOTTOM_RIGHT,
            )
        }
    }

    override fun draw(dt: Duration) {
        handleSelector()

        SPRITE_BATCH.use {
            it.draw(
                DIALOG_BOX_TEXTURE,
                x = HORIZONTAL_SPACING,
                y = TOP_MOST_Y - 75f,
                width = INPUT_BOX_WIDTH,
                height = INPUT_HEIGHT,
                flipY = true,
            )

            for (label in sceneState.availableActions) {
                val buttonPair = BUTTONS_BY_LABEL[label]!!
                val position = positionsByLabel[label]!!

                it.draw(
                    slice = if (label == sceneState.selectedAction) buttonPair.second else buttonPair.first,
                    x = position.x,
                    y = position.y,
                    width = ACTION_BUTTON_WIDTH,
                    height = ACTION_BUTTON_HEIGHT,
                    flipY = true,
                )
            }
        }
    }

    // move logic to its own file
    @Suppress("CyclomaticComplexMethod")
    private fun handleSelector() =
        with (sceneState) {
            when {
                context.input.isKeyPressed(Key.W) ->
                    if (selectedAction == availableActions[2]) {
                        selectedAction = availableActions[0]
                    } else if (selectedAction == availableActions[3]) {
                        selectedAction = availableActions[1]
                    }
                context.input.isKeyPressed(Key.S) ->
                    if (selectedAction == availableActions[0]) {
                        selectedAction = availableActions[2]
                    } else if (selectedAction == availableActions[1]) {
                        selectedAction = availableActions[3]
                    }
                context.input.isKeyPressed(Key.A) ->
                    if (selectedAction == availableActions[1]) {
                        selectedAction = availableActions[0]
                    } else if (selectedAction == availableActions[3]) {
                        selectedAction = availableActions[2]
                    }
                context.input.isKeyPressed(Key.D) ->
                    if (selectedAction == availableActions[0]) {
                        selectedAction = availableActions[1]
                    } else if (selectedAction == availableActions[2]) {
                        selectedAction = availableActions[3]
                    }
            }
        }

    companion object {
        private const val HORIZONTAL_SPACING = 10f
        private const val INPUT_BOX_WIDTH = 500f

        private val ACTION_BUTTON_HEIGHT = INPUT_HEIGHT / 2f
        private const val ACTION_BUTTON_WIDTH = (SCREEN_WIDTH - INPUT_BOX_WIDTH - HORIZONTAL_SPACING * 3) / 2f

        private const val LEFT_MOST_X = HORIZONTAL_SPACING * 2 + INPUT_BOX_WIDTH
        private const val TOP_MOST_Y = -244f

        private val TOP_LEFT = Vec2f(LEFT_MOST_X, TOP_MOST_Y)
        private val TOP_RIGHT = Vec2f(LEFT_MOST_X + ACTION_BUTTON_WIDTH, TOP_MOST_Y)
        private val BOTTOM_LEFT = Vec2f(LEFT_MOST_X, TOP_MOST_Y - ACTION_BUTTON_HEIGHT)
        private val BOTTOM_RIGHT = Vec2f(
            LEFT_MOST_X + ACTION_BUTTON_WIDTH,
            TOP_MOST_Y - ACTION_BUTTON_HEIGHT
        )

        private val DIALOG_BOX_TEXTURE: Texture by inject(Texture::class.java, named(DIALOG_BOX))
    }
}
