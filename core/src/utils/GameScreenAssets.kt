package utils

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.ParticleEffect
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer


class GameScreenAssets(assets : AssetManager) {
    private val textureAtlas : TextureAtlas = assets.get("graphics/atlas.atlas", TextureAtlas::class.java)

    // GEMS:
    val redGem : TextureRegion = textureAtlas.findRegion("gems/red_gem")
    val greenGem : TextureRegion = textureAtlas.findRegion("gems/green_gem")
    val blueGem : TextureRegion = textureAtlas.findRegion("gems/blue_gem")
    val purpleGem : TextureRegion = textureAtlas.findRegion("gems/purple_gem")
    val yellowGem : TextureRegion = textureAtlas.findRegion("gems/yellow_gem")
    val superGem : TextureRegion = textureAtlas.findRegion("gems/super_gem")
    val noGem : TextureRegion = textureAtlas.findRegion("gems/no_gem")

    // CELLS:
    val tileBlank : TextureRegion = textureAtlas.findRegion("cells/tile_blank")
    val tileBlankLite : TextureRegion = textureAtlas.findRegion("cells/tile_blank_lite")
    val border = ShapeRenderer()
    val selected : TextureRegion = textureAtlas.findRegion("selected")

    // ANIMATIONS:
    val fireAnimation : ParticleEffect = assets.get("graphics/effects/fire.p", ParticleEffect::class.java)
    val redExplosion : ParticleEffect = assets.get("graphics/effects/explosion_red.p",ParticleEffect::class.java)
    val blueExplosion : ParticleEffect = assets.get("graphics/effects/explosion_blue.p",ParticleEffect::class.java)
    val greenExplosion : ParticleEffect = assets.get("graphics/effects/explosion_green.p",ParticleEffect::class.java)
    val yellowExplosion : ParticleEffect = assets.get("graphics/effects/explosion_yellow.p",ParticleEffect::class.java)
    val purpleExplosion : ParticleEffect = assets.get("graphics/effects/explosion_purple.p",ParticleEffect::class.java)
    val crossAnimation : ParticleEffect = assets.get("graphics/effects/cross.p",ParticleEffect::class.java)
    //val selectedMoveSpell : TextureRegion = textureAtlas.findRegion("selected_move_spell")
    //val selectedFireSpell : TextureRegion = textureAtlas.findRegion("selected_fire_spell")
    val crossAnim : TextureRegion = textureAtlas.findRegion("cross_anim")
    val superGemGlow : TextureRegion = textureAtlas.findRegion("super_gem_glow")

    // ANIMATIONS SCALE:
    var animScaleFactor = 1f

    // INTERFACE
    val movesCircle : TextureRegion = textureAtlas.findRegion("moves_circle")

    // FONT
    val fontScore : BitmapFont = assets.get("fonts/JollyLodger-Regular.ttf", BitmapFont::class.java)  // 32 size

    // COLORS
    val myPurpleColor = Color(162/255f,57/255f,202/255f,1f)

    // SIZES
    val borderWidth = (Gdx.graphics.width.toFloat() / 8f) / 12f

    // OBJECTS
    val arc = Arc()

    init {
        animScaleFactor = Gdx.graphics.width.toFloat() / 520f // 520f is a reference width
        fireAnimation.scaleEffect(animScaleFactor)
        redExplosion.scaleEffect(animScaleFactor)
        blueExplosion.scaleEffect(animScaleFactor)
        greenExplosion.scaleEffect(animScaleFactor)
        yellowExplosion.scaleEffect(animScaleFactor)
        purpleExplosion.scaleEffect(animScaleFactor)
        crossAnimation.scaleEffect(animScaleFactor)
        border.color = myPurpleColor
    }

}