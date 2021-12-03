package service;

import com.wallet.dao.CustomerDao;
import com.wallet.model.Customer;
import com.wallet.model.dto.LoginRequestDto;
import com.wallet.model.dto.SignUpDto;
import com.wallet.payload.WalletResponsePayload;
import com.wallet.service.impl.CustomerServiceImpl;
import com.wallet.service.impl.WalletUserDetailsService;
import com.wallet.util.JWTUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest {

    @InjectMocks
    CustomerServiceImpl customerServiceImpl;

    @Mock
    ModelMapper modelMapper;

    @Mock
    CustomerDao customerDao;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    WalletUserDetailsService walletUserDetailsService;

    @Mock
    JWTUtil jwtUtil;

    @Mock
    SignUpDto signUpDto;

    @Mock
    Customer customer;

    @Test
    public void signUp_success(){
        SignUpDto signUpDto = new SignUpDto("sg2203@outlook.com","shivam","gulati","Dec#4140");
        Customer customer = new Customer("ss","ss","ss",100.0,"ss","ss");
        when(modelMapper.map(signUpDto,Customer.class)).thenReturn(customer);
        when(customerDao.existsById(Mockito.anyString())).thenReturn(false);
        when(customerDao.save(Mockito.any())).thenReturn(new Customer());
        CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl(customerDao,modelMapper,authenticationManager,walletUserDetailsService,jwtUtil);

        ResponseEntity<WalletResponsePayload> responseEntity = customerServiceImpl.signUp(signUpDto);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void logIn_success(){
        LoginRequestDto loginRequestDto = new LoginRequestDto("ss","ss");
        UserDetails userDetails = new User("ss","ss",new ArrayList<>());
        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getEmailId(), loginRequestDto.getPassword()))).thenReturn(null);
        when(walletUserDetailsService.loadUserByUsername(loginRequestDto.getEmailId())).thenReturn(userDetails);
        when(jwtUtil.generateToken(userDetails)).thenReturn("123");
        CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl(customerDao,modelMapper,authenticationManager,walletUserDetailsService,jwtUtil);
        ResponseEntity<WalletResponsePayload> responseEntity = customerServiceImpl.logIn(loginRequestDto);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

    }
}
