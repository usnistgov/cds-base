package gov.nist.healthcare.cds.service;

import org.springframework.beans.factory.annotation.Autowired;

import gov.nist.healthcare.cds.domain.Entity;
import gov.nist.healthcare.cds.domain.exception.IllegalSave;
import gov.nist.healthcare.cds.enumeration.EntityAccess;
import gov.nist.healthcare.cds.service.domain.SaveObject;

public abstract class EntitySaver<T extends Entity> {

	@Autowired
	protected MetaDataService mdService;
	
	@Autowired
	protected PropertyService ledger;
	
	@Autowired
	protected SaveObject saveObject;
	
	private final Class<T> clazz;
	
	public EntitySaver(Class<T> c){
		this.clazz = c;
	}
	
	public T save(T target) {
		saveObject.save();
		return target;
	}
	
	public T saveEntity(T e, String user) throws IllegalSave {
		saveObject.clear();
		if(this.exists(e)){
			System.out.println("[HTEX]");
			T persisted = ledger.belongsTo(e.getId(), user, clazz, EntityAccess.W);
			this.verify(clazz, persisted, e.getId());
			this.prepare(persisted, e, user);
			return save(e);
		}
		else {
			System.out.println("[NO HTEX]");
			this.prepare(null, e, user);
			return save(e);
		}
	}
	
	public <E extends Entity> E verify(Class<E> c, E x, String id) throws IllegalSave{
		if(x == null){
			throw new IllegalSave(c.getName()+" ID "+id+" Does Not Exist or Does not Belong to USER");
		}
		return x;
	}
	
	public abstract void prepare(T persisted, T e, String user) throws IllegalSave;
	
	public abstract boolean exists(T e);

}
