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
        runBlocking { context.resourcesVfs["assets/sprites/battle_box.jpg"].readTexture() }
    }

    single(named(BATTLE_BUTTONS)) {
        runBlocking { context.resourcesVfs["assets/sprites/battle_buttons.png"].readTexture() }
    }

    single(named(DIALOG_BOX)) {
        runBlocking { context.resourcesVfs["assets/sprites/dialog_box.png"].readTexture() }
    }
}

const val HEALTH_BAR_TEXTURE = "HealthTexture"
const val BATTLE_UI_BACKGROUND = "BattleUiBackground"
const val BATTLE_BUTTONS = "BattleButtons"
const val DIALOG_BOX = "DialogBox"
