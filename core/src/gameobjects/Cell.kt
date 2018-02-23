package gameobjects

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import enums.JewelType
import utils.TexturesLoader

class Cell(var isPlaying : Boolean, var jewel : Jewel, var tileTexture : TextureRegion) {

    var isTop = false
    var isBottom = false
    var isRight = false
    var isLeft = false

    fun drawTile(batch: Batch, x : Float, y : Float, size : Float) {
        if (isPlaying) {
            batch.draw(tileTexture, x, y, size, size)
        }
        if (isTop) {
            batch.draw(TexturesLoader.instance.oneBorder, x, y + size, size, size )
        }
        if (isBottom) {
            batch.draw(TexturesLoader.instance.oneBorder, x, y - size, size / 2f, size / 2f, size, size,
                    1f, 1f, 180f)
        }
    }

    fun drawJewel(batch: Batch, x : Float, y : Float, size : Float, delta : Float) {
        if (isPlaying) {
            if (jewel.jewelType != JewelType.NO_JEWEL) {
                jewel.draw(batch, x, y, size, delta)
            }
        }
    }

}