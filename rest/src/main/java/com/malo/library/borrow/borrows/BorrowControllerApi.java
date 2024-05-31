package com.malo.library.borrow.borrows;

import com.malo.library.borrow.comons.EndPoints;
import com.malo.library.borrow.comons.Http;
import com.malo.library.command.command.borrow.BorrowCommand;
import com.malo.library.exception.business.BusinessException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = EndPoints.APP_ROOT + EndPoints.BORROW_VERSION + EndPoints.BORROW_PATH, description = "API des emprunts")
@RequestMapping(EndPoints.APP_ROOT + EndPoints.BORROW_VERSION)
public interface BorrowControllerApi {
    @Operation(summary = "Créer un nouvel emprunt", method = Http.Method.POST)
    @ApiResponses({
            @ApiResponse(responseCode = Http.StatusCode.CREATED, description = "Emprunt créé")
    })
    @PostMapping(value = EndPoints.BORROW_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    Long create(@RequestBody BorrowCommand borrowCommand) throws BusinessException;
}
