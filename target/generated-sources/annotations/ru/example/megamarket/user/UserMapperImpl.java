package ru.example.megamarket.user;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-10-31T14:16:52+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 18.0.2-ea (Private Build)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserResponse userToUserResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponse userResponse = new UserResponse();

        if ( user.getId() != null ) {
            userResponse.setId( user.getId().longValue() );
        }
        userResponse.setFirstname( user.getFirstname() );
        userResponse.setLastname( user.getLastname() );
        userResponse.setEmail( user.getEmail() );
        userResponse.setPassword( user.getPassword() );
        userResponse.setRegistrationDate( user.getRegistrationDate() );
        if ( user.getBalance() != null ) {
            userResponse.setBalance( user.getBalance().longValue() );
        }
        userResponse.setRating( user.getRating() );

        return userResponse;
    }
}
