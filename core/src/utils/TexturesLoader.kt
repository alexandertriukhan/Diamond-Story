package utils

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.ParticleEffect
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion

class TexturesLoader {
    val textureAtlas = TextureAtlas(Gdx.files.internal("graphics/atlas.atlas"))

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
    val tileTop : TextureRegion = textureAtlas.findRegion("cells/tile_top")
    val tileTopLite : TextureRegion = textureAtlas.findRegion("cells/tile_top_lite")
    val tileDown : TextureRegion = textureAtlas.findRegion("cells/tile_down")
    val tileDownLite : TextureRegion = textureAtlas.findRegion("cells/tile_down_lite")
    val tileRight : TextureRegion = textureAtlas.findRegion("cells/tile_right")
    val tileRightLite : TextureRegion = textureAtlas.findRegion("cells/tile_right_lite")
    val tileLeft : TextureRegion = textureAtlas.findRegion("cells/tile_left")
    val tileLeftLite : TextureRegion = textureAtlas.findRegion("cells/tile_left_lite")
    val tileCornerLeftTop : TextureRegion = textureAtlas.findRegion("cells/tile_corner_left_top")
    val tileCornerLeftTopLite : TextureRegion = textureAtlas.findRegion("cells/tile_corner_left_top_lite")
    val tileCornerRightTop : TextureRegion = textureAtlas.findRegion("cells/tile_corner_right_top")
    val tileCornerRightTopLite : TextureRegion = textureAtlas.findRegion("cells/tile_corner_right_top_lite")
    val tileCornerLeftDown : TextureRegion = textureAtlas.findRegion("cells/tile_corner_left_down")
    val tileCornerLeftDownLite : TextureRegion = textureAtlas.findRegion("cells/tile_corner_left_down_lite")
    val tileCornerRightDown : TextureRegion = textureAtlas.findRegion("cells/tile_corner_right_down")
    val tileCornerRightDownLite : TextureRegion = textureAtlas.findRegion("cells/tile_corner_right_down_lite")
    val tileCornerTopThree : TextureRegion = textureAtlas.findRegion("cells/tile_corner_top_three")
    val tileCornerTopThreeLite : TextureRegion = textureAtlas.findRegion("cells/tile_corner_top_three")
    val tileCornerRightThree : TextureRegion = textureAtlas.findRegion("cells/tile_corner_right_three")
    val tileCornerRightThreeLite : TextureRegion = textureAtlas.findRegion("cells/tile_corner_right_three_lite")
    val tileCornerLeftThree : TextureRegion = textureAtlas.findRegion("cells/tile_corner_left_three")
    val tileCornerLeftThreeLite : TextureRegion = textureAtlas.findRegion("cells/tile_corner_left_three_lite")
    val tileCornerDownThree : TextureRegion = textureAtlas.findRegion("cells/tile_corner_down_three")
    val tileCornerDownThreeLite : TextureRegion = textureAtlas.findRegion("cells/tile_corner_down_three_lite")

    // ANIMATIONS:
    val fireAnimation = ParticleEffect()
    val selectedMoveSpell : TextureRegion = textureAtlas.findRegion("selected_move_spell")
    val selectedFireSpell : TextureRegion = textureAtlas.findRegion("selected_fire_spell")
    val crossAnim : TextureRegion = textureAtlas.findRegion("cross_anim")
    val superGemGlow : TextureRegion = textureAtlas.findRegion("super_gem_glow")
    val explosion : TextureRegion = textureAtlas.findRegion("explosion")

    init {
        fireAnimation.load(Gdx.files.internal("graphics/effects/fire.p"),textureAtlas)
    }

    private object Holder { val INSTANCE = TexturesLoader() }

    companion object {
        val instance: TexturesLoader by lazy { Holder.INSTANCE }
    }
}