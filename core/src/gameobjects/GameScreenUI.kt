package gameobjects

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextField
import enums.BonusType
import utils.GameScreenAssets
import utils.MyScreenUtils

class GameScreenUI(private val assets: GameScreenAssets, private val gameGrid: GameGrid, private val bonuses: Map<BonusType,Int>) {

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
    private val menuButtonPosition = Vector2(Gdx.graphics.width - colHeight,Gdx.graphics.height - colHeight)

    private val font = BitmapFont()

    init {
        assert(bonuses.size < 5,{ "Max number of bonuses is 4!!!" })
    }

    fun draw(batch: SpriteBatch) {
        drawUIBars(batch)
        batch.end()
        drawProgressArc()
        batch.begin()
        drawTopBarMoves(batch)
        drawTopBarButtons(batch)
        drawBottomBarButtons(batch)
        drawScore(batch)
        drawObjectives(batch)
    }

    private fun drawUIBars(batch: SpriteBatch) {
        batch.draw(assets.uiBar, screenUtils.initXOffset - (widthAddition / 2f),Gdx.graphics.height.toFloat() - colHeight,(screenUtils.initScreenWidth + widthAddition),colHeight)
        batch.draw(assets.uiBar, screenUtils.initXOffset - (widthAddition / 2f),0f, (screenUtils.initScreenWidth + widthAddition) / 2f,
                colHeight / 2f,screenUtils.initScreenWidth + widthAddition,colHeight,1f,1f,180f)
    }

    private fun drawTopBarButtons(batch: SpriteBatch) {
        batch.draw(assets.menuButton,menuButtonPosition.x,menuButtonPosition.y,buttonSize,buttonSize)
    }

    // TODO: draw count
    private fun drawBottomBarButtons(batch: SpriteBatch) {
        val bonusPosition = Vector2(0f,0f)
        val columnWidth = Gdx.graphics.width / bonuses.size
        var counter = 0
        for (bonus in bonuses) {
            counter++
            var heightDivider = 4
            if (bonuses.size == 3) {
                if (counter == 2)
                    heightDivider = 2
            }
            if (bonuses.size == 4) {
                if (counter == 2 || counter == 3)
                    heightDivider = 2
            }
            bonusPosition.set(((columnWidth * counter) - columnWidth / 2) - buttonSize / 2, colHeight / heightDivider)
            when (bonus.key) {
                BonusType.HAMMER -> {
                    batch.draw(assets.menuButton,bonusPosition.x,bonusPosition.y,buttonSize,buttonSize)
                    font.draw(batch,bonus.value.toString(),(bonusPosition.x + buttonSize) - buttonSize / 8,bonusPosition.y + buttonSize / 6)
                }
                BonusType.MASH -> {
                    batch.draw(assets.menuButton,bonusPosition.x,bonusPosition.y,buttonSize,buttonSize)
                    font.draw(batch,bonus.value.toString(),(bonusPosition.x + buttonSize) - buttonSize / 8,bonusPosition.y + buttonSize / 6)
                }
                BonusType.BOMB -> {
                    batch.draw(assets.menuButton,bonusPosition.x,bonusPosition.y,buttonSize,buttonSize)
                    font.draw(batch,bonus.value.toString(),(bonusPosition.x + buttonSize) - buttonSize / 8,bonusPosition.y + buttonSize / 6)
                }
                BonusType.COLOR_REMOVE -> {
                    batch.draw(assets.menuButton,bonusPosition.x,bonusPosition.y,buttonSize,buttonSize)
                    font.draw(batch,bonus.value.toString(),(bonusPosition.x + buttonSize) - buttonSize / 8,bonusPosition.y + buttonSize / 6)
                }
            }
        }
    }

    private fun drawObjectives(batch: SpriteBatch) {
        // TODO: implement
    }

    private fun drawScore(batch: SpriteBatch) {
        // TODO: remove hardcode
        font.draw(batch,gameGrid.score.toInt().toString(),menuButtonPosition.x - 50,menuButtonPosition.y + 25)
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
    }

}