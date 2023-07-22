package com.snowball.board.common.util;

import com.snowball.board.common.exception.message.AuthExceptionMessage;
import com.snowball.board.common.exception.model.UnauthorizedException;
import com.snowball.board.config.UserPrincipleDto;
import com.snowball.board.domain.auth.service.CustomUserDetailsService;
import com.snowball.board.domain.user.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    private final CustomUserDetailsService userDetailsService;

    @Value("${application.security.jwt.secret-key}")
    private String SECRET_KEY;

    @Value("${application.security.jwt.expiration}")
    private Long EXPIRATION;

    @Value("${application.security.jwt.refresh-token.expiration}")
    private Long REFRESH_EXPIRATION;

    @Autowired
    public JwtUtil(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public String extractUserAccount(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Long extractUserId(String token) {
        return Long.valueOf(extractClaim(token, Claims::getId));
    }

    private  <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, EXPIRATION);
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> extraClaims = new HashMap<>();
        User user = (User) userDetails;
        List<String> roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();
        extraClaims.put("userId", user.getId());
        extraClaims.put("roles", roles);

        return generateToken(extraClaims, userDetails);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return buildToken(new HashMap<>(), userDetails, REFRESH_EXPIRATION);
    }

    public boolean isValidToken(String token) {
        final String userAccount = extractUserAccount(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(userAccount);
        if (!userAccount.equals(userDetails.getUsername())) {
            throw new UnauthorizedException(AuthExceptionMessage.MISMATCH_TOKEN.message());
        }
        if (isTokenExpired(token)) {
            throw new UnauthorizedException(AuthExceptionMessage.TOKEN_VALID_TIME_EXPIRED.message());
        }
        return true;
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes =  Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration)
    {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Authentication getAuthenticationParseToken(String token) {
        try {
            Claims claims = extractAllClaims(token);
            Long userId = (long) (int) claims.get("userId");
            List<String> userRole = (List<String>) claims.get("roles");


            UserPrincipleDto userPrincipleDto = new UserPrincipleDto(userId, userRole);
            return new UsernamePasswordAuthenticationToken(userPrincipleDto, null, userPrincipleDto.getAuthorities());
        } catch (Exception e) {
            throw new UnauthorizedException(AuthExceptionMessage.FAIL_TOKEN_CHECK.message());
        }
    }

}
