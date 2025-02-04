package com.example.examenjpc_1.modelo

data class Alimento(
    var nombre:String="",
    var grProt:Double=0.0,
    var grHC:Double=0.0,
    var grLip:Double=0.0,
    var cantidad:Double=100.0
   ){
    public var KcalTotales: Double=0.0
    public fun calculaKcal():Double{
        KcalTotales=4*cantidad/100*grProt+
                4*cantidad/100*grHC+
                4*cantidad/100*grLip

        return KcalTotales
    }

}
