package gov.nist.healthcare.cds.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Document
public class VaccineMapping implements Serializable {

	@Id
	private String id;

	@DBRef
	private Vaccine vx;
	
	@DBRef
	private Set<VaccineGroup> groups;
	
	@JsonManagedReference
	@DBRef
	private Set<Product> products;
	
	private boolean isGroup;
	

    
	public Set<VaccineGroup> getGroups() {
		return groups;
	}
	public void setGroups(Set<VaccineGroup> groups) {
		this.groups = groups;
	}
	public boolean isGroup() {
		return isGroup;
	}
	public void setGroup(boolean isGroup) {
		this.isGroup = isGroup;
	}
	
	public Set<Product> getProducts() {
		return products;
	}
	public void setProducts(Set<Product> products) {
		this.products = products;
	}

	public Vaccine getVx() {
		return vx;
	}

	public void setVx(Vaccine vx) {
		this.vx = vx;
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
        if (vx == null || obj == null || getClass() != obj.getClass())
            return false;
        VaccineMapping that = (VaccineMapping) obj;
        if(that.vx == null)
        	return false;
        return vx.getCvx().equals(that.vx.getCvx());
    }
    
    @Override
    public int hashCode() {
        return vx == null ? 0 : vx.hashCode();
    }
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
