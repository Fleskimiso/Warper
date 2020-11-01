package com.warper.interfaces

import com.badlogic.gdx.graphics.g3d.Environment
import com.badlogic.gdx.graphics.g3d.ModelBatch

interface Drawable3D {
    fun draw3D(modelBatch: ModelBatch, environment: Environment?)
    fun dispose()
}