package domain.model.valueObject;

public enum MemberStatus {
    ACTIVE,       // Actif (le membre est autorisé à emprunter des livres)
    BLOCKED,      // Bloqué (le membre est temporairement empêché d'emprunter des livres)
    BANNED       // Interdit (le membre est interdit d'emprunter des livres)
}
