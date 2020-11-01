package com.warper.gameobjects

import com.badlogic.gdx.graphics.g3d.Environment
import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.math.Vector3
import com.warper.interfaces.Drawable3D

open class GameObject(x: Float, y:Float, z:Float, protected val modelInstance: ModelInstance): Drawable3D {

    init {
        modelInstance.transform.translate(x, y, z)
    }

    override fun draw3D(modelBatch: ModelBatch, environment: Environment?) {
        if (environment == null) {
            modelBatch.render(modelInstance)
        }else{
            modelBatch.render(modelInstance,environment)
        }
    }
    override fun dispose() {
        modelInstance.model.dispose()
    }
    fun setPosition(x:Float,y:Float,z:Float){
        this.modelInstance.transform.setTranslation(Vector3(x,y,z))
    }
    fun getPosition(): Vector3{
        return this.modelInstance.transform.getTranslation(Vector3(0f,0f,0f))
    }
}