import kotlin.math.roundToInt
import java.io.File

const val TAVERN_NAME = "Taernyl's Folly"

val patronList = mutableListOf("Eli", "Mordoc", "Sophie")
val lastName = listOf("Ironfoot", "Fernsworth", "Baggins")
val uniquePatrons = mutableSetOf<String>()
val menuList = File("data/tavern-menu-items.txt")
    .readText()
    .split("\n")
val patronGold = mutableMapOf<String, Double>()

fun main() {
//    if (patronList.contains("Eli")) {
//        println("The tavern master says: Eli's in the back playing cards.")
//    } else {
//        println("The tavern master says: Eli isn't here.")
//    }
//
//    if (patronList.containsAll(listOf("Sophie", "Mordoc"))) {
//        println("The tavern master says: Yea, they're seated by the stew kettle.")
//    } else {
//        println("The tavern master says: Nay, they departed hours ago.")
//    }


    println(patronList)
//    patronList.forEachIndexed { index, patron ->
//        println("Good evening, $patron - you're #${index + 1} in line")
//        placeOrder(patron, menuList.shuffled().first())
//    }
    menuList.forEachIndexed { index, data ->
        println("$index : $data")
    }
    (0..9).forEach {
        val first = patronList.shuffled().first()
        val last = lastName.shuffled().first()
        val name = "$first $last"
        uniquePatrons += name
    }

    uniquePatrons.forEach {
        patronGold[it] = 6.0
    }
    var orderCount = 0
    while (orderCount <= 9) {
        placeOrder(uniquePatrons.shuffled().first(),
            menuList.shuffled().first())
        orderCount++
    }
    displayPatronBalances()
}

private fun displayPatronBalances() {
    patronGold.forEach { patron, balance ->
        println("$patron, balance: ${"%.2f".format(balance)}")
    }
}

private fun placeOrder(patronName: String, menuData: String) {
    val indexOfApostrophe = TAVERN_NAME.indexOf('\'')
    val tavernMaster = TAVERN_NAME.substring(0 until indexOfApostrophe)
    println("$patronName speaks with $tavernMaster about their order.")

    val (type, name, price) = menuData.split(',')
    val message = "$patronName buys a $name ($type) for $price."
    println(message)
    performPurchase(price.toDouble(), patronName)

    val phrase = if (name == "Dragon's Breath") {
        "$patronName exclaims: ${toDragonSpeak("Ah, delicious $name!")}"
    } else {
        "$patronName says: Thanks for the $name."
    }
    println(phrase)
}

private fun toDragonSpeak(phrase: String) =
    phrase.replace(Regex("[aeiou]")) {
        when (it.value) {
            "a" ->"4"
            "e" ->"3"
            "i" ->"1"
            "o" ->"0"
            "u" ->"|_|"
            else ->it.value
        }
    }

fun performPurchase(price: Double, patronName: String) {
    val totalPurse = patronGold.getValue(patronName)
    patronGold[patronName] = totalPurse - price
}

class Weapon(val name: String)
class Player {
    var weapon: Weapon? = Weapon("Ebony Kris")

    fun printWeaponName() {
        if (weapon != null) {
            println(weapon!!.name)
        }
    }
}

fun main(args: Array<String>) {
    Player().printWeaponName()
}