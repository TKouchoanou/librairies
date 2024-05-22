package com.malo.library.domain.model.entities;

import com.malo.library.domain.model.valueObject.BorrowStatus;
import com.malo.library.domain.model.valueObject.ReturnStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Builder
public class Borrowing {
    Long id;
    Long memberId;

    Long bookId;

    LocalDate pickUpDate;

    LocalDate returnedDate;
    @Setter()
    LocalDate effectiveReturnedDate;

    BorrowStatus borrowStatus;

    ReturnStatus returnStatus;

    public void calculateStatus() {
        LocalDate today = LocalDate.now();
        if (effectiveReturnedDate != null) {
            borrowStatus = BorrowStatus.RETURNED;
            returnStatus = effectiveReturnedDate.isAfter(returnedDate) ? ReturnStatus.DELAYED : ReturnStatus.ONTIME;
        } else if (returnedDate != null) {
            borrowStatus = BorrowStatus.ONGOING;
            returnStatus = today.isAfter(returnedDate) ? ReturnStatus.DELAYED : ReturnStatus.ONTIME;
        } else {
            borrowStatus = BorrowStatus.ONGOING;
        }
    }

    public Borrowing markedAsDelayed(){
        this.returnStatus = ReturnStatus.DELAYED;
        return  this ;
    }

    public Borrowing markedAsReturned(){
        this.borrowStatus = BorrowStatus.RETURNED;
        return  this ;
    }

    public boolean isDelayed(){
        return  this.returnStatus.equals(ReturnStatus.DELAYED) ;
    }

    public boolean isOnGoing(){
        return  this.borrowStatus.equals(BorrowStatus.ONGOING) ;
    }
}
