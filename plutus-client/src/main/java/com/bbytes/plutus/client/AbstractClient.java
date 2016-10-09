package com.bbytes.plutus.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.Base64Utils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.bbytes.plutus.enums.AppProfile;
import com.bbytes.plutus.util.GlobalConstant;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AbstractClient {

	private static final Integer TIMEOUT_IN_SECS = 30;

	private RestTemplate restTemplate;

	private String baseURL;

	public AbstractClient(String baseUrl, String subscriptionKey, String subscriptionSecret, AppProfile appProfile) {
		List<HttpMessageConverter<?>> messageConverters = getMessageConverters();
		restTemplate = new RestTemplate(clientHttpRequestFactory());
		if (messageConverters != null && !messageConverters.isEmpty()) {
			restTemplate.setMessageConverters(messageConverters);
		}

		registerInterceptors(subscriptionKey, subscriptionSecret, appProfile);
		setBaseURL(baseUrl);

	}

	private ClientHttpRequestFactory clientHttpRequestFactory() {
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		factory.setReadTimeout(getReadTimeoutInSecs() * 1000);
		factory.setConnectTimeout(getConnectionTimeoutInSecs() * 1000);
		return factory;
	}

	protected Integer getReadTimeoutInSecs() {
		return TIMEOUT_IN_SECS;
	}

	protected Integer getConnectionTimeoutInSecs() {
		return TIMEOUT_IN_SECS;
	}

	private void registerInterceptors(String subscriptionKey, String subscriptionSecret, AppProfile appProfile) {
		List<ClientHttpRequestInterceptor> interceptors = getRestTemplate().getInterceptors();
		interceptors.add(new PlutusTokenRequestInterceptor(subscriptionKey, subscriptionSecret, appProfile));
		getRestTemplate().setInterceptors(interceptors);
	}

	protected List<HttpMessageConverter<?>> getMessageConverters() {
		return new ArrayList<>();
	}

	protected RestTemplate getRestTemplate() {
		return restTemplate;
	}

	protected String getBaseURL() {
		return baseURL;
	}

	protected void setBaseURL(String baseURL) {
		this.baseURL = baseURL;
	}


	protected <T> T post(String reativeURL, HttpEntity<?> entity, Class<T> type) throws PlutusClientException {
		try {
			return restTemplate.postForObject(baseURL + reativeURL, entity, type);
		} catch (Throwable e) {
			throw new PlutusClientException(e);
		}

	}

	protected <T> T get(String reativeURL, MultiValueMap<String, String> paramMap, Class<T> type)
			throws PlutusClientException {
		try {
			UriComponents uriComponents = UriComponentsBuilder.fromUriString(baseURL + reativeURL).queryParams(paramMap)
					.build();
			return restTemplate.getForObject(uriComponents.toUriString(), type);
		} catch (Throwable e) {
			throw new PlutusClientException(e);
		}

	}

	protected <T> T get(String reativeURL, Class<T> type) throws PlutusClientException {
		try {
			return restTemplate.getForObject(baseURL + reativeURL, type);
		} catch (Throwable e) {
			throw new PlutusClientException(e);
		}

	}

	private static final class PlutusTokenRequestInterceptor implements ClientHttpRequestInterceptor {

		private final String subscriptionKey;
		private final String subscriptionSecret;
		private final AppProfile appProfile;

		public PlutusTokenRequestInterceptor(final String subscriptionKey, final String subscriptionSecret,
				final AppProfile appProfile) {
			this.subscriptionKey = subscriptionKey;
			this.subscriptionSecret = subscriptionSecret;
			this.appProfile = appProfile;
		}

		public ClientHttpResponse intercept(final HttpRequest request, final byte[] body,
				ClientHttpRequestExecution execution) throws IOException {

			request.getHeaders().add(GlobalConstant.APP_PROFILE_HEADER, appProfile.toString());
			request.getHeaders().add(GlobalConstant.SUBSCRIPTION_KEY_HEADER, subscriptionKey);
			request.getHeaders().add(GlobalConstant.AUTH_TOKEN_HEADER, createToken());
			return execution.execute(request, body);
		}

		public String createToken() {
			Date now = new Date();
			Date expiration = DateTime.now().plusDays(1).toDate();
			return Jwts.builder().setId(UUID.randomUUID().toString()).setSubject(subscriptionKey).setIssuedAt(now)
					.setIssuer(subscriptionKey).setExpiration(expiration)
					.signWith(SignatureAlgorithm.HS512, Base64Utils.encode(subscriptionSecret.getBytes())).compact();
		}

	}

}
