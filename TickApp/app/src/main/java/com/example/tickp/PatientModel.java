package com.example.tickp;

public class PatientModel {
    private String Nom;
    private String Prenom;
    private String Service;
    private int Matricule;
    private String Type_Analyse;

    public PatientModel(String Nom, String Prenom, String Service, int Matricule, String TA){
        this.Nom=Nom;
        this.Prenom=Prenom;
        this.Service=Service;
        this.Matricule=Matricule;
        this.Type_Analyse=TA;


    }

    public PatientModel() {
    }

    public static int getMatricule() {
        return Matricule;
    }

    public void setMatricule(int matricule) {
        Matricule = matricule;
    }

    public static String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public static String getPrenom() {
        return Prenom;
    }

    public void setPrenom(String prenom) {
        Prenom = prenom;
    }

    public static String getService() {
        return Service;
    }

    public void setService(String service) {
        Service = service;
    }

    public static String getType_Analyse() {
        return Type_Analyse;
    }

    public void setType_Analyse(String type_Analyse) {
        Type_Analyse = type_Analyse;
    }



}
