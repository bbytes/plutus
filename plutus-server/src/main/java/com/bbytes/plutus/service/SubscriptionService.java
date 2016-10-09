package com.bbytes.plutus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbytes.plutus.model.Subscription;
import com.bbytes.plutus.repo.SubscriptionRepository;

@Service
public class SubscriptionService extends AbstractService<Subscription, String> {

	private SubscriptionRepository subscriptionRepository;

	@Autowired
	public SubscriptionService(SubscriptionRepository subscriptionRepository) {
		super(subscriptionRepository);
		this.subscriptionRepository = subscriptionRepository;
	}

	public Subscription findBysubscriptionKey(String subscriptionKey) {
		return subscriptionRepository.findBysubscriptionKey(subscriptionKey);
	}

//	public String createLicenseContent(Subscription licenseData) throws SubscriptionCreateException {
//
//		// The JWT signature algorithm we will be using to sign the token
//		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
//
//		long nowMillis = System.currentTimeMillis();
//		Date now = new Date(nowMillis);
//
//		// We will sign our JWT with our ApiKey secret
//		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(GlobalConstant.SECRET_KEY);
//		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
//
//		Map<String, Object> claims = new HashMap<>();
//		try {
//			claims.put(GlobalConstant.LIC_DATA_KEY, licenseData.toJson());
//		} catch (JsonProcessingException e) {
//			throw new SubscriptionCreateException(e);
//		}
//
//		// Let's set the JWT Claims
//		JwtBuilder builder = Jwts.builder().setId(licenseData.getId()).setClaims(claims).setIssuedAt(now)
//				.setSubject(licenseData.getSubscriptionKey()).setIssuer("Plutus").setClaims(claims)
//				.signWith(signatureAlgorithm, signingKey);
//
//		// if it has been specified, let's add the expiration
//		builder.setExpiration(licenseData.getValidTill());
//
//		// Builds the JWT and serializes it to a compact, URL-safe string
//		return builder.compact();
//	}
//
//	public File createLicenseFile(Subscription licenseData) throws SubscriptionCreateException {
//		String licContent = createLicenseContent(licenseData);
//		PrintWriter out = null;
//		try {
//			File licFile = File.createTempFile(licenseData.getId(), ".lic");
//			out = new PrintWriter(licFile);
//			out.write(licContent);
//			return licFile;
//		} catch (Exception e) {
//			throw new SubscriptionCreateException(e);
//		} finally {
//			if (out != null)
//				out.close();
//		}
//
//	}
//
//	public Subscription validate(String licenseContent) throws SubscriptionInvalidException {
//
//		try {
//			Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(GlobalConstant.SECRET_KEY))
//					.parseClaimsJws(licenseContent).getBody();
//			System.out.println("ID: " + claims.getId());
//			System.out.println("Subject: " + claims.getSubject());
//			System.out.println("Issuer: " + claims.getIssuer());
//			System.out.println("Expiration: " + claims.getExpiration());
//			Subscription licenseData = Subscription.fromJson((String) claims.get(GlobalConstant.LIC_DATA_KEY));
//			System.out.println("Lic object: " + licenseData);
//
//			// Builds the JWT and serializes it to a compact, URL-safe string
//			return (Subscription) claims.get(GlobalConstant.LIC_DATA_KEY);
//		} catch (Exception e) {
//			throw new SubscriptionInvalidException(e.getMessage());
//		}
//
//	}
//
//	public Subscription validateLicenseFile(File licenseFile) throws SubscriptionInvalidException, IOException {
//		String licenseContent = new String(Files.readAllBytes(licenseFile.toPath()));
//		return validate(licenseContent);
//	}

}
