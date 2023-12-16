package ru.example.megamarket.user;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-16T00:59:56+0000",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
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
        userResponse.setRole( user.getRole() );
        userResponse.setUserStatus( user.getUserStatus() );
        userResponse.setPhone( user.getPhone() );
        userResponse.setImage( user.getImage() );

        return userResponse;
    }
}
