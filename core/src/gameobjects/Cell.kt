package gameobjects

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import enums.EffectType
import enums.JewelType

class Cell(var isPlaying : Boolean, var jewel : Jewel, var tileTexture : TextureRegion) {

    fun draw(batch: Batch, x : Float, y : Float, size : Float, delta : Float) {
        if (isPlaying) {
            batch.draw(tileTexture, x, y, size, size)
            if (jewel.jewelType != JewelType.NO_JEWEL) {
                jewel.draw(batch, x, y, size, delta)
            } else if (jewel.effect == EffectType.BEING_DESTROYED) {
                jewel.draw(batch, x, y, size, delta)
            }
        }
    }

}