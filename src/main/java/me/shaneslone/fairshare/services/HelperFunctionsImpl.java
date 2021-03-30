package me.shaneslone.fairshare.services;

import me.shaneslone.fairshare.models.User;
import me.shaneslone.fairshare.models.ValidationError;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Service(value = "helperFunctions")
public class HelperFunctionsImpl
        implements HelperFunctions
{
    @Autowired
    private UserService userService;

    public List<ValidationError> getConstraintViolation(Throwable cause)
    {
        // Find any data violations that might be associated with the error and report them
        // data validations get wrapped in other exceptions as we work through the Spring
        // exception chain. Hence we have to search the entire Spring Exception Stack
        // to see if we have any violation constraints.
        while ((cause != null) && !(cause instanceof ConstraintViolationException || cause instanceof MethodArgumentNotValidException))
        {
            System.out.println(cause.getClass()
                    .toString());
            cause = cause.getCause();
        }

        List<ValidationError> listVE = new ArrayList<>();

        // we know that cause either null or an instance of ConstraintViolationException
        if (cause != null)
        {
            if (cause instanceof ConstraintViolationException)
            {
                ConstraintViolationException ex = (ConstraintViolationException) cause;
                ValidationError newVe = new ValidationError();
                newVe.setCode(ex.getMessage());
                newVe.setMessage(ex.getConstraintName());
                listVE.add(newVe);
            } else
            {
                if (cause instanceof MethodArgumentNotValidException)
                {
                    MethodArgumentNotValidException ex = (MethodArgumentNotValidException) cause;
                    List<FieldError> fieldErrors = ex.getBindingResult()
                            .getFieldErrors();
                    for (FieldError err : fieldErrors)
                    {
                        ValidationError newVe = new ValidationError();
                        newVe.setCode(err.getField());
                        newVe.setMessage(err.getDefaultMessage());
                        listVE.add(newVe);
                    }
                } else
                {
                    System.out.println("Error in producing constraint violations exceptions. " +
                            "If we see this in the console a major logic error has occurred in the " +
                            "helperfunction.getConstraintViolation method that we should investigate. " +
                            "Note the application will keep running as this only affects exception reporting!");
                }
            }
        }
        return listVE;
    }

    @Override
    public boolean isAuthorizedToMakeChange(String username)
    {
        // Check to see if the user whose information being requested is the current user
        // Check to see if the requesting user is an admin
        // if either is true, return true
        // otherwise return false;
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        return username.equalsIgnoreCase(authentication.getName()
                .toLowerCase()) || authentication.getAuthorities()
                .contains(new SimpleGrantedAuthority("ROLE_ADMIN"));

    }

    @Override
    public boolean isHouseholdMember(Set<User> householdMembers) {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        // find the current authenticated user
        User currentUser = userService.findByName(authentication.getName());
        // returns true if the current user is a member of the household or has the role of admin
        return householdMembers.contains(currentUser) || currentUser.getAuthority().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @Override
    public String generateHouseholdKey() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}