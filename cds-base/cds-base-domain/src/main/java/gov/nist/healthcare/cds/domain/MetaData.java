package gov.nist.healthcare.cds.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Version;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
public class MetaData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6944914219990941855L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String version;
	private boolean imported;
	@OneToOne(cascade = CascadeType.ALL, optional = false, orphanRemoval = true)
	private FixedDate dateCreated;
	@OneToOne(cascade = CascadeType.ALL, optional = false, orphanRemoval = true)
	private FixedDate dateLastUpdated;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public boolean isImported() {
		return imported;
	}
	public void setImported(boolean imported) {
		this.imported = imported;
	}
	public FixedDate getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(FixedDate dateCreated) {
		this.dateCreated = dateCreated;
	}
	public FixedDate getDateLastUpdated() {
		return dateLastUpdated;
	}
	public void setDateLastUpdated(FixedDate dateLastUpdated) {
		this.dateLastUpdated = dateLastUpdated;
	}
	@Override
	public String toString() 
	{ 
	    return ToStringBuilder.reflectionToString(this); 
	}
	
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (id == null || obj == null || getClass() != obj.getClass())
            return false;
        MetaData that = (MetaData) obj;
        return id.equals(that.id);
    }
    
    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }
	
	
	
}
