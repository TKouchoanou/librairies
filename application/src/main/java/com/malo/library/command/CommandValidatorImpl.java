package com.malo.library.command;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.Set;
@Component
public class CommandValidatorImpl implements CommandValidator{
    private Validator validator;
@Autowired
    public CommandValidatorImpl(Validator validator) {
        this.validator=validator;
    }



    @Override
    public void validBeforeHandling(Command command) {
        Set<ConstraintViolation<Command>> violations = validator.validate(command);
        StringBuilder violationsMessage=new StringBuilder(violations.toString());
        if(violations.size()>0){
            throw new RuntimeException(violationsMessage.toString());
        }
        if(!command.checkValidityBeforeHandling(violationsMessage)){
            throw new RuntimeException(violationsMessage.toString());
        }
    }

    @Override
    public void validAfterHandling(Command command) {
        StringBuilder violationsMessage=new StringBuilder();

        if(!command.checkValidityAfterHandling(violationsMessage)){
            throw new RuntimeException(violationsMessage.toString());
        }
    }
}
