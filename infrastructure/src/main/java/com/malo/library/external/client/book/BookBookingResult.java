package com.malo.library.external.client.book;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BookBookingResult {
    Long bookId;

    Integer availableQuantity;

    Integer bookedQuantity;
}
