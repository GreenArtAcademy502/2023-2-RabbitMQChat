package com.green.rabbitmqchat.user;

import com.green.rabbitmqchat.common.*;
import com.green.rabbitmqchat.entity.UserEntity;
import com.green.rabbitmqchat.exception.AuthErrorCode;
import com.green.rabbitmqchat.exception.RestApiException;
import com.green.rabbitmqchat.security.AuthenticationFacade;
import com.green.rabbitmqchat.security.JwtTokenProvider;
import com.green.rabbitmqchat.security.MyPrincipal;
import com.green.rabbitmqchat.security.MyUserDetails;
import com.green.rabbitmqchat.user.model.UserSigninDto;
import com.green.rabbitmqchat.user.model.UserSigninVo;
import com.green.rabbitmqchat.user.model.UserSignupDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AppProperties appProperties;
    private final CookieUtils cookieUtils;

    public ResVo signup(UserSignupDto dto) {
        String hashedPw = passwordEncoder.encode(dto.getUpw());
        UserEntity entity = UserEntity.builder()
                .uid(dto.getUid())
                .upw(hashedPw)
                .nm(dto.getNm())
                .pic(dto.getPic())
                .role(RoleEnum.USER)
                .build();
        UserEntity result = repository.save(entity);
        return new ResVo(result.getIuser().intValue());
    }


    public UserSigninVo signin(HttpServletResponse res, UserSigninDto dto) {
        Optional<UserEntity> optEntity = repository.findByUid(dto.getUid());
        UserEntity entity = optEntity.orElseThrow(() -> new RestApiException(AuthErrorCode.NOT_EXIST_USER_ID));

        if(!passwordEncoder.matches(dto.getUpw(), entity.getUpw())) {
            throw new RestApiException(AuthErrorCode.VALID_PASSWORD);
        }

        long iuser = entity.getIuser();
        MyPrincipal myPrincipal = MyPrincipal.builder()
                .iuser(iuser)
                .nm(entity.getNm())
                .build();
        myPrincipal.getRoles().add(entity.getRole().name());

        String at = jwtTokenProvider.generateAccessToken(myPrincipal);
        String rt = jwtTokenProvider.generateRefreshToken(myPrincipal);

        //rt > cookie에 담을꺼임
        int rtCookieMaxAge = appProperties.getJwt().getRefreshTokenCookieMaxAge();
        cookieUtils.deleteCookie(res, "rt");
        cookieUtils.setCookie(res, "rt", rt, rtCookieMaxAge);

        return UserSigninVo.builder()
                .result(Const.SUCCESS)
                .iuser(iuser)
                .nm(entity.getNm())
                .pic(entity.getPic())
                .accessToken(at)
                .build();

    }

    public ResVo signout(HttpServletResponse res) {
        cookieUtils.deleteCookie(res, "rt");
        return new ResVo(1);
    }

    public UserSigninVo getRefreshToken(HttpServletRequest req) {
        //Cookie cookie = cookieUtils.getCookie(req, "rt");
        Optional<String> optRt = cookieUtils.getCookie(req, "rt").map(Cookie::getValue);
        if(optRt.isEmpty()) {
            return UserSigninVo.builder()
                    .result(Const.FAIL)
                    .accessToken(null)
                    .build();
        }
        String token = optRt.get();
        if(!jwtTokenProvider.isValidateToken(token)) {
            return UserSigninVo.builder()
                    .result(Const.FAIL)
                    .accessToken(null)
                    .build();
        }
        MyUserDetails myUserDetails = (MyUserDetails) jwtTokenProvider.getUserDetailsFromToken(token);
        MyPrincipal myPrincipal = myUserDetails.getMyPrincipal();

        String at = jwtTokenProvider.generateAccessToken(myPrincipal);

        return UserSigninVo.builder()
                .result(Const.SUCCESS)
                .accessToken(at)
                .build();
    }

}
