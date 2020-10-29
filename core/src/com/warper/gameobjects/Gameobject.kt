package com.warper.gameobjects

import com.badlogic.gdx.graphics.g3d.Environment
import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.warper.interfaces.Drawable3D

open class Gameobject(x: Float, y:Float, z:Float, val modelInstance: ModelInstance): Drawable3D {

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
}