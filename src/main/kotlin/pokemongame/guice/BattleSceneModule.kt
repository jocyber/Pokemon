package pokemongame.guice

import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.google.inject.Singleton
import com.google.inject.name.Named
import com.lehaine.littlekt.Context
import com.lehaine.littlekt.file.vfs.readTexture
import com.lehaine.littlekt.graphics.Texture
import kotlinx.coroutines.runBlocking

class BattleSceneModule(private val context: Context) : AbstractModule() {
    override fun configure() {}

    @Provides
    @Singleton
    @Named(ENEMY_HEALTH_BAR_TEXTURE)
    fun getEnemyHealthTexture(): Texture = runBlocking {
        context.resourcesVfs["assets/sprites/enemyHealth.png"].readTexture()
    }

    @Provides
    @Singleton
    @Named(PLAYER_HEALTH_BAR_TEXTURE)
    fun getPlayerHealthTexture(): Texture = runBlocking {
        context.resourcesVfs["assets/sprites/playerHealth.png"].readTexture()
    }
}
