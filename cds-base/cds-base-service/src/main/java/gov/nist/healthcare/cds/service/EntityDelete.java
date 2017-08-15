package gov.nist.healthcare.cds.service;

import org.springframework.beans.factory.annotation.Autowired;

import gov.nist.healthcare.cds.domain.Entity;
import gov.nist.healthcare.cds.domain.exception.IllegalDelete;
import gov.nist.healthcare.cds.enumeration.EntityAccess;

public abstract class EntityDelete<T extends Entity> {

	@Autowired
	protected MetaDataService mdService;
	
	@Autowired
	protected PropertyService ledger;
	
	private final Class<T> clazz;
	
	public EntityDelete(Class<T> c){
		this.clazz = c;
	}
	
	public abstract boolean proceed(T entity, String user);
	
	public boolean delete(String id, String user) throws IllegalDelete{
		T _e = ledger.belongsTo(id, user, clazz, EntityAccess.W);
		this.verify(clazz, _e, id);
		return this.proceed(_e, user);
	}
	
	public <E extends Entity> E verify(Class<E> c, E x, String id) throws IllegalDelete{
		if(x == null){
			throw new IllegalDelete(c.getName()+" ID "+id+" Does Not Exist or Does not Belong to USER");
		}
		return x;
	}
	
}
