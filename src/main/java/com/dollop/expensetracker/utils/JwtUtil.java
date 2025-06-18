//package com.dollop.expensetracker.utils;
//
//import java.security.SignatureException;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.concurrent.TimeUnit;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//
//import com.dollop.expensetracker.enums.OtpType;
//import com.dollop.expensetracker.enums.TokenType;
//
//
//
//public class JwtUtil {
//	@Value("${app.secret}")
//	private String secret;
//	@Autowired
//	private IOtpService sevice;
//	public String getUsername(String token) {
//		return getClaims(token).getSubject();
//	}
//
//	public String generateToken(String subject, OtpType otpType, TokenType tokenType) {
//		Map<String, Object> m = new HashMap<String, Object>();
//		if (tokenType.equals(TokenType.AUTH_TOKEN))
//			m.put("otpType", otpType); 
//		if(otpType.equals(OtpType.FORGET_PASSWORD)) {
//			m.put("otp",sevice.getById(subject).getOtp());
//		}
//		m.put("tokenType", tokenType);
//		return generateToken(m, subject, tokenType);
//	}
//
//	private Claims getClaims(String token) {
//		return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
//	}
//
//	private String generateToken(Map<String, Object> claims, String subject, TokenType tokenType) {
////		System.err.println(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(9)));
//		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
//				.setExpiration(tokenType.equals(TokenType.AUTH_TOKEN)
//						? new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5))
//						: new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(9)))
//				.setIssuer("ConnectLocalPlatfrom").signWith(SignatureAlgorithm.HS256, secret.getBytes()).compact();
//	}
//
//	public Object getHeader(String token, String key) {
////		System.err.println(token);
//		return this.getClaims(token).get(key);
//	}
//
//	public String generateToken(String subject, TokenType tokenType, String role, String id) {
//		Map<String, Object> m = new HashMap<String, Object>();
//		
//		m.put("tokenType", tokenType);
//		m.put("role", role);
//		m.put("id", id);
//		return generateToken(m, subject, tokenType);
//	}
//	 public  Map<String,Object> extractClaimsEvenIfExpired(String token) {
//	         
//	        Claims body = Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
//
//	        try {
//	            // This will throw ExpiredJwtException if token is expired
//	            return body;
//	        } catch (ExpiredJwtException e) {
//	            // Token expired, but you can still get claims from it
//	            return e.getClaims();
//	        } catch (SignatureException e) {
//	            // Signature invalid
//	            System.out.println("Invalid token signature");
//	        } catch (Exception e) {
//	            System.out.println("Some other error: " + e.getMessage());
//	        }
//
//	        return null;
//	    }
//}
