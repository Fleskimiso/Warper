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
import com.warper.interfaces.Drawable3D

class Player( x: Float, y: Float, z: Float,private val camera: PerspectiveCamera,
private val orthographicCamera: OrthographicCamera): Drawable, Drawable3D {

    private var uBJsonReader = UBJsonReader()
    private var g3dModelLoader = G3dModelLoader(uBJsonReader)
    private var model = g3dModelLoader.loadModel(Gdx.files.getFileHandle("Spaceship.g3db",
    Files.FileType.Internal))
    private var boundingBox = BoundingBox()
    private var modelInstance = ModelInstance(model, x, y, z)
    init {
        boundingBox = modelInstance.calculateBoundingBox(boundingBox)
        camera.position.set(0f, 0f, 0f)
        camera.lookAt(0f,0f,-10f)
        modelInstance.transform.translate(0f,-10f,-10f)
        modelInstance.transform.rotate(Vector3(0f,10f,0f),180f)
    }
    private var focusVector3 = Vector3(0f,0f,-10f)

    //draws nothing
    override fun draw(batch: Batch) {

    }
    override fun draw3D(modelBatch: ModelBatch, environment: Environment?) {
        modelBatch.render(modelInstance, environment)
    }

    override fun dispose() {
        this.model.dispose()
    }
    fun handleInputDesktop() {
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            val translationVector = Vector3(0f,10f*Gdx.graphics.deltaTime,0f)
            this.camera.translate(translationVector)
            this.modelInstance.transform.translate(translationVector.scl(-1f))
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.S)) {
            val translationVector = Vector3(0f,-10f*Gdx.graphics.deltaTime,0f)
            this.camera.translate(translationVector)
            this.modelInstance.transform.translate(translationVector.scl(-1f))
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)){
            val translationVector = Vector3(-10f*Gdx.graphics.deltaTime,0f,0f)
            this.camera.translate(translationVector)
            this.modelInstance.transform.translate(translationVector.scl(-1f))
        }else if (Gdx.input.isKeyPressed(Input.Keys.D)){
            val translationVector = Vector3(10f*Gdx.graphics.deltaTime,0f,0f)
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
                val translationVector = Vector3(10f*Gdx.graphics.deltaTime,0f,0f)
                this.camera.translate(translationVector)
                this.modelInstance.transform.translate(translationVector.scl(-1f))
            }else if(touch2dCoordinates.x < -orthographicCamera.viewportWidth/4) {
                val translationVector = Vector3(-10f*Gdx.graphics.deltaTime,0f,0f)
                this.camera.translate(translationVector)
                this.modelInstance.transform.translate(translationVector.scl(-1f))
            }
            if(touch2dCoordinates.y > orthographicCamera.viewportHeight/4){
                val translationVector = Vector3(0f,10f*Gdx.graphics.deltaTime,0f)
                this.camera.translate(translationVector)
                this.modelInstance.transform.translate(translationVector)
            } else if (touch2dCoordinates.y < -orthographicCamera.viewportHeight/4){
                val translationVector = Vector3(0f,-10f*Gdx.graphics.deltaTime,0f)
                this.camera.translate(translationVector)
                this.modelInstance.transform.translate(translationVector)
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

    fun handlePan(x: Float, y: Float, deltaX: Float, deltaY: Float) {
        //TODO handle pan in a better way
        println("Handling pan...")
        if(deltaX > 10f || deltaY > 10f){

            setFocus(this.focusVector3.add(deltaX/10,deltaY/10,0f))
        }

    }


}