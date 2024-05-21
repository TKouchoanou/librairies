package com.malo.library.domain.model.valueObject;
    public enum BorrowStatus {
        ONGOING,        // En cours (le livre est actuellement emprunté)
        RETURNED,       // Restitué (le livre a été correctement restitué)
        LOST,           // Perdu (le livre a été déclaré perdu par l'utilisateur)
        DAMAGED         // Endommagé (le livre a été retourné dans un état endommagé)
    }


