package com.example.spbapp
import com.google.firebase.firestore.PropertyName

data class Spell(
    val name: String = "",
    val school: String = "",
    val level: Int = 0,
    @PropertyName("casting_time") val castingTime: String = "",
    val component: String = "",
    @PropertyName("saving_throw") val savingThrow: String = "",
    @PropertyName("spell_resistance") val spellResistance: String = "",
    // Acá van otros campos según estructura de datos (faltan millones)
)
