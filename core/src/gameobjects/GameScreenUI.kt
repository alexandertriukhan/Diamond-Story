package gameobjects

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import utils.GameScreenAssets
import utils.MyScreenUtils

class GameScreenUI(private val assets: GameScreenAssets, private val gameGrid: GameGrid) {

    // TODO: check why circle of different size on phone and on desktop
    private val screenUtils = MyScreenUtils()

    private var colWidth  = Gdx.graphics.width.toFloat() * 0.4f
    private var colHeight = Gdx.graphics.height.toFloat() / 9f
    private var widthAddition = screenUtils.initScreenWidth / 10f
    private val movesCircleRadius = (Gdx.graphics.width.toFloat() - (colWidth * 2)) / 2  // height = width
    private val moveCircleCenter = Vector2(colWidth + movesCircleRadius,Gdx.graphics.height.toFloat() - movesCircleRadius)

    private val shape = ShapeRenderer()
    private val decreasePercent : Float = 360f / gameGrid.movesLeft.toFloat()
    private var progressArcColor = Color.GREEN

    private val buttonSize = (movesCircleRadius * 2) / 1.5f
    private val menuButtonPosition = Vector2(Gdx.graphics.width - colHeight * 1.2f,Gdx.graphics.height - colHeight)

    fun draw(batch: SpriteBatch) {
        drawUIBars(batch)
        batch.end()
        drawProgressArc()
        batch.begin()
        drawTopBarMoves(batch)
        drawTopBarButtons(batch)
        drawObjectives(batch)
        drawScore(batch)
        drawBottomBarButtons(batch)
    }

    private fun drawUIBars(batch: SpriteBatch) {
        batch.draw(assets.uiBar, screenUtils.initXOffset - (widthAddition / 2f),Gdx.graphics.height.toFloat() - colHeight,(screenUtils.initScreenWidth + widthAddition),colHeight)
        batch.draw(assets.uiBar, screenUtils.initXOffset - (widthAddition / 2f),0f, (screenUtils.initScreenWidth + widthAddition) / 2f,
                colHeight / 2f,screenUtils.initScreenWidth + widthAddition,colHeight,1f,1f,180f)
    }

    private fun drawTopBarButtons(batch: SpriteBatch) {
        batch.draw(assets.menuButton,menuButtonPosition.x,menuButtonPosition.y,buttonSize,buttonSize)
    }

    private fun drawBottomBarButtons(batch: SpriteBatch) {
        // TODO: implement
    }

    private fun drawObjectives(batch: SpriteBatch) {
        // TODO: implement
    }

    private fun drawScore(batch: SpriteBatch) {
        // TODO: implement
    }

    private fun drawTopBarMoves(batch: SpriteBatch) {
        // TODO: use screenUtils, remove hardCode
        batch.draw(assets.movesCircle,colWidth + 5f,(Gdx.graphics.height.toFloat() - movesCircleRadius * 2) + 5f,(movesCircleRadius * 2) - 10f,
                (movesCircleRadius * 2) - 10f)
        assets.fontScore.color = Color.WHITE
        val fontLayout = GlyphLayout(assets.fontScore,gameGrid.movesLeft.toString())
        assets.fontScore.draw(batch,fontLayout,moveCircleCenter.x - (fontLayout.width / 2),moveCircleCenter.y + (fontLayout.height / 2))
    }

    private fun drawProgressArc() {
        shape.begin(ShapeRenderer.ShapeType.Filled)
        shape.color = Color.BLACK
        shape.circle(moveCircleCenter.x,moveCircleCenter.y,movesCircleRadius + 5f)
        shape.color = progressArcColor
        shape.arc(moveCircleCenter.x,moveCircleCenter.y,movesCircleRadius, 90f,gameGrid.movesLeft * decreasePercent)
        shape.end()
    }

    fun resize(width : Int, height : Int) {
        colWidth = width.toFloat() * 0.4f
        colHeight = height.toFloat() / 11f
    }

}