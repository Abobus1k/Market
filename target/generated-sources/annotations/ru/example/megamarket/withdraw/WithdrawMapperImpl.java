package ru.example.megamarket.withdraw;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.example.megamarket.user.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-16T00:59:56+0000",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class WithdrawMapperImpl implements WithdrawMapper {

    @Override
    public Withdraw withdrawRequestToWithdraw(WithdrawRequest request) {
        if ( request == null ) {
            return null;
        }

        Withdraw.WithdrawBuilder withdraw = Withdraw.builder();

        withdraw.sum( request.getSum() );

        return withdraw.build();
    }

    @Override
    public WithdrawResponse withdrawToWithdrawResponse(Withdraw withdraw) {
        if ( withdraw == null ) {
            return null;
        }

        WithdrawResponse withdrawResponse = new WithdrawResponse();

        withdrawResponse.setUserId( withdrawUserId( withdraw ) );
        withdrawResponse.setId( withdraw.getId() );
        withdrawResponse.setSum( withdraw.getSum() );

        return withdrawResponse;
    }

    private Integer withdrawUserId(Withdraw withdraw) {
        if ( withdraw == null ) {
            return null;
        }
        User user = withdraw.getUser();
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
