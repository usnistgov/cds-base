package gov.nist.healthcare.cds.domain.wrapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Resources {

	private List<Document> resources;
	private Map<String, Document> resourceMap;

	public List<Document> getResources() {
		return resources;
	}

	public void setResources(List<Document> resources) {
		this.resources = resources;
		this.resourceMap = new HashMap<>();
		for(Document doc: resources) {
			this.resourceMap.put(doc.getFileName(), doc);
		}
	}

	public Map<String, Document> getResourceMap() {
		return resourceMap;
	}
}
