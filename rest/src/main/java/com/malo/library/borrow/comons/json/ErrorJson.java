package com.malo.library.borrow.comons.json;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = { "errorDescription" })
public class ErrorJson {

    private String error;

    private String errorDescription;

    private List<String> errorParams;

    // Optional key representing the target of the error (example: the member id when it's not found)
    private String targetKey;

    public ErrorJson() {
        super();
    }

}