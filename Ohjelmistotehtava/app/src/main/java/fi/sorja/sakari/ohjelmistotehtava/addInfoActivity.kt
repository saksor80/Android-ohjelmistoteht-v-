package fi.sorja.sakari.ohjelmistotehtava

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.NavHostFragment
import com.squareup.picasso.Picasso
// Määritellään dataPass tiedon siirtoa varten
class addInfoActivity : AppCompatActivity(), reviewFragment.dataPass  {

    // Määritetään muuttujat ja tietorakenteet
    lateinit var title: TextView
    lateinit var category: TextView
    lateinit var alcoholic: TextView
    lateinit var glass: TextView
    lateinit var instructions : TextView
    lateinit var image: ImageView

    lateinit var rating : TextView
    lateinit var counter : TextView

    var key:String = "1"
    var arrayList:ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_info)

        // Määritellään Host fragmenteille
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Otetaan MainActivitystä tullut arrayList laajempaa näkymää varten
        arrayList = intent.getStringArrayListExtra(key) as ArrayList<String>

        // Määritellään layoutin komponentit ja viedään arrayLististä tiedot niihin
        image = findViewById(R.id.imageView)
        // Otetaan verkko-osoite kuvalle ja haetaan picassolla kuva imageViewiin
        Picasso.get().load(arrayList.get(5)).into(image)

        title = findViewById(R.id.title)
        title.text = arrayList.get(0)

        category = findViewById(R.id.category)
        category.text = arrayList.get(1)

        alcoholic = findViewById(R.id.alcoholic)
        alcoholic.text = arrayList.get(2)

        glass = findViewById(R.id.glass)
        glass.text = arrayList.get(3)

        instructions = findViewById(R.id.instructions)
        instructions.text = arrayList.get(4)
    }

    // Otetaan fragmentista tulevaa tietoa vastaan ja laitetaan se keskiarvoa laskevaan funktioon, joka laittaa arvon laajempaan näkymään
    override fun dataPass(data: String) {
        countAverage(data)
    }

    // Lasketaan keskiarvoa laajenmpaan näkymään, jos tulee useampia arvosteluja fragmentista
    fun countAverage(data: String) {

        counter = findViewById(R.id.counter)
        rating = findViewById(R.id.rating_result)

        val intCounter: Int
        var floatRating: Float
        val strRating:String

        if (rating.text.toString() != "0") {
            floatRating = (rating.text.toString().toFloat() * counter.text.toString().toFloat()) + data.toFloat()

            intCounter = counter.text.toString().toInt() + 1
            counter.text = intCounter.toString()

            floatRating = floatRating / intCounter.toFloat()
            strRating = String.format("%.2f",floatRating)
            rating.text = strRating

        } else  {
            rating.text = data + ".00"
            counter.text = "1"
        }
    }
}