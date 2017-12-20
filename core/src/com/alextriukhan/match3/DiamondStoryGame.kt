package com.alextriukhan.match3;

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import screens.GameScreen
import utils.MyStack


class DiamondStoryGame : ApplicationAdapter() {

	private val assetManager = AssetManager()
	private val screenStack = MyStack<Screen>()
	
	override fun create () {
		setResourcesToBeLoaded()
		screenStack.push(GameScreen())
	}

	override fun render () {
		screenStack.peek()?.render(Gdx.graphics.deltaTime)
	}

	fun removeScreen() {
		screenStack.pop()?.dispose()
	}

	fun replaceScreen(screen : Screen) {
		screenStack.pop()?.dispose()
		screenStack.push(screen)
	}

	fun pushScreen(screen : Screen) {
		screenStack.push(screen)
	}

	private fun setResourcesToBeLoaded() {
		assetManager.apply {
			load("graphics/atlas.atlas", TextureAtlas::class.java)
		}
	}
}
