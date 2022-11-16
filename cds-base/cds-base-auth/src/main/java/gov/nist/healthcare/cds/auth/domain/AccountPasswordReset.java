package gov.nist.healthcare.cds.auth.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.Id;
import org.springframework.security.crypto.codec.Base64;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = "new", ignoreUnknown = true)
public class AccountPasswordReset implements Serializable  {

	private static final long serialVersionUID = 20130625L;
	public static final Long tokenValidityTimeInMilis = 172800000L;

	
	@Id
	protected String id;
	
	
	@Column(unique = true)
	private String username;

	@Column(unique = true)
	private String currentToken;

	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;
	private Long numberOfReset = 0L;

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the currentToken
	 */
	public String getCurrentToken() {
		return currentToken;
	}

	/**
	 * @param currentToken
	 *            the currentToken to set
	 */
	public void setCurrentToken(String currentToken) {
		this.currentToken = currentToken;
	}

	/**
	 * @return the timestamp
	 */
	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp
	 *            the timestamp to set
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return the numberOfReset
	 */
	public Long getNumberOfReset() {
		return numberOfReset;
	}

	/**
	 * @param numberOfReset
	 *            the numberOfReset to set
	 */
	public void setNumberOfReset(Long numberOfReset) {
		this.numberOfReset = numberOfReset;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isTokenExpired() {
		boolean result = false;

		Long currentTimeInMilis = (new Date()).getTime();
		result = (currentTimeInMilis - timestamp.getTime()) > tokenValidityTimeInMilis;

		return result;
	}

	public static String getNewToken() throws Exception {
		String result = UUID.randomUUID().toString();
		// base 64 encoding
		byte[] bs = Base64.encode(result.getBytes());
		result = new String(bs, "UTF-8");
		return result;
	}

}