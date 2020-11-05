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
import com.warper.gameobjects.Stargate
import com.warper.util.ModelFactory

class BattleField(bitmapFont: BitmapFont): Stage() {

    init {
        bitmapFont.data.setScale(1f)
    }
    private var perspectiveCamera = PerspectiveCamera(80f,
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
    private var cameraLabels: MutableList<Label> = mutableListOf()
            init{
              for(x in 0..2){
                  cameraLabels.add(Label(
                      when(x) {
                          0 ->  "camera X:"
                          1-> "camera Y:"
                          2-> "camera Z:"
                          else -> "NULL"
                      }
                  ,"k",bitmapFont,-orthographicCamera.viewportWidth/2 ,orthographicCamera.viewportHeight/2 - 30f*(x+1)))
              }
            }
    //Texture loading
    private var backGroundTexture = Texture(Gdx.files.internal("background.png"))
    private var backGroundSprite = Sprite(backGroundTexture)
    init {
        backGroundSprite.setScale((Gdx.graphics.width/backGroundSprite.texture.width).toFloat(),
                (Gdx.graphics.height/backGroundSprite.texture.height).toFloat())
        backGroundSprite.setPosition(-Gdx.graphics.width/2f, -Gdx.graphics.height/2f)
    }
    init {
        perspectiveCamera.near = 1f
        perspectiveCamera.far = 5000f
        //environment of spaceship
        val blueDirectionalLight = DirectionalLight()
        blueDirectionalLight.color.set(Color.BLUE)
        blueDirectionalLight.setDirection(0f,0f,-10f)
        environment.add(blueDirectionalLight)
        val purpleDirectionalLight = DirectionalLight()
        purpleDirectionalLight.color.set(Color.PURPLE)
        purpleDirectionalLight.setDirection(0f,0f,10f)
        environment.add(purpleDirectionalLight)
    }
    //
    private val starGates: MutableList<Stargate> = mutableListOf()
    init {
        for (i in 0..6){
            val x: Float = (1f-  2*Math.random().toFloat()) * 100f
            val y: Float = (1f-  2*Math.random().toFloat()) * 100f
            val z: Float = -this.player.getVelocity() * i * 2
            starGates.add(Stargate(x,y,z,ModelFactory.getStarGateInstance()))
        }
    }
    private val scoreLabel = Label("Score", 0.toString(), bitmapFont,0f, orthographicCamera.viewportHeight/2f -30f )

    override fun draw(batch: Batch) {
        Gdx.gl.glViewport(0,0,Gdx.graphics.width,Gdx.graphics.height)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)
        Gdx.gl.glClearColor(0f,0f,0f,1f)
        //2d Draws
        orthographicCamera.update()
        batch.projectionMatrix = orthographicCamera.combined
        batch.begin()
       batch.draw(backGroundTexture,-orthographicCamera.viewportWidth/2,-orthographicCamera.viewportHeight/2,
                orthographicCamera.viewportWidth,orthographicCamera.viewportHeight)

        drawLabels(batch)
        player.draw(batch)
        batch.end()
        //input -> logic -> drawing
        handleInput()
        logic()
        //3d Draws
        perspectiveCamera.update()
        modelBatch.begin(perspectiveCamera)
        player.draw3D(modelBatch,environment)
        for (stargate in starGates){
            stargate.draw3D(modelBatch,environment)
        }
        modelBatch.end()

    }

    //  TODO: HANDLE ANDROID PAN
    override fun pan(x: Float, y: Float, deltaX: Float, deltaY: Float): Boolean {
        this.player.direct(x, y)
        return true
    }

    /*
    override fun zoom(initialDistance: Float, distance: Float): Boolean {
        println("Zoom handled; Ratio:${distance/initialDistance}")
        val sensitivity = 0.3f
        if(distance/initialDistance > 1) {
            perspectiveCamera.fieldOfView = (perspectiveCamera.fieldOfView
                    + sensitivity * Gdx.graphics.deltaTime * perspectiveCamera.fieldOfView * (distance/initialDistance))
        } else {
            perspectiveCamera.fieldOfView = (perspectiveCamera.fieldOfView
                    - sensitivity * Gdx.graphics.deltaTime * perspectiveCamera.fieldOfView * (distance/initialDistance))
        }
        return true
    } */

    override fun touchDown(x: Float, y: Float, pointer: Int, button: Int): Boolean {
        this.player.direct(x,y)
        return true
    }

    override fun keyDown(keycode: Int): Boolean {
        when(keycode){
            Input.Keys.R -> {
                this.player.back(200f)
            }
        }
        return true
    }

    private fun handleInput() {
        if(Gdx.app.type == Application.ApplicationType.Android) {
            player.handleInputAndroid()
        } else if (Gdx.app.type == Application.ApplicationType.Desktop){
            player.handleInputDesktop()
        }
    }
    private fun logic(){
        this.player.handlePlayerLogic()
        for (i in 0 until starGates.size-1){
            if(perspectiveCamera.position.z <  starGates[i].getPosition().z ){
                if(starGates[i].isWithin(this.player.getPosition())){
                    scoreLabel.setValue((scoreLabel.getValue().toInt() + 1).toString())
                }
                starGates[i].setPosition((1f-  2*Math.random().toFloat()) * 50f,
                        (1f-  2*Math.random().toFloat()) * 50f,
                        -(40f * 20) * (1f + 2*Math.random().toFloat()) + perspectiveCamera.position.z )
            }
        }
    }

    private fun drawLabels(batch: Batch) {
        val playerPositionVector = player.getPosition()
        labelPlayerX.setValue(playerPositionVector.x.toString())
        labelPlayerY.setValue(playerPositionVector.y.toString())
        labelPlayerZ.setValue(playerPositionVector.z.toString())
        labelPlayerX.draw(batch)
        labelPlayerY.draw(batch)
        labelPlayerZ.draw(batch)
        for (x in 0..2){
            cameraLabels[x].setValue(when(x){
                0-> perspectiveCamera.position.x.toString()
                1-> perspectiveCamera.position.y.toString()
                2 -> perspectiveCamera.position.z.toString()
                else -> "0"
            })
            cameraLabels[x].draw(batch)
        }
        scoreLabel.draw(batch)

    }

    override fun dispose() {
        this.player.dispose()
        for(stargate in this.starGates ){
            stargate.dispose()
        }
        labelPlayerX.dispose()
        labelPlayerY.dispose()
        labelPlayerZ.dispose()
        for (label in this.cameraLabels){
            label.dispose()
        }
    }

    override fun longPress(x: Float, y: Float): Boolean {
        if(!player.getShouldMove()) {
            this.player.setVelocity(200f)
        }
        return true
    }

    override fun tap(x: Float, y: Float, count: Int, button: Int): Boolean {
        if(count >= 2) {
            if(player.getVelocity() == 40f){
                this.player.setVelocity(200f)
            }else if (player.getVelocity() == 200f) {
                this.player.setVelocity(40f)
            }
        }
        return true
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        player.setShouldMove(false)
        return true
    }

}
/**
 * Things that need to be improved:
 * - spaceship collision with stargate
 * Things that might be added:
 * - stargate shining and activation on close distance
 * - setting steering values and setting menu
 */
