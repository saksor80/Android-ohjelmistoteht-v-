package fi.sorja.sakari.ohjelmistotehtava

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import org.json.JSONTokener
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection


class MainActivity : AppCompatActivity() {

    // Määritellään muuttujat ja tietorakenteet
    lateinit var list: ListView
    var arrayList_details:ArrayList<Drink> = ArrayList()
    var key:String = "1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Luodaan Scope, minkä sisällä voidaan ajaa aikaa vieviä tehtäviä että UI ei kärsi
        lifecycleScope.launch  {

            // Ajetaan httpGet-funktio(API-rajapinnan osoite), json-parser-funktio ja tallennetaan tulos ArrayList-tietorakenteeseen
            arrayList_details = jsonParser(httpGet("https://www.thecocktaildb.com/api/json/v1/1/search.php?f=m"))

            // Loput ajetaan UI-threadilla
            runOnUiThread({

                // Määritellään adapteri, mitä käytetään listViewin näyttämiseen
                val myAdapter: MyAdapter
                myAdapter = MyAdapter(this@MainActivity, arrayList_details)

                // Määritellään lista ja liitetään siihen adapteri
                list = findViewById<ListView>(R.id.list) as ListView
                list.adapter = myAdapter

                // Kun listan kohtaa clickataan tehdään yksi "tietue" arrayList_detailsista ja laitetaan se arrayList-tietorakenteeseen ja laitaan se sendData-funktioon
                list.setOnItemClickListener { parent, view, position, id ->

                    val arrayList:ArrayList<String> = ArrayList()

                    arrayList.add(arrayList_details.get(position).name)
                    arrayList.add(arrayList_details.get(position).category)
                    arrayList.add(arrayList_details.get(position).alcoholic)
                    arrayList.add(arrayList_details.get(position).glass)
                    arrayList.add(arrayList_details.get(position).instructions)
                    arrayList.add(arrayList_details.get(position).drinkthumb)

                    sendData(arrayList)
                }
            })
        }
    }

    // Json-parser-funktio, joka ottaa httpGetistä tulleen Stringin sisäänsä
    fun jsonParser(parsee: String): ArrayList<Drink> {
        val main = JSONTokener(parsee).nextValue() as JSONObject
        val drinksArray = main.getJSONArray("drinks")
        val size:Int = drinksArray.length()

        // Otetaan Stringistä tiedot talteen ja laitettaan ne luokkarakenteeseen Drink
        for (i in 0..size-1) {

                val drink = Drink()

                drink.name = drinksArray.getJSONObject(i).getString("strDrink")
                drink.category = drinksArray.getJSONObject(i).getString("strCategory")
                drink.alcoholic = drinksArray.getJSONObject(i).getString("strAlcoholic")
                drink.glass = drinksArray.getJSONObject(i).getString("strGlass")
                drink.instructions = drinksArray.getJSONObject(i).getString("strInstructions")
                drink.drinkthumb = drinksArray.getJSONObject(i).getString("strDrinkThumb")

                // Lopuksi laitetaan luokan tiedot tietorakenteeseen arrayLost_details
                arrayList_details.add(drink)
        }
        // Palautetaan arrayList_details
        return arrayList_details
    }

    // httpGet-funktio muodostaa yhteyden web-osoitteeseen(palvelimeen) annetulla osoitteella ja ottaa API-rajapinnasta sisäänsä bittivirtaa ja kutsuu funktiota joka muuttaa bittivirran String-muotoon ja lopulta palauttaa sen eteenpäin
    suspend fun httpGet(myUrl: String): String{
        val result = withContext(Dispatchers.IO){
            val inputStream: InputStream
            val url = URL(myUrl)
            val conn: HttpsURLConnection = url.openConnection() as HttpsURLConnection
            conn.connect()
            inputStream = conn.inputStream
            if (inputStream != null) {
                convertInputStreamToString(inputStream)
            }
            else{
                "Doesn't work!"
            }
        }
        return result
    }

    // Muuttaa bittivirran String-muotoon ja palauttaa sen
    private fun convertInputStreamToString(Inpustream: InputStream): String {
        val bufferedReader: BufferedReader? = BufferedReader(InputStreamReader(Inpustream))
        var line:String? = bufferedReader?.readLine()
        var result = ""
        while (line != null) {
            result += line
            line = bufferedReader?.readLine()
        }
        Inpustream.close()
        Log.d("input",result)
        return result
    }

    // Lähettää arrayList-tietorakenteen Intenttinä addInfoActivitylle ja samalla käynnistää sen
    fun sendData(arrayList: ArrayList<String>) {
        val intent = Intent(this,addInfoActivity::class.java).apply {
            putStringArrayListExtra(key,arrayList)
        }
        startActivity(intent)
    }
}