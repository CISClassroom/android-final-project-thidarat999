package th.ac.kku.cis.mobileapp.MyCookingApplication

class namefood(var nameFOOD:String)

//ไฟล์โมเดล
class food {
    companion object Factory {
        fun create(): food = food()
    }
    var name_f: String? = null
    var staple_f: String? = null
    var step_f: String? = null
}

//class Food1 (val id : String,val name_f : String,val step_f : String){}

