package ru.example.megamarket.deposit;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.example.megamarket.user.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-09T15:55:25+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 18.0.2-ea (Private Build)"
)
@Component
public class DepositMapperImpl implements DepositMapper {

    @Override
    public Deposit depositRequestToDeposit(DepositRequest request) {
        if ( request == null ) {
            return null;
        }

        Deposit.DepositBuilder deposit = Deposit.builder();

        deposit.sum( request.getSum() );

        return deposit.build();
    }

    @Override
    public DepositResponse depositToDepositResponse(Deposit deposit) {
        if ( deposit == null ) {
            return null;
        }

        DepositResponse depositResponse = new DepositResponse();

        depositResponse.setUserId( depositUserId( deposit ) );
        depositResponse.setId( deposit.getId() );
        depositResponse.setSum( deposit.getSum() );

        return depositResponse;
    }

    private Integer depositUserId(Deposit deposit) {
        if ( deposit == null ) {
            return null;
        }
        User user = deposit.getUser();
        if ( user == null ) {
            return null;
        }
        Integer id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
