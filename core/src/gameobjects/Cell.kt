package gameobjects

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import enums.JewelType
import utils.TexturesLoader

class Cell(var isPlaying : Boolean, var jewel : Jewel, var tileTexture : TextureRegion) {

    private val borderSizeDel = 14

    var isTop = false
    var isBottom = false
    var isRight = false
    var isLeft = false
    var isTopEdge = false
    var isBottomEdge = false

    fun drawTile(batch: Batch, x : Float, y : Float, size : Float) {
        val borderSize = size / borderSizeDel
        if (isPlaying) {
            batch.draw(tileTexture,x,y,size,size)
        }
        batch.end()
        TexturesLoader.instance.border.begin(ShapeRenderer.ShapeType.Filled)
        if (isTop) {
            if (!isTopEdge) {
                TexturesLoader.instance.border.rect(x, y + size, size, borderSize)
            } else {
                TexturesLoader.instance.border.rect(x - borderSize, y + size, size + borderSize * 2, borderSize)
            }
        }
        if (isBottom) {
            if (!isBottomEdge) {
                TexturesLoader.instance.border.rect(x, y - borderSize, size, borderSize)
            } else {
                TexturesLoader.instance.border.rect(x - borderSize, y - borderSize, size + borderSize * 2, borderSize)
            }
        }
        if (isRight) {
            TexturesLoader.instance.border.rect(x + size,y,borderSize,size)
        }
        if (isLeft) {
            TexturesLoader.instance.border.rect(x - borderSize,y,borderSize,size)
        }
        TexturesLoader.instance.border.end()
        batch.begin()
    }

    fun drawJewel(batch: Batch, x : Float, y : Float, size : Float, delta : Float) {
        if (isPlaying) {
            if (jewel.jewelType != JewelType.NO_JEWEL) {
                jewel.draw(batch, x, y, size, delta)
            }
        }
    }

    fun isBlocked() : Boolean {  // TODO: returns true for cells that arent allowed to be moved, ex: chained jewels
        if (!isPlaying) {
            return true
        }
        return false
    }

}