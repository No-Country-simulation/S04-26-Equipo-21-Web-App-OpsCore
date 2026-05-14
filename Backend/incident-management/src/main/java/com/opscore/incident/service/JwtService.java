package com.opscore.incident.service;

import com.opscore.incident.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtService {

    @Value("${spring.application.name}")
    private String issuer;

    @Value("${security.jwt.access-exp-min}")
    private long accessExpMin;

    @Value("${security.jwt.refresh-exp-hours}")
    private long refreshExpHours;

    @Value("${jwt.key.private}")
    private String privateKeyBase64;

    @Value("${jwt.key.public}")
    private String publicKeyBase64;

    private ECPrivateKey privateKey;
    private ECPublicKey publicKey;

    private static final String TYPE = "type";
    private static final String ACCESS = "access";
    private static final String REFRESH = "refresh";

    @PostConstruct
    public void init() throws NoSuchAlgorithmException, InvalidKeySpecException {
        this.privateKey = loadPrivateKey(privateKeyBase64);
        this.publicKey = loadPublicKey(publicKeyBase64);
    }

    // =========================
    // GENERATION
    // =========================

    public String generateAccessToken(Usuario usuario) {
        return buildToken(usuario, ACCESS, accessExpMin, ChronoUnit.MINUTES);
    }

    public String generateRefreshToken(Usuario usuario) {
        return buildToken(usuario, REFRESH, refreshExpHours, ChronoUnit.HOURS);
    }

    private String buildToken(Usuario usuario, String type, long amount, ChronoUnit unit) {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime exp = now.plus(amount, unit);

        return Jwts.builder()
                .issuer(issuer)
                .subject(usuario.getUsername())
                .issuedAt(toDate(now))
                .expiration(toDate(exp))
                .claim("uid", usuario.getId())
                .claim("role", usuario.getRol().name())
                .claim(TYPE, type)
                .signWith(privateKey, Jwts.SIG.ES256)
                .compact();
    }

    // =========================
    // VALIDATION
    // =========================

    public boolean isTokenValid(String token) {
        try {
            Claims claims = extractClaims(token);

            if (claims.getExpiration().before(new Date())) {
                return true;
            }

            return claims.getSubject() == null;

        } catch (Exception e) {
            return true;
        }
    }

    public boolean isAccessToken(String token) {
        return ACCESS.equals(extractClaims(token).get(TYPE));
    }

    public boolean isRefreshToken(String token) {
        return REFRESH.equals(extractClaims(token).get(TYPE));
    }

    // =========================
    // EXTRACTION
    // =========================

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(publicKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // =========================
    // KEY LOADING
    // =========================

    private ECPrivateKey loadPrivateKey(String base64) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] decoded = decodePem(base64);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
        return (ECPrivateKey) KeyFactory.getInstance("EC").generatePrivate(spec);
    }

    private ECPublicKey loadPublicKey(String base64) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] decoded = decodePem(base64);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
        return (ECPublicKey) KeyFactory.getInstance("EC").generatePublic(spec);
    }

    private byte[] decodePem(String base64) {
        String pem = new String(Base64.getDecoder().decode(base64));
        pem = pem.replaceAll("-----BEGIN.*?-----", "")
                .replaceAll("-----END.*?-----", "")
                .replaceAll("\\s+", "");
        return Base64.getDecoder().decode(pem);
    }

    private Date toDate(LocalDateTime time) {
        return Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
    }

}
