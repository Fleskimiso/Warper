package com.warper

import com.badlogic.gdx.Files
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g3d.Environment
import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.math.collision.BoundingBox
import com.badlogic.gdx.utils.UBJsonReader
import com.warper.interfaces.Drawable

class Player( x: Float, y: Float, z: Float,private val camera: PerspectiveCamera,
var orthographicCamera: OrthographicCamera): Drawable {

    private var uBJsonReader = UBJsonReader()
    private var g3dModelLoader = G3dModelLoader(uBJsonReader)
    private var model = g3dModelLoader.loadModel(Gdx.files.getFileHandle("Spaceship.g3db",
    Files.FileType.Internal))
    private var boundingbox = BoundingBox()
    var modelInstance = ModelInstance(model, x, y, z)
    init {
        boundingbox = modelInstance.calculateBoundingBox(boundingbox)
        camera.position.set(0f, 0f, 0f)
        camera.lookAt(0f,0f,-10f)
        modelInstance.transform.translate(0f,-10f,-10f)
        modelInstance.transform.rotate(Vector3(0f,10f,0f),180f)
    }
    private var focusVector3 = Vector3(0f,0f,-10f)

    //draws nothing
    override fun draw(batch: Batch) {

    }
    fun draw3D( modelBatch: ModelBatch, enviroment: Environment) {
        modelBatch.render(modelInstance, enviroment)
    }
    fun handleInputDesktop() {
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            val translationVector = Vector3(0f,0f,-30f*Gdx.graphics.deltaTime)
            this.camera.translate(translationVector)
            this.modelInstance.transform.translate(translationVector.scl(-1f))
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)) {
            val translationVector = Vector3(0f, 0f, 30f * Gdx.graphics.deltaTime)
            this.camera.translate(translationVector)
            this.modelInstance.transform.translate(translationVector.scl(-1f))
        }

    }
    fun handleInputAndroid() {

        if(Gdx.input.isTouched){
            val touch2dCoordinates = orthographicCamera.unproject(Vector3(
                    Gdx.input.x.toFloat(),Gdx.input.y.toFloat(),0f
            ))
            if(touch2dCoordinates.x > orthographicCamera.viewportWidth/4 ){
                this.camera.translate(10f*Gdx.graphics.deltaTime,0f,0f)
                this.modelInstance.transform.translate(10f*Gdx.graphics.deltaTime,0f,0f)
                this.setFocus(Vector3(10f*Gdx.graphics.deltaTime,0f,0f))
            }else if(touch2dCoordinates.x < -orthographicCamera.viewportWidth/4) {
                this.camera.translate(-10f*Gdx.graphics.deltaTime,0f,0f)
                this.modelInstance.transform.translate(-10f*Gdx.graphics.deltaTime,0f,0f)
                this.setFocus(Vector3(-10f*Gdx.graphics.deltaTime,0f,0f))
            }
            if(touch2dCoordinates.y > orthographicCamera.viewportHeight/4){
                this.camera.translate(0f,10f*Gdx.graphics.deltaTime,0f)
                this.modelInstance.transform.translate(0f,10f*Gdx.graphics.deltaTime,0f)
                this.setFocus(Vector3(0f,10f*Gdx.graphics.deltaTime,0f))
            } else if (touch2dCoordinates.y < -orthographicCamera.viewportHeight/4){
                this.camera.translate(0f,-10f*Gdx.graphics.deltaTime,0f)
                this.modelInstance.transform.translate(0f,-10f*Gdx.graphics.deltaTime,0f)
                this.setFocus(Vector3(0f,-10f*Gdx.graphics.deltaTime,0f))
            }
        }
    }
    fun getPosition(): Vector3 {
        return this.modelInstance.transform.getTranslation(Vector3(0f,0f,0f))
    }
    fun setFocus(position: Vector3) {
        this.focusVector3 = position
        this.camera.lookAt(focusVector3)
    }
    fun handlePlayerLogic() {
        val translationVector = Vector3(0f,0f,-50f*Gdx.graphics.deltaTime)
        camera.translate(translationVector)
        modelInstance.transform.translate(translationVector.scl(-1f))
    }


 }