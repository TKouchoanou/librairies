package domain.model.entities;

import domain.model.valueObject.BookAvailabilityStatus;
import domain.model.valueObject.BookStatus;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Book {
    Long id ;
    BookStatus bookStatus;

    BookAvailabilityStatus availabilityStatus;

    public boolean notFound(){
        return this.bookStatus.equals(BookStatus.NOT_FOUND);
    }

    public boolean notAvailable(){
        return !this.availabilityStatus.equals(BookAvailabilityStatus.AVAILABLE);
    }
}
