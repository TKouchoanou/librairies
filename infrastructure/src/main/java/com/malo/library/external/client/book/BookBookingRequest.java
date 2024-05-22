package com.malo.library.external.client.book;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BookBookingRequest {
    Long bookId;

    Integer quantity;
}
