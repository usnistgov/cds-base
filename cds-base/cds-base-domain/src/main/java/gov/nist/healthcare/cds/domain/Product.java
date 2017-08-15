package gov.nist.healthcare.cds.domain;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonTypeName;

@Document
@JsonTypeName("specific")
public class Product extends Injection implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4331540454494356651L;
	@DBRef
	private Vaccine vx;
	@DBRef
	private Manufacturer mx;
	private String name;

	public Manufacturer getMx() {
		return mx;
	}
	public void setMx(Manufacturer mx) {
		this.mx = mx;
	}
	
    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Vaccine getVx() {
		return vx;
	}
	public void setVx(Vaccine vx) {
		this.vx = vx;
	}
	public String getCode() {
		return id;
	}
	public void setCode(String code) {
		this.id = code;
	}
	@Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (id == null || obj == null || getClass() != obj.getClass())
            return false;
        Product that = (Product) obj;
        return id.equals(that.id);
    }
    
    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }
	@Override
	public String getCvx() {
		return this.getVx().getCvx();
	}
	
}
