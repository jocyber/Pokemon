package pokemongame.koin

import com.lehaine.littlekt.Context
import com.lehaine.littlekt.file.vfs.readTexture
import com.lehaine.littlekt.graphics.g2d.SpriteBatch
import kotlinx.coroutines.runBlocking
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun battleSceneModule(context: Context) = module {
    single { SpriteBatch(context) }

    single(named(HEALTH_BAR_TEXTURE)) {
        runBlocking { context.resourcesVfs["assets/sprites/hp_bar.png"].readTexture() }
    }

    single(named(BATTLE_UI_BACKGROUND)) {
        runBlocking { context.resourcesVfs["assets/text_boxes/black_text_box.png"].readTexture() }
    }
}

const val HEALTH_BAR_TEXTURE = "HealthTexture"
const val BATTLE_UI_BACKGROUND = "BattleUiBackground"
