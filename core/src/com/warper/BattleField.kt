package com.warper

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.*
import com.badlogic.gdx.graphics.g2d.Batch

import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g3d.Environment
import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController

class BattleField(bitmapFont: BitmapFont): Stage() {

    init {
        bitmapFont.data.setScale(1f)
    }
    private var perspectiveCamera = PerspectiveCamera(67f,
            Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
    private var orthographicCamera = OrthographicCamera(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
    var cameraInputController = CameraInputController(perspectiveCamera)
    private var player = Player(0f,0f, 0f,perspectiveCamera,orthographicCamera)
    private var modelBatch = ModelBatch()
    private var environment =  Environment()
    private var labelPlayerX
            = Label("X", 0f.toString(), bitmapFont,Gdx.graphics.width/2 - 100f,Gdx.graphics.height/2 - 30f)
    private var labelPlayerY
            = Label("Y", 0f.toString(), bitmapFont,Gdx.graphics.width/2 - 100f,Gdx.graphics.height/2 - 60f)
    private var labelPlayerZ
            = Label("Z", 0f.toString(), bitmapFont,Gdx.graphics.width/2 - 100f,Gdx.graphics.height/2 - 90f)

    //Texture loading
    private var backGroundTexture = Texture(Gdx.files.internal("background.png"))
    private var backGroundSprite = Sprite(backGroundTexture)
    init {
        backGroundSprite.setScale((Gdx.graphics.width/backGroundSprite.texture.width).toFloat(),
                (Gdx.graphics.height/backGroundSprite.texture.height).toFloat())
        backGroundSprite.setPosition(-Gdx.graphics.width/2f, -Gdx.graphics.height/2f)
    }
    init {
        /*to do camera initialization
        /camera position is synonymous with the player position */
        perspectiveCamera.near = 1f
        perspectiveCamera.far = 300f
        //environment
        val blueDirectionalLight = DirectionalLight()
        blueDirectionalLight.color.set(Color.BLUE)
        blueDirectionalLight.setDirection(0f,0f,-10f)
        environment.add(blueDirectionalLight)
        val purpleDirectionalLight = DirectionalLight()
        purpleDirectionalLight.color.set(Color.PURPLE)
        purpleDirectionalLight.setDirection(0f,0f,10f)
        environment.add(purpleDirectionalLight)
    }

    override fun draw(batch: Batch) {
        Gdx.gl.glViewport(0,0,Gdx.graphics.width,Gdx.graphics.height)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)
        Gdx.gl.glClearColor(0f,0f,0f,1f)
        //drawing info
        orthographicCamera.update()
        batch.projectionMatrix = orthographicCamera.combined
        batch.begin()
        batch.draw(backGroundTexture,-orthographicCamera.viewportWidth/2,-orthographicCamera.viewportHeight/2,
                orthographicCamera.viewportWidth,orthographicCamera.viewportHeight)
        drawLabels(batch)
        player.draw(batch)
        batch.end()
        handle_input()
        perspectiveCamera.update()
        modelBatch.begin(perspectiveCamera)
        player.draw3D(modelBatch,environment)
        modelBatch.end()

    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        return super.mouseMoved(screenX, screenY)
    }

    override fun keyDown(keycode: Int): Boolean {
        when(keycode){
            Input.Keys.UP -> {
                //perspectiveCamera.translate(0f,0f,20f)
            }
            Input.Keys.DOWN -> {
               // perspectiveCamera.translate(0f,0f,-20f)
            }
        }
        return false
    }
    fun handle_input() {
        if(Gdx.app.type == Application.ApplicationType.Android) {
            player.handleInputAndroid()
        } else if (Gdx.app.type == Application.ApplicationType.Desktop){
            player.handleInputDesktop()
        }
    }
    fun drawLabels(batch: Batch) {
        var playerPostionVector = player.getPosition()
        labelPlayerX.setValue(playerPostionVector.x.toString())
        labelPlayerY.setValue(playerPostionVector.y.toString())
        labelPlayerZ.setValue(playerPostionVector.z.toString())
        labelPlayerX.draw(batch)
        labelPlayerY.draw(batch)
        labelPlayerZ.draw(batch)
    }

}