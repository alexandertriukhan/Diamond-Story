package utils

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.scenes.scene2d.ui.Skin

class GlobalAssets(assets: AssetManager) {

    val skin = assets.get("skin/glassy-ui.json", Skin::class.java)

}