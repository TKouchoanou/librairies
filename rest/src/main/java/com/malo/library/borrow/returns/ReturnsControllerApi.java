package com.malo.library.borrow.returns;

import com.malo.library.borrow.comons.EndPoints;
import com.malo.library.borrow.comons.Http;
import com.malo.library.command.command.borrow.ReturnCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = EndPoints.APP_ROOT + EndPoints.RETURN_VERSION + EndPoints.RETURN_PATH, description = "API des retours")
@RequestMapping(EndPoints.APP_ROOT + EndPoints.RETURN_VERSION)
public interface ReturnsControllerApi {

    @Operation(summary = "Enregistrer le retour d'emprunt", method = Http.Method.POST)
    @ApiResponses({
            @ApiResponse(responseCode = Http.StatusCode.OK, description = "Retour enregistré")
    })
    @PostMapping(value = EndPoints.RETURN_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    void handle(@RequestBody ReturnCommand returnCommand);
}
