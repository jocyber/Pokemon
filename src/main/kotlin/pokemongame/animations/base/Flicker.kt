package pokemongame.animations.base

import com.lehaine.littlekt.graphics.Color
import com.lehaine.littlekt.graphics.toFloatBits
import com.lehaine.littlekt.util.milliseconds
import kotlin.time.Duration
import pokemongame.animations.PokemonAnimation
import pokemongame.scene.battle.PokemonBattleState

class Flicker(private val target: PokemonBattleState) : PokemonAnimation {
    private enum class FlickerStage {
        FULL_OPAQUE,
        HALF_TRANSPARENT,
        FULL_BLACK,
        FULL_BLACK_AND_HALF_TRANSPARENT,
        FULL_TRANSPARENT,
        END_STAGE,
    }

    private var stage = FlickerStage.HALF_TRANSPARENT
    private var deltaTime = 0f
    private var repeats = 0

    override fun update(dt: Duration) {
        deltaTime += dt.milliseconds

        if (deltaTime >= SWITCH_TIME) {
            when (stage) {
                FlickerStage.FULL_OPAQUE -> {
                    target.colorBits = Color.WHITE.withAlpha(1f).toFloatBits()
                    repeats++

                    stage =
                        if (repeats == MAX_REPEATS) FlickerStage.END_STAGE
                        else FlickerStage.HALF_TRANSPARENT
                }
                FlickerStage.HALF_TRANSPARENT -> {
                    target.colorBits = Color.WHITE.withAlpha(0.5f).toFloatBits()
                    stage = FlickerStage.FULL_BLACK
                }
                FlickerStage.FULL_BLACK -> {
                    target.colorBits = Color.BLACK.toFloatBits()
                    stage = FlickerStage.FULL_BLACK_AND_HALF_TRANSPARENT
                }
                FlickerStage.FULL_BLACK_AND_HALF_TRANSPARENT -> {
                    target.colorBits = Color.BLACK.withAlpha(0.5f).toFloatBits()
                    stage = FlickerStage.FULL_TRANSPARENT
                }
                FlickerStage.FULL_TRANSPARENT -> {
                    target.colorBits = Color.WHITE.withAlpha(0f).toFloatBits()
                    stage = FlickerStage.FULL_OPAQUE
                }
                FlickerStage.END_STAGE -> {}
            }

            deltaTime -= SWITCH_TIME
        }
    }

    override fun isDone(): Boolean = stage == FlickerStage.END_STAGE && repeats == MAX_REPEATS

    private companion object {
        val TOTAL_SECONDS = 500f.milliseconds
        const val TOTAL_STEPS = 15
        val SWITCH_TIME = TOTAL_SECONDS.div(TOTAL_STEPS).milliseconds

        const val MAX_REPEATS = 3
    }
}
