package vip.mate.core.auth.service.impl;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import vip.mate.core.auth.service.TokenService;
import vip.mate.core.common.constant.Oauth2Constant;
import vip.mate.core.common.exception.TokenException;
import vip.mate.core.common.util.TokenUtil;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Service
public class TokenServiceImpl implements TokenService {

    @Override
    public boolean checkToken(HttpServletRequest request) throws TokenException {
        String headerToken = request.getHeader(Oauth2Constant.HEADER_TOKEN);
        if (StringUtils.isBlank(headerToken)) {
            throw new TokenException("没有携带Token信息！");
        }
        String token = StringUtils.isNotBlank(headerToken) ? TokenUtil.getToken(headerToken) : "";
        if (StringUtils.isNotBlank(token)) {
            Claims claims = null;
            try {
                claims = TokenUtil.getClaims(token);
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage());
                throw new TokenException("Token已过期！");
            }
//            if (claims == null) {
//                log.error("claims:{}", "claims值为空");
//                throw new TokenException("Token已过期！");
//            }
        }
        return true;
    }
}
