package com.algos.droidactivator.backend

import java.text.SimpleDateFormat

class Activation {

    // formattazione della data
    private static SimpleDateFormat SDF = new SimpleDateFormat('d MMM yy')

    // nomi interni dei campi (ordine non garantito)
    def String appName
    def boolean paid = false
    def int amount
    def boolean active = false
    def String uniqueID = ''
    def int level = 0
    def String userID
    def Date expiration
    def String activationCode = ''
    def String userName = ''
    def String userAddress = ''
    def String userCategory = ''
    def String userCurrentMail = ''

    // Attributi dei vari campi
    // Ordine in cui vengono mostrate le colonne nella lista
    static constraints = {
        appName(nullable: true, blank: true)
        paid(nullable: false)
        amount(nullable: true, blank: true)
        active(nullable: false)
        uniqueID(nullable: true, blank: true)
        level(nullable: false, blank: false)
        userID(nullable: false, blank: false, email: true)
        expiration(nullable: true, formatoData: SDF)
        activationCode(nullable: true, blank: true)
        userName(nullable: true, blank: true)
        userCategory(nullable: true, blank: true)
        userCurrentMail(nullable: true, blank: true)
    }

    static mapping = {
        // le sottoclassi usano tavole specifiche
        tablePerHierarchy false
    }

}// end of domain class