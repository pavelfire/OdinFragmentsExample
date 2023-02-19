package com.vk.directop.odinfragmentsexample

data class Marker(
    val currentPos: CurrentPosition = CurrentPosition(0f, 0f),
    val value: Int = 0
)

data class CurrentPosition(
    var x: Float,
    var y: Float
)