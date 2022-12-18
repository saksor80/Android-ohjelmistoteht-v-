package fi.sorja.sakari.ohjelmistotehtava

// Määritellään luokka Drink, muuttujat ja construktori
public class Drink{

    lateinit var name: String
    lateinit var category: String
    lateinit var alcoholic: String
    lateinit var glass: String
    lateinit var instructions: String
    lateinit var drinkthumb: String
    lateinit var ingredient: String
    lateinit var measure: String

    constructor(name: String, category:String, alcoholic:String, glass:String,instructions:String,
                drinkthumb:String,ingredient:String,measure:String) {

        this.name = name
        this.category = category
        this.alcoholic = alcoholic
        this.glass= glass
        this.instructions = instructions
        this.drinkthumb = drinkthumb
        this.ingredient = ingredient
        this.measure = measure
    }

    constructor()
}
