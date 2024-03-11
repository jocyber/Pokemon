package pokemongame.guice

import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.google.inject.Singleton
import com.google.inject.name.Named
import com.lehaine.littlekt.Context
import com.lehaine.littlekt.file.vfs.readTexture
import com.lehaine.littlekt.graphics.Texture
import kotlinx.coroutines.runBlocking

class BattleUiModule(private val context: Context) : AbstractModule() {
    override fun configure() {}

    @Provides
    @Singleton
    @Named(HEALTH_BAR_TEXTURE)
    fun getHealthTexture(): Texture = runBlocking {
        context.resourcesVfs["assets/sprites/hp_bar.png"].readTexture()
    }
}
