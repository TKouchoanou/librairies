package com.malo.library.exception.business;

import com.malo.library.exception.BorrowExceptionKey;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BusinessExceptionKeyEnum implements BorrowExceptionKey {

    MEMBER_NOT_FOUND(404, "Le membre est introuvable avec l'identifiant %"),
    MEMBER_IS_INACTIVE(403, "Le membre est inactif"),
    MEMBER_IS_BLOCKED(403, "Le membre est bloqué"),
    BOOK_NOT_FOUND(404, "Le livre est introuvable"),
    BOOK_NOT_AVAILABLE(409, "Le livre n'est pas disponible"),
    BOOK_ALREADY_BORROWED_BY_MEMBER(409, "Le livre est déjà emprunté par le membre"),
    MAX_BORROW_LIMIT_REACHED(403, "Limite maximale d'emprunt atteinte"),
    BOOK_BORROW_FAILED(500, "Échec de l'emprunt du livre"),
    INCOHERENT_COMMAND(400, "Commande incohérente"),
    RETURNED_BORROW_NOT_OWNED_BY_MEMBER(400, "L'emprunt retourné n'appartient pas au membre");

    final int mappedHttpCode;
    final String message;

}
