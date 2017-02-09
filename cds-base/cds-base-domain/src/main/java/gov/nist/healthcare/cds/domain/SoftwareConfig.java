package gov.nist.healthcare.cds.domain;

import gov.nist.healthcare.cds.enumeration.FHIRAdapter;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class SoftwareConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6056059554984678200L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private String endPoint;
	@Column(nullable=true)
	@Enumerated(EnumType.STRING)
	private FHIRAdapter connector;
	@JsonIgnore
	private String user;
	
	public Long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}
	
    public FHIRAdapter getConnector() {
		return connector;
	}

	public void setConnector(FHIRAdapter connector) {
		this.connector = connector;
	}

	@Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (id == null || obj == null || getClass() != obj.getClass())
            return false;
        SoftwareConfig that = (SoftwareConfig) obj;
        return id.equals(that.id);
    }
    
    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }
    
	@Override
	public String toString() 
	{ 
	    return ToStringBuilder.reflectionToString(this); 
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	
}
